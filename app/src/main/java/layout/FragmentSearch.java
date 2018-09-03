package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import models.Entry;
import models.Event;
import vinay.com.publicity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearch extends Fragment {

    Firebase entriesRef;
    Firebase costDBRef;



    EditText receiptId;
    Button btnSearch;

    TextView tvreceiptId;
    TextView EventName;
    TextView PaymentDate;
    Button Pay_Balance;
    TextView NameDisplay;
    TextView Mobile;
    TextView Email;
    TextView College;
    TextView Year;
    TextView payment;
    TextView balance;

    ScrollView scrollView;
    Event event;
    Entry entry;
    String key;
    String keyEvent;
    public FragmentSearch() {
        // Required empty public constructor
        entriesRef=new Firebase(SharedResources.ENTRIES);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Search");
        receiptId= (EditText) getActivity().findViewById(R.id.receiptid);
        btnSearch= (Button) getActivity().findViewById(R.id.btnSearch);

        tvreceiptId= (TextView) getActivity().findViewById(R.id.receiptId);
        EventName= (TextView) getActivity().findViewById(R.id.EventName);
        Pay_Balance= (Button) getActivity().findViewById(R.id.Pay_Balance);
        NameDisplay= (TextView) getActivity().findViewById(R.id.NameDisplay);
        Mobile= (TextView) getActivity().findViewById(R.id.Mobile);
        Email= (TextView) getActivity().findViewById(R.id.Email);
        College= (TextView) getActivity().findViewById(R.id.College);
        Year= (TextView) getActivity().findViewById(R.id.Year);
        payment= (TextView) getActivity().findViewById(R.id.payment);
        balance= (TextView) getActivity().findViewById(R.id.balance);
        PaymentDate= (TextView) getActivity().findViewById(R.id.PaymentDate);
        scrollView= (ScrollView) getActivity().findViewById(R.id.printDetails);

        SharedPreferences sp=getActivity().getSharedPreferences(
                SharedResources.SharedUSERDATA,
                Context.MODE_PRIVATE);
        receiptId.setText(sp.getString(SharedResources.SharedInital,"")+"-");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id=receiptId.getText().toString();
                scrollView.setVisibility(View.INVISIBLE);
                entriesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean isFound=false;
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            entry=ds.getValue(Entry.class);

                            if(id.equals(entry.getReceiptId())){
                                fillData(entry);
                                key=ds.getKey();
                                isFound=true;
                                break;
                            }
                        }
                        if(!isFound){
                            Toast.makeText(getActivity(),"No such Entry Found!!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });


        Pay_Balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                costDBRef=new Firebase(SharedResources.EVENT);
                costDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds :dataSnapshot.getChildren()){
                            event=ds.getValue(Event.class);
                            if(event.getName().equals(entry.getEventName())){
                                keyEvent=ds.getKey();
                                break;
                            }else{
                                event=null;
                            }
                        }
                        event.setTotal_cost(event.getTotal_cost()+entry.getBalance());
                        event.setNo_payment_due(event.getNo_payment_due()-1);
                        costDBRef.child(keyEvent).setValue(event);
                        entry.setPayment(entry.getPayment()+entry.getBalance());
                        entry.setBalance(0);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        String time=sdf.format(new Date());
                        entry.setBalance_payed_at(time);
                        entry.setPaid(true);
                        SharedPreferences sp=getActivity().getSharedPreferences(
                                SharedResources.SharedUSERDATA, Context.MODE_PRIVATE);
                        entry.setBalance_paid_by(sp.getString(SharedResources.SharedInital,""));
                        entriesRef.child(key).setValue(entry);
                        event=new Event();
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });

            }
        });

    }
    private void fillData(Entry entry){
        scrollView.setVisibility(View.VISIBLE);
        tvreceiptId.setText(""+entry.getReceiptId());
        EventName.setText(""+entry.getEventName());
        PaymentDate.setText(""+entry.getPayed_at());
        NameDisplay.setText(""+entry.getName());
        Mobile.setText(""+entry.getMobile());
        Email.setText(""+entry.getEmail());
        College.setText(""+entry.getCollege());
        Year.setText(""+entry.getYear());
        payment.setText(""+entry.getPayment());
        balance.setText(""+entry.getBalance());
        if(entry.getBalance()>0){
            Pay_Balance.setVisibility(View.VISIBLE);
        }
        else{
            Pay_Balance.setVisibility(View.INVISIBLE);
        }
    }
}
