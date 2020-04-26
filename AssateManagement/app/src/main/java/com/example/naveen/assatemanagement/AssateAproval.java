package com.example.naveen.assatemanagement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.naveen.assatemanagement.databaseConnection.AssateHolder;
import com.example.naveen.assatemanagement.databaseConnection.AssateSummary;
import com.example.naveen.assatemanagement.databaseConnection.AssateTypes;
import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;
import com.example.naveen.assatemanagement.databaseConnection.RequestAssateDetail;
import com.example.naveen.assatemanagement.databaseConnection.RequestAssates;
import com.example.naveen.assatemanagement.databaseConnection.RequestResponse;
import com.example.naveen.assatemanagement.databaseConnection.TotalAssateSummary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AssateAproval extends AppCompatActivity {

    ProvalDesign adapter;
    List<AssateHolder> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assate_aproval);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar3);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView3);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout3);
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
                            startActivity(new Intent(AssateAproval.this,MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(AssateAproval.this,Profile.class).putExtra("username","admin"));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(AssateAproval.this,AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(AssateAproval.this,EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(AssateAproval.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(AssateAproval.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(AssateAproval.this,LoginActivity.class));
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

        ListView listView=(ListView)findViewById(R.id.listView7);

        adapter=new ProvalDesign(this,getData());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AssateHolder assateHolder=data.get(position);
                final Dialog dialog=new Dialog(AssateAproval.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.request_grant_view);

                TextView data=(TextView)dialog.findViewById(R.id.textView32);
                Button reject=(Button)dialog.findViewById(R.id.button6);
                Button accept=(Button)dialog.findViewById(R.id.button7);

                data.setText("Is "+assateHolder.getTitle()+" issued to "+assateHolder.getData());

                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            new RequestResponse().execute("Reject",assateHolder.getData()).get();
                            dialog.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            new RequestResponse().execute("Accepted",assateHolder.getData()).get();
                            dialog.dismiss();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });

                dialog.show();

            }
        });

    }


    public List<AssateHolder> getData() {
        AssateHolder assaeteHolder;
        data=new ArrayList<>();


        try {
            JSONObject assateTypes=new JSONObject(new RequestAssateDetail().execute().get());
            JSONArray arrayType=assateTypes.getJSONArray("Request");

            for(int i=0;i<arrayType.length();i++)
            {
                JSONObject assateTypeObject=arrayType.getJSONObject(i);
                assaeteHolder=new AssateHolder(assateTypeObject.getString("AssateID"),assateTypeObject.getString("IssuedTo"));
                data.add(assaeteHolder);
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
