package layout;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import models.Entry;
import models.Event;
import models.User;
import vinay.com.publicity.MainActivity;
import vinay.com.publicity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFormFragment extends Fragment {


    Firebase userRef;
    Entry entry;
    Firebase eventRef;
    Firebase entriesRef;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
    String eventName;
    String receiptId;
    int totalCost;

    TextView name;
    TextView mobile;
    TextView email;
    TextView college;
    Spinner year;
    TextView payment;
    TextView balance;
    Button btnAdd;
    CheckBox chkMember;
CheckBox team;
int t=0;
    boolean isCSI=false;
    Event event;
    String key;

    EditText bala,paywithteam;
    String UserKey;
    public AddFormFragment() {
        // Required empty public constructor
        userRef=new Firebase(SharedResources.USER);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(receiptId==null || eventName==null){
            android.support.v4.app.FragmentTransaction transaction =
                    getActivity().getSupportFragmentManager().beginTransaction();
            AddFragment addFragment = new AddFragment();
            transaction.replace(R.id.fragment_container, addFragment);
            transaction.commit();
        }
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        name= (TextView) getActivity().findViewById(R.id.name);
        mobile= (TextView) getActivity().findViewById(R.id.Mobile);;
        email= (TextView) getActivity().findViewById(R.id.email);;
        college= (TextView) getActivity().findViewById(R.id.college);;
        year= (Spinner) getActivity().findViewById(R.id.year);;
        payment= (TextView) getActivity().findViewById(R.id.pay);;
        btnAdd= (Button) getActivity().findViewById(R.id.btnAdd);
        balance= (TextView) getActivity().findViewById(R.id.tvBalance);
        chkMember=(CheckBox)getActivity().findViewById(R.id.chkMember);
        team =(CheckBox) getActivity().findViewById(R.id.team);
        bala=getActivity().findViewById(R.id.balance);
        paywithteam=getActivity().findViewById(R.id.paywithteam);
        balance.setText(""+totalCost);
      /*  eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds :dataSnapshot.getChildren()){
                    event=ds.getValue(Event.class);
                    if(event.getName().equals(eventName)) {
                        key = ds.getKey();
                        break;
                    }else {
                        event=null;
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
databaseReference.child("events").addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
    @Override
    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
        for(com.google.firebase.database.DataSnapshot ds:dataSnapshot.getChildren())
        {
            event=ds.getValue(Event.class);
            if(event.getName().equals(eventName)){
                key=ds.getKey();
                break;
            }
            else
            {
                event=null;
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED && validate()) {
                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                   String time = sdf.format(new Date());
                   if(t==0)
                   {
                    entry = new Entry(
                           receiptId,
                           name.getText().toString(),
                           mobile.getText().toString(),
                           email.getText().toString(),
                           college.getText().toString(),
                           String.valueOf(year.getSelectedItem()),
                           eventName,
                           Integer.parseInt(payment.getText().toString()),
                           "new",//status,
                           false,//csi member set it later
                           Integer.parseInt(balance.getText().toString()),
                           time,
                           "",//balance payed at
                           "",//balance paid by
                           true//paid or not
                           ,MainActivity.membername
                   );}
                   else
                   {
                        entry = new Entry(
                               receiptId,
                               name.getText().toString(),
                               mobile.getText().toString(),
                               email.getText().toString(),
                               college.getText().toString(),
                               String.valueOf(year.getSelectedItem()),
                               eventName,
                               Integer.parseInt(paywithteam.getText().toString()),
                               "new",//status,
                               false,//csi member set it later
                               Integer.parseInt(bala.getText().toString()),
                               time,
                               "",//balance payed at
                               "",//balance paid by
                               true//paid or not
                               ,MainActivity.membername
                       );
                   }
                   //sending message----------------------------------
                   //SEND TO participant
                   String textmsg="RAIT EVENTS\n"+
                           "Name:"+name.getText().toString()+"\n"+
                           "ReceiptNo:"+receiptId+"\n"+
                           "EventName:"+eventName+"\n"+
                           "COST:"+totalCost+"\n"+
                           "Balance:"+balance.getText().toString();
                   String phoneNO="+91"+mobile.getText().toString();
                   SmsManager smsManager = SmsManager.getDefault();
                   smsManager.sendTextMessage(phoneNO, null, textmsg, null, null);
                   Toast.makeText(getActivity(), "Msg sent to participant!", Toast.LENGTH_SHORT).show();
                   //SEND TO Admin
                   textmsg+="\nClient Mob Number:-"+phoneNO;
                   smsManager.sendTextMessage(SharedResources.ADMINNO, null, textmsg, null, null);
                   Toast.makeText(getActivity(), "Msg sent to Admin", Toast.LENGTH_SHORT).show();


                   //---------------------------------------------------------
                   if (isCSI) {
                       event.setCsi_member(event.getCsi_member() + 1);
                       entry.setCsimember(true);
                   }
                   event.setNo_of_participants(event.getNo_of_participants() + 1);
                   if (entry.getPayment() != totalCost) {
                       entry.setPaid(false);
                       event.setNo_payment_due(event.getNo_payment_due() + 1);
                   }
                   event.setTotal_cost(event.getTotal_cost() + entry.getPayment());
                   databaseReference.child("entries").push().setValue(entry);
int balance=event.getbalance();
balance=balance+entry.getBalance();
event.setBalance(balance);

               //    eventRef.child(key).setValue(event);
                   databaseReference.child("events").child(key).setValue(event);
                   //sharedPrefeernce update
                   final SharedPreferences sp = getActivity().getSharedPreferences(
                           SharedResources.SharedUSERDATA,
                           Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = sp.edit();
                   editor.putInt(SharedResources.SharedENTRIES,
                           sp.getInt(SharedResources.SharedENTRIES, -99) + 1);
                   editor.apply();
                   //user data update
              /*     userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           for (DataSnapshot ds : dataSnapshot.getChildren()) {
                               User user1 = ds.getValue(User.class);
                               if (user1.getMember_initial().
                                       equals(sp.getString(SharedResources.SharedInital, ""))) {
                                   UserKey = ds.getKey();
                                   user1.setLast_entry(user1.getLast_entry() + 1);
                                   userRef.child(UserKey).setValue(user1);
                                   break;
                               }
                           }
                       }

                       @Override
                       public void onCancelled(FirebaseError firebaseError) {

                       }
                   });*/
              databaseReference.child("users").addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                      for(com.google.firebase.database.DataSnapshot ds:dataSnapshot.getChildren())
                      {
                          User user1=ds.getValue(User.class);
                          if(user1.getMember_initial().equals(sp.getString(SharedResources.SharedInital,""))){
                              UserKey=ds.getKey();
                              user1.setLast_entry(user1.getLast_entry() + 1);
                         //     userRef.child(UserKey).setValue(user1);
                              databaseReference.child("users").child(UserKey).setValue(user1);
                              break;

                          }

                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {

                  }
              });
                   //------------------------------------------------------------------
                   clear();
                   android.support.v4.app.FragmentTransaction transaction =
                           getActivity().getSupportFragmentManager().beginTransaction();
                   WelcomeFragment welcomeFragment = new WelcomeFragment();
                   transaction.replace(R.id.fragment_container, welcomeFragment);
                   transaction.commit();
               }else {
                   String msg1="Something went wrong; check for sms permission is assigned and retry";
                   Toast.makeText(getActivity(),msg1,Toast.LENGTH_LONG).show();
               }
            }
        });
        if(t==0)
        payment.setFilters(new InputFilter[]{ new InputFilterMinMax(0, totalCost)});
        payment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    balance.setText(""+(totalCost-Integer.parseInt(s.toString())));
                }catch (NumberFormatException ex ){
                    balance.setText(" "+totalCost);
                }

            }
        });

        chkMember.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    totalCost/=2;
                    college.setText("RAIT");
                    college.setClickable(false);
                    college.setFocusable(false);
                    college.setFocusableInTouchMode(false);
                    isCSI=true;
                }else{
                    college.setText("");
                    totalCost*=2;
                    college.setClickable(true);
                    college.setFocusable(true);
                    college.setFocusableInTouchMode(true);
                    isCSI=false;
                }
                balance.setText(""+totalCost);
                payment.setFilters(new InputFilter[]{ new InputFilterMinMax(0, totalCost)});
            }
        });
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                balance.setVisibility(View.GONE);
                bala.setVisibility(View.VISIBLE);
                payment.setVisibility(View.GONE);
                paywithteam.setVisibility(View.VISIBLE);
                t=1;
                }
                else
                {
                    balance.setVisibility(View.VISIBLE);
                    t=0;
                    bala.setVisibility(View.GONE);
                    payment.setVisibility(View.VISIBLE);
                }
                }
        });
    }
    private void clear()
    {
        Toast.makeText(getActivity(),"Data Submitted successfully",Toast.LENGTH_SHORT).show();
        name.setText("");
        mobile.setText("");
        email.setText("");
        college.setText("");
        year.setSelection(0);
        payment.setText("");
        chkMember.setChecked(false);
        team.setChecked(false);
    }
    public void setEvent(String eventName,String receiptId,int cost){
        this.eventName=eventName;
        this.receiptId=receiptId;
        entriesRef=new Firebase(SharedResources.ENTRIES);
        eventRef=new Firebase(SharedResources.EVENT);
        totalCost=cost;
    }
    //------------------------------validation
    private boolean validate(){
        boolean flag=true;
        if(     !name.getText().toString().isEmpty()&&
                !mobile.getText().toString().isEmpty()&&
                !email.getText().toString().isEmpty()&&
                !college.getText().toString().isEmpty()&&
                !year.isSelected()
                ){
            if(payment.getText().toString().isEmpty())
            {
                if (paywithteam.getText().toString().isEmpty())
                {
                    paywithteam.setError("enter payment");
                    flag=false;
                }
            }
            // TODO: 26-08-2016 add all type of validation
            if(mobile.getText().toString().length()!=10){
                //mobile wrong
                mobile.setError("enter valid mobile no");
                flag=false;
            }
            if(!isValidName(name.getText().toString())){
                //name wrong
                name.setError("enter valid name");
                flag=false;
            }
            if(!isEmailValid(email.getText().toString())){
                email.setError("enter valid email id");
                flag=false;
            }
        }else {
            flag=false;
            Toast.makeText(getActivity(), "Fill every details first!!", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }

    boolean isValidName(String name){
        char ch[]=name.toCharArray();
        boolean flag=true;
        for(int i=0;i<ch.length;i++){
            if(!Character.isAlphabetic(ch[i])){
                if (ch[i] != ',') { flag=false; break;}

            }
        }
        return  flag;
    }
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    //=====================================================================================================
    private class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input)){
                    return null;
                }
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            if(t==0)
                return true;
            else
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        receiptId=null;
        eventName=null;
    }
}
