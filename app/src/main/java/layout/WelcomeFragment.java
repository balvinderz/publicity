package layout;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.util.Collections;
import java.util.List;

import database.EventDatabaseOperations;
import models.Event;
import vinay.com.publicity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {



    public WelcomeFragment() {
        // Required empty public constructor

    }


RelativeLayout add,search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Publicity");
        add= (RelativeLayout) getActivity().findViewById(R.id.addLayout);
        search= (RelativeLayout) getActivity().findViewById(R.id.searchLayout);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction transaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                AddFragment addFragment = new AddFragment();
                transaction.replace(R.id.fragment_container, addFragment);
                transaction.addToBackStack("removeIt");
                transaction.commit();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction transaction =
                        getActivity().getSupportFragmentManager().beginTransaction();
                FragmentSearch fragmentSearch = new FragmentSearch();
                transaction.replace(R.id.fragment_container, fragmentSearch);
                transaction.addToBackStack("");
                transaction.commit();
            }
        });
    }

}


