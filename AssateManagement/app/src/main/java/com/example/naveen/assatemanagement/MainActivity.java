package com.example.naveen.assatemanagement;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.AllAssateWarranty;
import com.example.naveen.assatemanagement.databaseConnection.AssateSummary;
import com.example.naveen.assatemanagement.databaseConnection.AssateTypes;
import com.example.naveen.assatemanagement.databaseConnection.EmployeeSummary;
import com.example.naveen.assatemanagement.databaseConnection.FIndRequest;
import com.example.naveen.assatemanagement.databaseConnection.FindResponse;
import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;
import com.example.naveen.assatemanagement.databaseConnection.LoginTry;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    CardView assetDiscription,employ,profile,returnAssate,request;
    TextView assetTitle;

    PieChart assateData;

    List<PieEntry> entries;
    List<Integer> colors;
    List<String> string;
    Legend legend;
    PieDataSet dataSet;
    PieData pieData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);




        final String file="logIn.dat";


        try {
            JSONObject loginObjecct=new JSONObject(new BufferedReader(new InputStreamReader(openFileInput(file))).readLine());

            boolean test=!loginObjecct.toString().isEmpty()&&new BufferedReader(new InputStreamReader(openFileInput("type.dat"))).readLine().contains("true");


            if(!loginObjecct.toString().isEmpty()&&new BufferedReader(new InputStreamReader(openFileInput("type.dat"))).readLine().contains("true"))
            {
                if(!new LoginTry().execute(loginObjecct.getString("username"),loginObjecct.getString("password")).get().contains("error")&&!new LoginTry().execute(loginObjecct.getString("username"),loginObjecct.getString("password")).get().contains("Not"))
                {
                    new LoginDataTempStorage(loginObjecct.getString("username"),loginObjecct.getString("password"),new LoginTry().execute(loginObjecct.getString("username"),loginObjecct.getString("password")).get());
                    FileOutputStream fos = openFileOutput("type.dat", Context.MODE_PRIVATE);
                    fos.write("true".getBytes());

                }
                else
                {
                    startActivity(new Intent(MainActivity.this,AlertLogin.class));FileOutputStream fos = openFileOutput("type.dat", Context.MODE_PRIVATE);
                    fos.write("true".getBytes());
                }
            }
            else if(!new LoginDataTempStorage().getUsername().isEmpty()&&!new LoginDataTempStorage().getPassword().isEmpty())
            {
                if(!new LoginTry().execute(loginObjecct.getString("username"),loginObjecct.getString("password")).get().contains("error"))
                {
                    new LoginDataTempStorage(loginObjecct.getString("username"),loginObjecct.getString("password"),new LoginTry().execute(loginObjecct.getString("username"),loginObjecct.getString("password")).get());
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,AlertLogin.class));
                }
            }
            else
            {
                startActivity(new Intent(MainActivity.this,AlertLogin.class));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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


        if(new LoginDataTempStorage().getType().contains("admin"))
        {
            try {
                JSONObject jsonObject=new JSONObject(new FIndRequest().execute().get());
                JSONArray jsonArray=jsonObject.getJSONArray("Request");

                for(int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObjecttemp=jsonArray.getJSONObject(i);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                    builder.setSmallIcon(R.drawable.ic_request);
                    builder.setContentTitle("Asset Request");
                    builder.setContentText(jsonObjecttemp.getString("AssateID"+" to "+jsonObjecttemp.getString("IssuedTo")));

                    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(99, builder.build());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else if(new LoginDataTempStorage().getType().contains("keeper"))
        {
            try {
                JSONObject jsonObject=new JSONObject(new FindResponse().execute().get());
                JSONArray jsonArray=jsonObject.getJSONArray("Request");

                for(int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObjecttemp=jsonArray.getJSONObject(i);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                    builder.setSmallIcon(R.drawable.ic_request);
                    builder.setContentTitle("Asset Response");
                    builder.setContentText(jsonObjecttemp.getString("AssateID"+" to "+jsonObjecttemp.getString("IssuedTo")));

                    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(99, builder.build());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        /*else
        {
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.ic_approved);
            builder.setContentTitle("Notification");
            builder.setContentText("sdf");

            NotificationManager mNotifyMgr =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(99,builder.build());
        }*/



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
                            startActivity(new Intent(getActivity(),MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(getActivity(),Profile.class).putExtra("username",new LoginDataTempStorage().getUsername()));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(getActivity(),AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(getActivity(),EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(getActivity(),ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(getActivity(),ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(getActivity(),LoginActivity.class));
                            try {
                                FileOutputStream fos = openFileOutput("type.dat", Context.MODE_PRIVATE);
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



        assetDiscription=(CardView)findViewById(R.id.cardView);
        employ=(CardView)findViewById(R.id.cardView2);
        profile=(CardView)findViewById(R.id.cardView3);
        returnAssate=(CardView)findViewById(R.id.cardView4);
        request=(CardView)findViewById(R.id.cardView5);

        if(new LoginDataTempStorage().getType().contains("admin"))
        {

        }
        else if(new LoginDataTempStorage().getType().contains("employee"))
        {
            assetDiscription.setVisibility(View.GONE);
            employ.setVisibility(View.GONE);
        }
        else
        {
            employ.setVisibility(View.GONE);
        }


        assetTitle=(TextView)findViewById(R.id.textView2);
        assateData=(PieChart) findViewById(R.id.textView);
        assetTitle.setText(R.string.discription);

        try {
            JSONObject jsonObject=new JSONObject(new AssateSummary().execute().get());
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
            assateData.animateY(1300);
            assateData.setTouchEnabled(false);
            assateData.setCenterText(jsonObject.getString("Total"));
            assateData.setHoleColor(Color.rgb(69,177,250));


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assetDiscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AssateDiscription.class));
            }
        });

        employ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EmployeeDetail.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Profile.class).putExtra("username",new LoginDataTempStorage().getUsername()));

            }
        });

        returnAssate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ReturnNotice.class));
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RequestAssate.class));
            }
        });



    }

    public Context getActivity() {
        return this;
    }
}
