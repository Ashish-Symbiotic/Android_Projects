package com.example.naveen.assatemanagement;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.CardViewHolder;
import com.example.naveen.assatemanagement.databaseConnection.CustomData;
import com.example.naveen.assatemanagement.databaseConnection.TrackAssate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Custom extends AppCompatActivity {

    Button from,to;

    private int mYear, mMonth, mDay;

    TextView fromDate,toDate;

    DisplayCard adapter;


    List<CardViewHolder> data=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        from=(Button)findViewById(R.id.button9);
        to=(Button)findViewById(R.id.button10);
        fromDate=(TextView)findViewById(R.id.fromdate);
        toDate=(TextView)findViewById(R.id.fromdate);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(Custom.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                fromDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                from.setText(fromDate.getText().toString());

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(Custom.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                toDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                                to.setText(toDate.getText().toString());

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

            }
        });

        Button search=(Button)findViewById(R.id.button11);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fromDate.getText().toString().isEmpty()&&toDate.getText().toString().isEmpty())
                {
                    from.setError("Field must required!");
                    to.setError("Field must required!");
                }
                else if(fromDate.getText().toString().isEmpty())
                {
                    from.setError("Field must required!");
                }
                else if(toDate.getText().toString().isEmpty())
                {
                    to.setError("Field must required!");
                }
                else if(!from.getText().toString().equals(to.getText().toString()))
                {
                    final Dialog dialog=new Dialog(Custom.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.customserrchview);
                    final ListView recyclerView=(ListView)dialog.findViewById(R.id.listView10);

                    adapter=new DisplayCard(Custom.this,getData());
                    recyclerView.setAdapter(adapter);

                    recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            CardViewHolder selectedItem=data.get(position);
                            final Dialog selectedDialog=new Dialog(Custom.this);
                            selectedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            selectedDialog.setContentView(R.layout.assate_list_select);

                            TextView selectedID=(TextView)selectedDialog.findViewById(R.id.textView26);
                            TextView selectedType=(TextView)selectedDialog.findViewById(R.id.textView27);
                            TextView selectedBoughtDate=(TextView)selectedDialog.findViewById(R.id.textView28);
                            TextView selectedStatus=(TextView)selectedDialog.findViewById(R.id.textView29);

                            selectedID.setText(selectedItem.getAssateID());
                            selectedType.setText(selectedItem.getAssateName());
                            selectedBoughtDate.setText(selectedItem.getBoughtDate());
                            selectedStatus.setText(selectedItem.getStatus());

                            selectedDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    selectedDialog.dismiss();
                                }
                            });

                            selectedDialog.show();
                        }
                    });

                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
                else
                {
                    Toast.makeText(Custom.this, "Both date must be different!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public List<CardViewHolder> getData() {
        CardViewHolder cardView;

        try {
            JSONObject jsonObject=new JSONObject(new CustomData().execute(fromDate.getText().toString(),to.getText().toString()).get());

            JSONArray array=jsonObject.getJSONArray("Assate");
            for(int i=0;i<array.length();i++)
            {
                JSONObject dataObject=array.getJSONObject(i);
                cardView=new CardViewHolder(dataObject.getString("AssateID"),dataObject.getString("AssateName"),dataObject.getString("BoughtDate"),dataObject.getString("Status"));
                data.add(cardView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return data;
    }
}
