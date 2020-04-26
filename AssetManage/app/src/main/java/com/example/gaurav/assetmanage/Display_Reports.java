package com.example.gaurav.assetmanage;

import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Display_Reports extends AppCompatActivity {
String json_String;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ListView listView;
ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__reports);
        contactsAdapter=new ContactsAdapter(this,R.layout.row_layout);
        listView=(ListView)findViewById(R.id.listView1);
        json_String=getIntent().getExtras().getString("json_data");
        listView.setAdapter(contactsAdapter);
        try {
            jsonObject=new JSONObject(json_String);
            jsonArray=jsonObject.getJSONArray("Server1");
            int count=0;
            String Uid,Pass,SecQ,Ans;
            while (count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                Uid=jo.getString("Uid");
                Pass=jo.getString("Pass");
                SecQ=jo.getString("SecQ");
                Ans=jo.getString("Ans");
                ContactsContract.Contacts contacts=new Contacts(Uid,Pass,SecQ,Ans);
                contactsAdapter.add(contacts);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
