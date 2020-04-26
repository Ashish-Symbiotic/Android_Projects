package com.example.naveen.assatemanagement;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.AddNewAssate;
import com.example.naveen.assatemanagement.databaseConnection.AssateSubType;
import com.example.naveen.assatemanagement.databaseConnection.AssateTypes;
import com.example.naveen.assatemanagement.databaseConnection.CardViewHolder;
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

public class AddAssate extends AppCompatActivity {

    Spinner type,subType;
    EditText totalAssate;
    Button add,cancel;

    List<String> typeList=new ArrayList<>();
    List<String> subTypeList;
    String sendType,sendSubType;

    ArrayAdapter<String> subArrayAdapter;

    List<CardViewHolder> assateList=new ArrayList<CardViewHolder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assate);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView2);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout2);
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
                            startActivity(new Intent(AddAssate.this,MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(AddAssate.this,Profile.class).putExtra("username","admin"));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(AddAssate.this,AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(AddAssate.this,EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(AddAssate.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(AddAssate.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(AddAssate.this,LoginActivity.class));
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

        type=(Spinner)findViewById(R.id.spinner);
        subType=(Spinner)findViewById(R.id.spinner2);

        totalAssate=(EditText)findViewById(R.id.editText);

        add=(Button)findViewById(R.id.button);
        cancel=(Button)findViewById(R.id.button2);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAssate.this,AssateDiscription.class));
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalAssate.getText().toString().isEmpty())
                {
                    totalAssate.setError("Field must required!");
                }
                else
                {
                    try {
                        String result=new AddNewAssate().execute(sendType,sendSubType,totalAssate.getText().toString()).get();

                        ViewDialog viewDialog=new ViewDialog();
                        viewDialog.showDialog(getActivity(),result);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        try {
            JSONObject jsonObject=new JSONObject(new AssateTypes().execute().get());
            JSONArray jsonArray=jsonObject.getJSONArray("Type");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                typeList.add(jsonObject1.getString("Type"));
            }

            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typeList.toArray(new String[typeList.size()]));
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            type.setAdapter(arrayAdapter);

            type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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

    private class ViewDialog
    {
        public void showDialog(final Activity activity, String result)
        {
            final Dialog dialog=new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.assate_add_alert);

            ListView listView=(ListView)dialog.findViewById(R.id.listView5);
            Button okDilog=(Button)dialog.findViewById(R.id.button4);

            DisplayCard adapter=new DisplayCard(getActivity(),getData(result));
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CardViewHolder selectedItem=assateList.get(position);
                    final Dialog selectedDialog=new Dialog(activity);
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

            okDilog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                    startActivity(new Intent(AddAssate.this,AssateDiscription.class));
                }
            });

            dialog.show();
        }
    }


    private void setAdapter() {
        subArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,subTypeList.toArray(new String[subTypeList.size()]));
    }


    private List<CardViewHolder> getData(String result)
    {
        CardViewHolder cardViewHolder;
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.getJSONArray("Assate");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                cardViewHolder=new CardViewHolder(jsonObject1.getString("AssateID"),jsonObject1.getString("SubType")+", "+jsonObject1.getString("AssateName"),jsonObject1.getString("BoughtDate"),jsonObject1.getString("Status"));
                assateList.add(cardViewHolder);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return assateList;
    }

    public Activity getActivity() {
        return this;
    }
}
