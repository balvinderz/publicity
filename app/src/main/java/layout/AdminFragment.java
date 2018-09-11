package layout;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import models.Entry;
import models.Event;
import vinay.com.publicity.R;

public class AdminFragment extends android.support.v4.app.Fragment {
    Firebase eventRef;
  public  static ArrayList<Integer> balance=new ArrayList<Integer>();
public  static ArrayList<String> eventnames=new ArrayList<String>();
public  static ArrayList<Integer> moneycollected=new ArrayList<Integer>();
public static ArrayList<Entry> entries=new ArrayList<Entry>();
    int size = 0;
    int i = 0;
    int l=0;
    int balancez;
    String eventname;
    public AdminFragment() {
        // Required empty public constructor
        eventRef = new Firebase(SharedResources.EVENT);
    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        ListView list = (ListView) getActivity().findViewById(R.id.list);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Admin");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference1 = firebaseDatabase.getReference();
        BalanceOfPeople.entries=new ArrayList<Entry>();

        databaseReference.child("entries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                for(DataSnapshot child:children)
                {
                    Entry entry=child.getValue(Entry.class);
                    if(entry.getEventName().equals(eventname)&&entry.getBalance()!=0) {
                        BalanceOfPeople.entries.add(entry);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseListAdapter<Event> adapter= new FirebaseListAdapter<Event>(
                getActivity(),
                Event.class,
                R.layout.balance,
                eventRef
        )
        {
            @Override
            protected void populateView(View view, Event event, int i) {
                final TextView textView= (TextView) view.findViewById(R.id.xyz);
                TextView textView2= (TextView) view.findViewById(R.id.money);
                TextView textView3=(TextView) view.findViewById(R.id.balance);
                textView.setText(event.getName());
                textView2.setText("Money collected: "+event.getTotal_cost());
                textView3.setText("Money to be collected: "+event.getbalance());
              final  Button button=view.findViewById(R.id.nothing);
                 l=0;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                         eventname=textView.getText().toString();
                        Intent intent=new Intent(getActivity(),BalanceOfPeople.class);
                        intent.putExtra("eventname",eventname);
                        BalanceOfPeople.entries=new ArrayList<Entry>();
                        databaseReference.child("entries").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                                for(DataSnapshot child:children)
                                {
                                    Entry entry=child.getValue(Entry.class);
                                    if(entry.getEventName().equals(eventname)&&entry.getBalance()!=0) {
                                        BalanceOfPeople.entries.add(entry);

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                            startActivity(intent);

                    }
                });
            }
        };
        list.setAdapter(adapter);

    }
}