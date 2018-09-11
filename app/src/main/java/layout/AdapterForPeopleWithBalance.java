package layout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import models.Entry;
import vinay.com.publicity.R;


public class AdapterForPeopleWithBalance extends RecyclerView.Adapter<AdapterForPeopleWithBalance.MyViewHolder> {
    ArrayList<Entry> entries;
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name,balance,no,email;

        public MyViewHolder(View view)
        {
            super(view);
name=view.findViewById(R.id.entryname);
balance=view.findViewById(R.id.entrybalance);
no=view.findViewById(R.id.entryphone);
email=view.findViewById(R.id.entryemail);
        }
    }
    public  AdapterForPeopleWithBalance(ArrayList<Entry> entries)
    {
this.entries=entries;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int  viewtype)
    {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.balanceadapter,parent,false);
        return  new MyViewHolder(itemview);


    }
    @Override
    public  void onBindViewHolder(final  MyViewHolder holder,int position)
    {
        final  Entry entry=entries.get(position);
        holder.name.setText("name: "+entry.getName());
        holder.balance.setText("balance : "+ String.valueOf(entry.getBalance()));
        holder.no.setText("phone no : "+entry.getMobile());
        holder.email.setText("email id : "+entry.getEmail());

    }
    @Override
    public  int getItemCount()
    {
        return  entries.size();
    }
}
