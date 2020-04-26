package com.example.naveen.assatemanagement;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.naveen.assatemanagement.databaseConnection.CardViewHolder;
import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;
import com.example.naveen.assatemanagement.databaseConnection.TrackAssate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchAssate extends AppCompatActivity {

    ListView recyclerView;
    DisplayCard adapter;


    List<CardViewHolder> data=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_assate);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar12);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView12);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout12);
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
                            startActivity(new Intent(SearchAssate.this,MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(SearchAssate.this,Profile.class).putExtra("username","admin"));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(SearchAssate.this,AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(SearchAssate.this,EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(SearchAssate.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(SearchAssate.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(SearchAssate.this,LoginActivity.class));
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

        recyclerView=(ListView) findViewById(R.id.recyclerView4);
        adapter=new DisplayCard(this,getData());
        recyclerView.setAdapter(adapter);

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardViewHolder selectedItem=data.get(position);
                final Dialog selectedDialog=new Dialog(SearchAssate.this);
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


    }



    public List<CardViewHolder> getData() {
        CardViewHolder cardView;

        try {
            JSONObject jsonObject=new JSONObject(new TrackAssate().execute("").get());
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
