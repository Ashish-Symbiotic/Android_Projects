package com.example.naveen.assatemanagement;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.naveen.assatemanagement.databaseConnection.CardViewHolder;

import java.util.List;

public class DisplayCard extends BaseAdapter{

    List<CardViewHolder> data;
    Activity activity;
    LayoutInflater inflater = null;

    CardViewHolder cardViewHolder;

    public DisplayCard(Activity activity,List<CardViewHolder> data)
    {
        this.activity=activity;
        this.data=data;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{TextView AssateID,AssateName,BoughtDate,Status;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.activity_display_card, null);
            holder = new ViewHolder();
            holder.AssateID=(TextView)view.findViewById(R.id.textView11);
            holder.AssateName=(TextView)view.findViewById(R.id.textView12);
            holder.BoughtDate=(TextView)view.findViewById(R.id.textView14);
            holder.Status=(TextView)view.findViewById(R.id.textView13);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        if (data.size() <= 0)
            holder.AssateID.setText("No Data");
        else {
            cardViewHolder = data.get(position);
            holder.AssateID.setText(cardViewHolder.getAssateID());
            holder.BoughtDate.setText(cardViewHolder.getBoughtDate());
            holder.Status.setText(cardViewHolder.getStatus());
            holder.AssateName.setText(cardViewHolder.getAssateName());
        }


        return view;
    }
}
