package vinay.com.publicity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

import database.EventDatabaseOperations;
import layout.AddFragment;
import layout.AdminFragment;
import layout.FragmentSearch;
import layout.SharedResources;
import layout.WelcomeFragment;
import models.Event;
import models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
public static User user;

public static String membername;


    Firebase eventRef;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    //local DB
    EventDatabaseOperations DB;
    Cursor CR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Publicity");
database=FirebaseDatabase.getInstance();
databaseReference=database.getReference();
        SharedPreferences sp=getSharedPreferences(
                SharedResources.SharedUSERDATA,
                Context.MODE_PRIVATE);
        membername=sp.getString(SharedResources.SharedNAME,"null");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//Log.i("checking something",String.valueOf(user.getAdmin()));
        /*Firebase eventref=new Firebase(SharedResources.EVENT);
        Event event=new Event("NTPP",60,0,0,0,0);
        eventref.push().setValue(event);
        event.setName("Java Jargons");
        eventref.push().setValue(event);
        event.setName("Seek in c");
        eventref.push().setValue(event);
        event.setName("placment mantra");
        eventref.push().setValue(event);
        event.setName("next aim");
        eventref.push().setValue(event);
        event.setName("quiz-o-clock");
        eventref.push().setValue(event);
        event.setName("Game Garage");
        eventref.push().setValue(event);
        event.setName("freakenstein");
        eventref.push().setValue(event);
        event.setName("spellbound");
        eventref.push().setValue(event);
        event.setName("crico-o-lumina");
        eventref.push().setValue(event);
        event.setName("digital picasso");
        eventref.push().setValue(event);
*/

        Log.i("checkingkyaaatahai",String.valueOf(sp.getInt(SharedResources.SharedIsAdmin,-99)));
       if(sp.getInt(SharedResources.SharedIsAdmin,-99)==0)
        {
            NavigationView nav = findViewById(R.id.nav_view);
            Menu menu = nav.getMenu();
            menu.findItem(R.id.adminbar).setVisible(false);
        }
        storeEventsInLocal();

        android.support.v4.app.FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        transaction.replace(R.id.fragment_container, welcomeFragment);
        transaction.commit();












    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
    //    if(user.getAdmin()==1)
      //  {
        //   menu.findItem(R.id.admin).setVisible(true);
        //}


        return true;

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add) {
            android.support.v4.app.FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            AddFragment addFragment = new AddFragment();
            transaction.replace(R.id.fragment_container, addFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.search) {
            android.support.v4.app.FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            FragmentSearch fragmentSearch = new FragmentSearch();
            transaction.replace(R.id.fragment_container, fragmentSearch);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.logOut) {
            SharedPreferences sharedPreferences=getSharedPreferences(
                    SharedResources.SharedUSERDATA,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(SharedResources.SharedNAME,"null");
            editor.putString(SharedResources.SharedInital,"null");
            editor.putInt(SharedResources.SharedENTRIES,-99);
            editor.apply();

            Intent intent=new Intent(getApplicationContext(),Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else if(id==R.id.ptour){
            PrefManager prefManager=new PrefManager(this);
            prefManager.setFirstTimeLaunch(true);
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }
        else if(id==R.id.adminbar)
        {
            android.support.v4.app.FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            AdminFragment addFragment = new AdminFragment();
            transaction.replace(R.id.fragment_container, addFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void storeEventsInLocal(){
        if(isNetworkConnected()){
            eventRef=new Firebase(SharedResources.EVENT);
       /*     databaseReference.child("events").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    EventDatabaseOperations DB = new EventDatabaseOperations(getApplicationContext());
                    DB.clearAll();
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        Event event=ds.getValue(Event.class);
                        DB.putInformation(DB,event.getName(),event.getCost());
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });*/
       databaseReference.child("events").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
           @Override
           public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
               EventDatabaseOperations DB=new EventDatabaseOperations(getApplicationContext());
               DB.clearAll();
               for(com.google.firebase.database.DataSnapshot ds :dataSnapshot.getChildren())
               {
                   Event event=ds.getValue(Event.class);
                   DB.putInformation(DB,event.getName(),event.getCost());
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        }else{
            DB=new EventDatabaseOperations(getApplicationContext());
            CR=DB.getInformation(DB);
            CR.moveToFirst();
            if(CR.getCount()>0){
                do{
                    Event event=new Event(CR.getString(0),CR.getInt(1));
                    Log.d("EVENTS",event.getName()+":"+event.getCost());
                }while(CR.moveToNext());
            }else{
                Toast.makeText(this,"Event List need sync",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
