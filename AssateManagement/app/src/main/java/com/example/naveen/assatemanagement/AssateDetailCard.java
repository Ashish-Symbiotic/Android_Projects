package com.example.naveen.assatemanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.AssateHolder;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssateDetailCard extends BaseAdapter {

    List<AssateHolder> items;
    Activity activity;

    LayoutInflater inflater = null;
    AssateHolder assateHolder;

    public AssateDetailCard(Activity activity,List<AssateHolder> items)
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

    private class ViewHolder {
        PieChart pieChart;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.activity_assate_detail_card, null);
            holder = new ViewHolder();
            holder.pieChart=(PieChart) view.findViewById(R.id.pieChart);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        if (items.size() <= 0);

        else {
            assateHolder = items.get(position);

            try {
                if(assateHolder.getData().contains("Defactive"))
                {
                    JSONObject jsonObject = new JSONObject(assateHolder.getData());
                    List<PieEntry> entries;
                    List<Integer> colors;
                    PieDataSet dataSet;
                    PieData pieData;
                    entries=new ArrayList<>();
                    colors=new ArrayList<>();


                    entries.add(new PieEntry(jsonObject.getInt("Defactive"),0));
                    entries.add(new PieEntry(jsonObject.getInt("Issued"),1));
                    entries.add(new PieEntry(jsonObject.getInt("Total")-((jsonObject.getInt("Defactive")+jsonObject.getInt("Issued"))),2));


                    colors.add(Color.RED);
                    colors.add(Color.GREEN);
                    colors.add(Color.BLUE);

                    dataSet=new PieDataSet(entries,"Data");
                    dataSet.setColors(colors);
                    dataSet.setSliceSpace(2);

                    pieData=new PieData(dataSet);
                    holder.pieChart.setData(pieData);
                    holder.pieChart.setDescription(assateHolder.getTitle());
                    holder.pieChart.animateY(1300);
                    holder.pieChart.setHoleColor(Color.rgb(69,177,250));
                    holder.pieChart.setTouchEnabled(false);
                    holder.pieChart.setCenterText(jsonObject.getString("Total"));
                }
                else {

                    JSONObject jsonObject = new JSONObject(assateHolder.getData());
                    List<PieEntry> entries;
                    List<Integer> colors;
                    PieDataSet dataSet;
                    PieData pieData;
                    entries=new ArrayList<>();
                    colors=new ArrayList<>();


                    entries.add(new PieEntry(jsonObject.getInt("Expired"),0));
                    entries.add(new PieEntry(jsonObject.getInt("AboutTo"),1));
                    entries.add(new PieEntry(jsonObject.getInt("Total")-((jsonObject.getInt("Expired")+jsonObject.getInt("AboutTo"))),2));


                    colors.add(Color.RED);
                    colors.add(Color.GREEN);
                    colors.add(Color.BLUE);

                    dataSet=new PieDataSet(entries,"Data");
                    dataSet.setColors(colors);
                    dataSet.setSliceSpace(2);

                    pieData=new PieData(dataSet);
                    holder.pieChart.setData(pieData);
                    holder.pieChart.setDescription(assateHolder.getTitle());
                    holder.pieChart.animateY(1300);
                    holder.pieChart.setHoleColor(Color.rgb(69,177,250));
                    holder.pieChart.setTouchEnabled(false);
                    holder.pieChart.setCenterText(jsonObject.getString("Total"));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


        return view;
    }
}
