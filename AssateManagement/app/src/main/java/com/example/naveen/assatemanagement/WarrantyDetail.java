package com.example.naveen.assatemanagement;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.AllAssateWarranty;
import com.example.naveen.assatemanagement.databaseConnection.AssateHolder;
import com.example.naveen.assatemanagement.databaseConnection.AssateSummary;
import com.example.naveen.assatemanagement.databaseConnection.AssateTypes;
import com.example.naveen.assatemanagement.databaseConnection.AssateWarrantySummary;
import com.example.naveen.assatemanagement.databaseConnection.GetPieData;
import com.example.naveen.assatemanagement.databaseConnection.GetPieEmployee;
import com.example.naveen.assatemanagement.databaseConnection.GetWarrantyAsset;
import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;
import com.example.naveen.assatemanagement.databaseConnection.TotalAssateSummary;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WarrantyDetail extends AppCompatActivity {

    ListView recyclerView;
    AssateDetailCard adapter;
    List<AssateHolder> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warranty_detail);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar13);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView13);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout13);
        setSupportActionBar(toolbar);

        if(new LoginDataTempStorage().getType().contains("admin"))
        {
            navigationView.inflateMenu(R.menu.admin_navigation);
        }
        else if(new LoginDataTempStorage().getType().contains("employee"))
        {
            navigationView.inflateMenu(R.menu.employee);

        }
        else
        {
            navigationView.inflateMenu(R.menu.keeper);

        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.isChecked())
                    ;
                else
                {
                    item.setCheckable(true);
                    drawerLayout.closeDrawers();
                    switch (item.getItemId())
                    {
                        case R.id.menu:
                            startActivity(new Intent(WarrantyDetail.this,MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(WarrantyDetail.this,Profile.class).putExtra("username","admin"));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(WarrantyDetail.this,AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(WarrantyDetail.this,EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(WarrantyDetail.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(WarrantyDetail.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(WarrantyDetail.this,LoginActivity.class));
                            FileOutputStream fos= null;
                            try {
                                fos = openFileOutput("type.dat", Context.MODE_PRIVATE);
                                fos.write("false".getBytes());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            finish();
                            return true;
                        default:
                            return true;
                    }
                }

                return true;
            }
        });

        recyclerView=(ListView) findViewById(R.id.recyclerView2);
        adapter=new AssateDetailCard(this,getData());
        recyclerView.setAdapter(adapter);

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AssateHolder assateHolder=data.get(position);

                final Dialog dialog=new Dialog(WarrantyDetail.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.assate_detail_by_slected);
                final ListView listView=(ListView)dialog.findViewById(R.id.listView6);
                PieChart chart=(PieChart)dialog.findViewById(R.id.pieChart2);
                List<PieEntry> entries=new ArrayList<PieEntry>();
                List<Integer> colors=new ArrayList<Integer>();

                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(assateHolder.getData());

                    entries.add(new PieEntry(jsonObject.getInt("Expired"),"Expired"));
                    entries.add(new PieEntry(jsonObject.getInt("AboutTo"),"About To"));
                    entries.add(new PieEntry(jsonObject.getInt("Total")-((jsonObject.getInt("Expired")+jsonObject.getInt("AboutTo"))),"Good"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                colors.add(Color.RED);
                colors.add(Color.GREEN);
                colors.add(Color.BLUE);

                PieDataSet dataSet=new PieDataSet(entries,"Data");
                dataSet.setColors(colors);

                PieData pieData=new PieData(dataSet);

                chart.setData(pieData);
                chart.setDescription(assateHolder.getTitle());
                chart.animateY(1300);
                chart.setHoleColor(Color.rgb(69,177,250));
                chart.setTouchEnabled(true);

                final JSONObject finalJsonObject = jsonObject;
                chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {

                        JSONObject getObject=new JSONObject();
                        if(e==null)
                        {
                            listView.setVisibility(View.GONE);
                        }
                        else
                        {
                            listView.setVisibility(View.VISIBLE);
                            try {
                                if(finalJsonObject.getString("Expired").equals(String.valueOf((int)e.getY())))
                                {
                                    getObject=new JSONObject(new GetWarrantyAsset().execute(assateHolder.getTitle(),"out").get());
                                }
                                else if(finalJsonObject.getString("AboutTo").equals(String.valueOf((int)e.getY())))
                                {
                                    getObject=new JSONObject(new GetWarrantyAsset().execute(assateHolder.getTitle(),"still").get());
                                }
                                else
                                {
                                    Toast.makeText(WarrantyDetail.this, "Hear", Toast.LENGTH_SHORT).show();
                                    getObject=new JSONObject(new GetWarrantyAsset().execute(assateHolder.getTitle(),"good").get());
                                }


                                List<String> adpt=new ArrayList<String>();

                                JSONArray array=getObject.getJSONArray("Data");

                                for(int i=0;i<array.length();i++)
                                {
                                    JSONObject temp=array.getJSONObject(i);
                                    adpt.add(temp.getString("ID"));
                                }

                                ArrayAdapter arrayAdapter=new ArrayAdapter(WarrantyDetail.this,android.R.layout.simple_list_item_1,adpt.toArray());

                                listView.setAdapter(arrayAdapter);


                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            } catch (ExecutionException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });


                try {
                    chart.setCenterText(jsonObject.getString("Total"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.show();

            }
        });

    }

    public List<AssateHolder> getData() {
        AssateHolder assaeteHolder;
        data=new ArrayList<>();


        try {
            JSONObject assateTypes=new JSONObject(new AssateTypes().execute().get());
            JSONArray arrayType=assateTypes.getJSONArray("Type");

            JSONObject assateObject=new JSONObject(new AllAssateWarranty().execute().get());
            JSONArray assateArray=assateObject.getJSONArray("WarrantyData");

            assaeteHolder=new AssateHolder("Total",new AssateWarrantySummary().execute().get());
            data.add(assaeteHolder);

            for(int i=0;i<arrayType.length();i++)
            {
                JSONObject assateTypeObject=arrayType.getJSONObject(i);

                JSONObject assateObjectTemp=assateArray.getJSONObject(i);

                JSONArray assateArrayTemp=assateObjectTemp.getJSONArray(assateTypeObject.getString("Type"));

                for(int j=0;j<assateArrayTemp.length();j++)
                {
                    JSONObject temp=assateArrayTemp.getJSONObject(j);
                    assaeteHolder=new AssateHolder(assateTypeObject.getString("Type"),temp.toString());
                    data.add(assaeteHolder);
                }

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
