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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.naveen.assatemanagement.databaseConnection.GetEcn;
import com.example.naveen.assatemanagement.databaseConnection.GetEmployeeID;
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

public class ReturnNotice extends AppCompatActivity {

    AutoCompleteTextView searchText;
    Button serchButton;
    ImageView imageView;

    TextView employeeid,name,email,phone;
    ListView list;
    List<String> listitem=new ArrayList<>();
    List<String> searchList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_notice);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar11);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView11);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout11);
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
                            startActivity(new Intent(ReturnNotice.this,MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(ReturnNotice.this,Profile.class).putExtra("username","admin"));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(ReturnNotice.this,AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(ReturnNotice.this,EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(ReturnNotice.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(ReturnNotice.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(ReturnNotice.this,LoginActivity.class));
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


        employeeid=(TextView)findViewById(R.id.textView21);
        name=(TextView)findViewById(R.id.textView20);
        phone=(TextView)findViewById(R.id.textView22);
        email=(TextView)findViewById(R.id.textView23);


        imageView=(ImageView)findViewById(R.id.imageView);

        list=(ListView)findViewById(R.id.listView3);

        searchText=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);

        try {
            JSONObject jsonObject=new JSONObject(new GetEmployeeID().execute("").get());
            JSONArray jsonArray=jsonObject.getJSONArray("ID");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                searchList.add(jsonObject1.getString("ID"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,searchList.toArray());

        searchText.setAdapter(arrayAdapter);

        serchButton=(Button)findViewById(R.id.button3);

        searchText.setVisibility(View.VISIBLE);
        serchButton.setVisibility(View.VISIBLE);

        serchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchText.getText().toString().isEmpty())
                {
                    searchText.setError("Field must required!");
                }
                else
                {
                    searchText.setVisibility(View.GONE);
                    serchButton.setVisibility(View.GONE);
                    try {
                        String ecn=new GetEcn().execute(searchText.getText().toString()).get();

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



                        list.setAdapter(getAdapter());

                        name.setVisibility(View.VISIBLE);
                        employeeid.setVisibility(View.VISIBLE);
                        phone.setVisibility(View.VISIBLE);
                        email.setVisibility(View.VISIBLE);
                        list.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public ListAdapter getAdapter() {
        return new ArrayAdapter(this,android.R.layout.simple_list_item_1,listitem.toArray());
    }
}
