package com.example.naveen.assatemanagement;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.GetEcn;
import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;
import com.example.naveen.assatemanagement.databaseConnection.ProfileDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Profile extends AppCompatActivity {

    TextView employeeid,name,email,phone;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar9);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView9);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout9);
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
                            startActivity(new Intent(Profile.this,MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(Profile.this,Profile.class).putExtra("username","admin"));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(Profile.this,AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(Profile.this,EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(Profile.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(Profile.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(Profile.this,LoginActivity.class));
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

        employeeid=(TextView)findViewById(R.id.textView17);
        name=(TextView)findViewById(R.id.textView16);
        phone=(TextView)findViewById(R.id.textView18);
        email=(TextView)findViewById(R.id.textView19);

        list=(ListView)findViewById(R.id.listView);

        Intent intent=getIntent();

        try {
            String ecn=new GetEcn().execute(intent.getStringExtra("username")).get();

            JSONObject jsonObject=new JSONObject(new ProfileDetail().execute(ecn).get());

            name.setText(jsonObject.getString("Name"));
            employeeid.setText(jsonObject.getString("ECN"));
            phone.setText(jsonObject.getString("Phone"));
            email.setText(jsonObject.getString("Email"));

            List<String> listitem=new ArrayList<>();

            JSONArray jsonArray=jsonObject.getJSONArray("Assate");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                listitem.add(jsonObject1.getString("AssateID"));
            }

            ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listitem.toArray());

            list.setAdapter(arrayAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
