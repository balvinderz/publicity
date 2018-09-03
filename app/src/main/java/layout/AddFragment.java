package layout;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;

import alladpaters.EventListAdapter;
import database.EventDatabaseOperations;
import models.Event;
import vinay.com.publicity.MainActivity;
import vinay.com.publicity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {

    ListView list;

    Firebase eventRef;
    //local DB
    EventDatabaseOperations DB;
    Cursor CR;
    public AddFragment() {
        eventRef=new Firebase(SharedResources.EVENT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list= (ListView) getActivity().findViewById(R.id.listEvents);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Publicity");
        if(isNetworkConnected()){
            LoadFromFirebase();
        }else{
            LoadFromLocal();
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String eventName = ((TextView)view.findViewById(R.id.textViewEvent1))
                        .getText().toString();
                int cost=Integer.parseInt(((TextView)view.findViewById(R.id.textViewEvent2))
                        .getText().toString().trim());
                SharedPreferences sp=getActivity().getSharedPreferences(
                        SharedResources.SharedUSERDATA, Context.MODE_PRIVATE);

                String receiptId=sp.getString(SharedResources.SharedInital,"")+"-"
                        +(sp.getInt(SharedResources.SharedENTRIES,-99)+1);
                android.support.v4.app.FragmentTransaction transaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                AddFormFragment add = new AddFormFragment();
                add.setEvent(eventName,receiptId,cost);
                transaction.replace(R.id.fragment_container, add);
                ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle(eventName +" "+ receiptId);
                transaction.commit();
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    void  LoadFromLocal(){
        ArrayList<Event> eventList=new ArrayList<Event>();;
        DB=new EventDatabaseOperations(getActivity().getApplicationContext());
        CR=DB.getInformation(DB);
        CR.moveToFirst();
        if(CR.getCount()>0) {
                do{
                    Event event = new Event(CR.getString(0),CR.getInt(1));
                    eventList.add(event);
            }while(CR.moveToNext());
            EventListAdapter eventListAdapter=new EventListAdapter(getActivity(),eventList);
            list.setAdapter(eventListAdapter);
        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Device is OFFLINE and Events are not synchronized YET");
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
        }
    }

    void LoadFromFirebase() {
        FirebaseListAdapter<Event> adapter= new FirebaseListAdapter<Event>(
                getActivity(),
                Event.class,
                R.layout.event_single_row,
                eventRef
        ) {
            @Override
            protected void populateView(View view, Event event, int i) {
                TextView textView= (TextView) view.findViewById(R.id.textViewEvent1);
                TextView textView2= (TextView) view.findViewById(R.id.textViewEvent2);
                textView.setText(event.getName());
                textView2.setText(" "+event.getCost());
            }
        };
        list.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
