package com.example.naveen.assatemanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.AssateSummary;
import com.example.naveen.assatemanagement.databaseConnection.AssateWarrantySummary;
import com.example.naveen.assatemanagement.databaseConnection.EmployeeAssateWarranty;
import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;
import com.example.naveen.assatemanagement.databaseConnection.TrackAssate;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AssateDiscription extends AppCompatActivity {


    CardView assate,warranty,addAssate,employee,track,request,response,custom;
    PieChart assateData,warrantyData,employeeData;
    List<PieEntry> entries;
    List<Integer> colors;
    List<String> string;
    Legend legend;
    PieDataSet dataSet;
    PieData pieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assate_discription);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar5);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView5);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout5);
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
                            startActivity(new Intent(AssateDiscription.this,MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(AssateDiscription.this,Profile.class).putExtra("username","admin"));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(AssateDiscription.this,AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(AssateDiscription.this,EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(AssateDiscription.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(AssateDiscription.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(AssateDiscription.this,LoginActivity.class));
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


        assate=(CardView)findViewById(R.id.cardView6);
        warranty=(CardView)findViewById(R.id.cardView7);
        addAssate=(CardView)findViewById(R.id.cardView8);
        employee=(CardView)findViewById(R.id.cardView9);
        track=(CardView)findViewById(R.id.cardView10);
        request=(CardView)findViewById(R.id.cardView11);
        response=(CardView)findViewById(R.id.cardView12);
        custom=(CardView)findViewById(R.id.cardView13);

        if(new LoginDataTempStorage().getType().contains("admin"))
        {
            response.setVisibility(View.GONE);
        }
        else
        {
            request.setVisibility(View.GONE);
        }

        assateData=(PieChart) findViewById(R.id.textView7);
        warrantyData=(PieChart) findViewById(R.id.textView8);
        employeeData=(PieChart) findViewById(R.id.textView9);


        try {


            AssateSummary assateGetData=new AssateSummary();
            AsyncTask<String,Void,String> assateResult=assateGetData.execute();
            JSONObject jsonObject=new JSONObject(assateResult.get());
            entries=new ArrayList<>();
            colors=new ArrayList<>();
            string=new ArrayList<>();


            entries.add(new PieEntry(jsonObject.getInt("Issued"),0));
            entries.add(new PieEntry(jsonObject.getInt("Defactive"),1));
            entries.add(new PieEntry(jsonObject.getInt("Total")-((jsonObject.getInt("Issued")+jsonObject.getInt("Defactive"))),2));

            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.BLUE);

            string.add("Issued");
            string.add("Defactive");
            string.add("In Stock");

            legend=assateData.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
            legend.setExtra(colors,string);

            dataSet=new PieDataSet(entries,"Data");
            dataSet.setColors(colors);
            dataSet.setSliceSpace(2);

            pieData=new PieData(dataSet);
            assateData.setData(pieData);
            assateData.setDescription("Asset");
            assateData.animateY(1300);
            assateData.setTouchEnabled(false);
            assateData.setCenterText(jsonObject.getString("Total"));
            assateData.setHoleColor(Color.rgb(69,177,250));

            AssateWarrantySummary assateWarrantySummary=new AssateWarrantySummary();
            AsyncTask<String,Void,String> warrantySummary=assateWarrantySummary.execute();
            JSONObject jsonObject1=new JSONObject(warrantySummary.get());
            entries=new ArrayList<>();
            colors=new ArrayList<>();
            string=new ArrayList<>();


            entries.add(new PieEntry(jsonObject1.getInt("Expired"),0));
            entries.add(new PieEntry(jsonObject1.getInt("AboutTo"),1));
            entries.add(new PieEntry(jsonObject1.getInt("Total")-((jsonObject1.getInt("AboutTo")+jsonObject1.getInt("Expired"))),2));

            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.BLUE);

            string.add("Expired");
            string.add("AboutTo");
            string.add("Good");

            legend=warrantyData.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
            legend.setExtra(colors,string);

            dataSet=new PieDataSet(entries,"Data");
            dataSet.setColors(colors);
            dataSet.setSliceSpace(2);

            pieData=new PieData(dataSet);
            warrantyData.setData(pieData);
            warrantyData.setDescription("Warranty");
            warrantyData.animateY(1300);
            warrantyData.setTouchEnabled(false);
            warrantyData.setHoleColor(Color.rgb(69,177,250));
            warrantyData.setCenterText(jsonObject1.getString("Total"));

            EmployeeAssateWarranty employeeAssateWarranty=new EmployeeAssateWarranty();
            AsyncTask<String,Void,String> employeeSummary=employeeAssateWarranty.execute();
            JSONObject jsonObject2=new JSONObject(employeeSummary.get());
            entries=new ArrayList<>();
            colors=new ArrayList<>();
            string=new ArrayList<>();


            entries.add(new PieEntry(jsonObject2.getInt("Expired"),0));
            entries.add(new PieEntry(jsonObject2.getInt("AboutTo"),1));
            entries.add(new PieEntry(jsonObject2.getInt("Total")-((jsonObject2.getInt("AboutTo")+jsonObject2.getInt("Expired"))),2));

            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.BLUE);

            string.add("Expired");
            string.add("AboutTo");
            string.add("Good");

            legend=employeeData.getLegend();
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
            legend.setExtra(colors,string);

            dataSet=new PieDataSet(entries,"Data");
            dataSet.setColors(colors);
            dataSet.setSliceSpace(2);

            pieData=new PieData(dataSet);
            employeeData.setData(pieData);
            employeeData.setDescription("Employee");
            employeeData.animateY(1300);
            employeeData.setTouchEnabled(false);
            employeeData.setHoleColor(Color.rgb(69,177,250));
            employeeData.setCenterText(jsonObject2.getString("Total"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssateDiscription.this,AssateDetail.class));
            }
        });

        warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssateDiscription.this,WarrantyDetail.class));
            }
        });

        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssateDiscription.this,Employee.class));
            }
        });

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssateDiscription.this,SearchAssate.class));
            }
        });

        addAssate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssateDiscription.this,AddAssate.class));
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssateDiscription.this,AssateAproval.class));
            }
        });

        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssateDiscription.this,GetResponse.class));
            }
        });

        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssateDiscription.this,Custom.class));
            }
        });

    }
}
