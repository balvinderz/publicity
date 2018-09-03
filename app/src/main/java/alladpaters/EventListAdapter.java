package alladpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

import models.Event;
import vinay.com.publicity.R;

/**
 * Created by Patil on 26-08-2016.
 */
public class EventListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Event> events;

    public EventListAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events= events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        MyViewHolder myViewHolder=null;
        if(row==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.event_single_row,parent,false);
            myViewHolder=new MyViewHolder(row);
            row.setTag(myViewHolder);
        }else{
            myViewHolder= (MyViewHolder) row.getTag();

        }
        Event event=events.get(position);
        myViewHolder.text1.setText(event.getName());
        myViewHolder.text2.setText(" "+event.getCost());

        return row;
    }
    class MyViewHolder{
        TextView text1;
        TextView text2;

        public MyViewHolder(View v) {
            text1= (TextView) v.findViewById(R.id.textViewEvent1);
            text2= (TextView) v.findViewById(R.id.textViewEvent2);
        }
    }
}
