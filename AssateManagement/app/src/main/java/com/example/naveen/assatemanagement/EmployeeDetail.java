package com.example.naveen.assatemanagement;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

public class EmployeeDetail extends AppCompatActivity {

    ListAdapter listAdapter;
    List<EmployeData> builder = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar7);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView7);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout7);
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
                            startActivity(new Intent(EmployeeDetail.this,MainActivity.class));
                            finish();
                            return  true;
                        case R.id.profile:
                            startActivity(new Intent(EmployeeDetail.this,Profile.class).putExtra("username","admin"));
                            finish();
                            return true;
                        case R.id.discription:
                            startActivity(new Intent(EmployeeDetail.this,AssateDiscription.class));
                            finish();
                            return true;
                        case R.id.employee:
                            startActivity(new Intent(EmployeeDetail.this,EmployeeDetail.class));
                            finish();
                            return true;
                        case R.id.returnAssate:
                            startActivity(new Intent(EmployeeDetail.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.requestAssate:
                            startActivity(new Intent(EmployeeDetail.this,ReturnNotice.class));
                            finish();
                            return true;
                        case R.id.logout:
                            startActivity(new Intent(EmployeeDetail.this,LoginActivity.class));
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

        listView = (ListView) findViewById(R.id.listView4);

        try {
            JSONObject jsonObject = new JSONObject(new GetEmployeeID().execute("").get());
            JSONArray jsonArray = jsonObject.getJSONArray("ID");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                builder.add(new EmployeData(jsonObject1.getString("ID"), jsonObject1.getString("Name")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        listAdapter = new ListAdapter(this, builder);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmployeData employeData=builder.get(position);

                final Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.employ_detail_dialog);

                TextView employeeid=(TextView)dialog.findViewById(R.id.textView37);
                TextView name=(TextView)dialog.findViewById(R.id.textView36);
                TextView phone=(TextView)dialog.findViewById(R.id.textView38);
                TextView email=(TextView)dialog.findViewById(R.id.textView39);

                ListView list=(ListView)dialog.findViewById(R.id.listView9);

                try {
                    JSONObject jsonObject=new JSONObject(new ProfileDetail().execute(employeData.getId()).get());

                    name.setText(jsonObject.getString("Name"));
                    employeeid.setText(jsonObject.getString("ECN"));
                    phone.setText(jsonObject.getString("Phone"));
                    email.setText(jsonObject.getString("Email"));


                    JSONArray jsonArray=jsonObject.getJSONArray("Assate");

                    List<String> listitem=new ArrayList<>();

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        listitem.add(jsonObject1.getString("AssateID"));
                    }

                    ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listitem.toArray());

                    list.setAdapter(arrayAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

    }

    public Context getActivity() {
        return this;
    }


    private class ListAdapter extends BaseAdapter {
        List<EmployeData> data;
        Activity activity;
        EmployeData employeeData = null;
        LayoutInflater inflater = null;

        public ListAdapter(Activity activity, List<EmployeData> data) {
            this.activity = activity;
            this.data = data;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView id, name;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            ViewHolder holder;

            if (view == null) {
                view = inflater.inflate(R.layout.custom_employee_view, null);
                holder = new ViewHolder();
                holder.id = (TextView) view.findViewById(R.id.textView24);
                holder.name = (TextView) view.findViewById(R.id.textView25);
                view.setTag(holder);
            } else
                holder = (ViewHolder) view.getTag();

            if (data.size() <= 0)
                holder.id.setText("No Data");
            else {
                employeeData = data.get(position);
                holder.id.setText(employeeData.getId());
                holder.name.setText(employeeData.getName());
            }


            return view;
        }
    }

    private class EmployeData {
        private String id;
        private String name;

        public EmployeData() {
        }

        public EmployeData(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
