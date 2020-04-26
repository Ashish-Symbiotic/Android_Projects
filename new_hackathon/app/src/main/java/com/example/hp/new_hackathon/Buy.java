package com.example.hp.new_hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Buy extends AppCompatActivity {
ArrayAdapter<String> adapter;
ArrayList<String> list;
String result1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        ListView listView=(ListView)findViewById(R.id.listView2);
        Intent intent= getIntent();
        result1 =intent.getStringExtra("mystr");
        getJSonData(result1);
        list=new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,R.layout.activity_buy,list);

    }
    public void getJSonData(String result2){
        try {
            JSONObject  jsonRootObject = new JSONObject(result2);
            JSONArray jsonArray = jsonRootObject.optJSONArray("Server1");
            for(int i=0;i<jsonArray.length();i++){
                try {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    list.add(jsonObject.getString("pro_id"));
                    list.add(jsonObject.getString("serice_type"));
                    list.add(jsonObject.getString("resource_type"));
                    list.add(jsonObject.getString("qty"));
                    list.add(jsonObject.getString("price"));
                    list.add(jsonObject.getString("date"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
