package layout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import models.Entry;
import vinay.com.publicity.R;

public class BalanceOfPeople extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    AdapterForPeopleWithBalance maAdapter;
   static ArrayList<Entry> entries=new ArrayList<Entry>();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
       final String eventname= getIntent().getStringExtra("eventname");
       firebaseDatabase=FirebaseDatabase.getInstance();
       databaseReference=firebaseDatabase.getReference();

       Log.i("sizeofentry",String.valueOf(entries.size()));
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
maAdapter=new AdapterForPeopleWithBalance(entries);
RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
recyclerView.setLayoutManager(mLayoutManager);
recyclerView.setItemAnimator(new DefaultItemAnimator());
recyclerView.setAdapter(maAdapter);

    }
}
