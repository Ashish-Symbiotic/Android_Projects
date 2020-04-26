package com.example.naveen.assatemanagement;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.naveen.assatemanagement.databaseConnection.AssateHolder;

import java.util.List;

public class esponseDegine extends BaseAdapter {
    List<AssateHolder> items;
    Activity activity;

    LayoutInflater inflater = null;
    AssateHolder assateHolder;

    public esponseDegine(Activity activity,List<AssateHolder> items)
    {
        this.activity=activity;
        this.items=items;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder
    {TextView ID,Employee,status;}

    @Override
    public View getView(int position,final View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.activity_esponse_degine, null);
            holder = new ViewHolder();
            holder.ID=(TextView) view.findViewById(R.id.textView33);
            holder.Employee=(TextView)view.findViewById(R.id.textView34);
            holder.status=(TextView)view.findViewById(R.id.textView35);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        if (items.size() <= 0)
            holder.ID.setText("No request found");
        else
        {
            assateHolder=items.get(position);
            holder.ID.setText(assateHolder.getTitle());
            holder.Employee.setText(assateHolder.getData());
            holder.status.setText(assateHolder.getStatus());
        }
        return view;
    }
}
