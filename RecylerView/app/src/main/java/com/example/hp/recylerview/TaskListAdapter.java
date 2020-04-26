package com.example.hp.recylerview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by HP on 30-Jan-18.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>{
    static String [] fake_data=new String[]{
            "one",
            "Two",
            "Three",
            "Four",
            "Five",
            "hsjdjhd"

    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_task,parent,false);
        return new ViewHolder(v);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Context context= holder.titleView.getContext();
        holder.titleView.setText(fake_data[position]);
        Picasso.with(context).load(getImageUrl(position)).
                into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OnEditTask)context).editTask(position);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage(holder.titleView.getText())
                        .setCancelable(true)
                        .setNegativeButton("Cancel",null)
                        .setPositiveButton(
                                "Delete",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteTask(context,i);
                                    }
                                }
                        ).show();
                return true;
            }
        });
    }
    void deleteTask(Context context,long i)
    {
        Log.d("TaskList","Called Deletd");
    }

    @Override
    public int getItemCount() {
        return fake_data.length;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView titleView;
        ImageView imageView;
        public ViewHolder(CardView card)
        {
            super(card);
            cardView=card;
            titleView=(TextView)card.findViewById(R.id.text1);
            imageView=(ImageView)card.findViewById(R.id.image);
        }
    }
    public static String getImageUrl(long task_id){
        return "http://lorempixel.com/600/400/cats/?fakeId=" + task_id;
    }
}
