package com.example.naveen.assatemanagement;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.AddNewAssate;
import com.example.naveen.assatemanagement.databaseConnection.AssateIssueRequest;
import com.example.naveen.assatemanagement.databaseConnection.AssateSubType;
import com.example.naveen.assatemanagement.databaseConnection.AssateTypes;
import com.example.naveen.assatemanagement.databaseConnection.CardViewHolder;
import com.example.naveen.assatemanagement.databaseConnection.GetAssateByID;
import com.example.naveen.assatemanagement.databaseConnection.GetEmployeeID;
import com.example.naveen.assatemanagement.databaseConnection.GetListAssate;
import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RequestAssate extends AppCompatActivity {

    AutoCompleteTextView employee;
    List<String> list=new ArrayList<>();

    Spinner assatetype,subType;

    ListView listView;

    List<String> typeList=new ArrayList<>();
    List<String> assateList;
    List<String> subTypeList;
    String sendType,sendSubType;


    ArrayAdapter<String> subArrayAdapter,listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_assate);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar10);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView10);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout10);
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
                            startActivity(new Intent(RequestAssate.this,MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(RequestAssate.this,Profile.class).putExtra("username","admin"));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(RequestAssate.this,AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(RequestAssate.this,EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(RequestAssate.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(RequestAssate.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(RequestAssate.this,LoginActivity.class));
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

        employee=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);


        try {
            JSONObject jsonObject=new JSONObject(new GetEmployeeID().execute("").get());
            JSONArray jsonArray=jsonObject.getJSONArray("ID");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                list.add(jsonObject1.getString("ID"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,list.toArray());

        employee.setAdapter(arrayAdapter);

        assatetype=(Spinner)findViewById(R.id.spinner3);
        subType=(Spinner)findViewById(R.id.spinner4);

        listView=(ListView)findViewById(R.id.listView2);

        try {
            JSONObject jsonObject=new JSONObject(new AssateTypes().execute().get());
            JSONArray jsonArray=jsonObject.getJSONArray("Type");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                typeList.add(jsonObject1.getString("Type"));
            }

            ArrayAdapter<String> arrayAdaptertype=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typeList.toArray(new String[typeList.size()]));
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            assatetype.setAdapter(arrayAdaptertype);

            assatetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sendType=typeList.get(position).toString();
                    subTypeList=new ArrayList<>();
                    try {
                        JSONObject subTypeJson=new JSONObject(new AssateSubType().execute(sendType).get());
                        JSONArray subTypeArray=subTypeJson.getJSONArray("SubType");
                        for(int i=0;i<subTypeArray.length();i++)
                        {
                            JSONObject object=subTypeArray.getJSONObject(i);
                            subTypeList.add(object.getString("SubType"));
                        }

                        setAdapter();
                        subArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subType.setAdapter(subArrayAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            subType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sendSubType=subTypeList.get(position).toString();

                    assateList=new ArrayList<>();

                    try {
                        JSONObject jsonObject1=new JSONObject(new GetListAssate().execute(sendSubType).get());

                        JSONArray jsonArray1=jsonObject1.getJSONArray("AssateList");

                        for(int i=0;i<jsonArray1.length();i++)
                        {
                            JSONObject assateListObject=jsonArray1.getJSONObject(i);
                            assateList.add(assateListObject.getString("ID"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    listView.setAdapter(getListAdapter());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(employee.getText().toString().isEmpty())
                    {
                        employee.setError("Field must required!");
                    }
                    else
                    {
                        CardViewHolder selectedItem;
                        try {
                            final JSONObject dataObject=new JSONObject(new GetAssateByID().execute(assateList.get(position)).get());

                            selectedItem=new CardViewHolder(dataObject.getString("AssateID"),dataObject.getString("AssateName"),dataObject.getString("BoughtDate"),dataObject.getString("Status"));
                            final Dialog selectedDialog=new Dialog(RequestAssate.this);
                            selectedDialog.setTitle("Issued to "+employee.getText().toString());
                            selectedDialog.setContentView(R.layout.assate_list_select);

                            TextView selectedID=(TextView)selectedDialog.findViewById(R.id.textView26);
                            TextView selectedType=(TextView)selectedDialog.findViewById(R.id.textView27);
                            TextView selectedBoughtDate=(TextView)selectedDialog.findViewById(R.id.textView28);
                            TextView selectedStatus=(TextView)selectedDialog.findViewById(R.id.textView29);
                            Button issued=(Button)selectedDialog.findViewById(R.id.button5);
                            issued.setVisibility(View.VISIBLE);
                            issued.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        if(new AssateIssueRequest().execute(employee.getText().toString(),dataObject.getString("AssateID")).get().contains("true"))
                                        {
                                            Toast.makeText(RequestAssate.this, "Request Sucessful", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(RequestAssate.this, "Request Failed", Toast.LENGTH_SHORT).show();
                                        }
                                        selectedDialog.dismiss();
                                        finish();
                                        startActivity(new Intent(RequestAssate.this,MainActivity.class));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

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

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public ArrayAdapter<String> getListAdapter() {
        listAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,assateList.toArray(new String[assateList.size()]));
        return listAdapter;
    }

    private void setAdapter() {
        subArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,subTypeList.toArray(new String[subTypeList.size()]));
    }

}
