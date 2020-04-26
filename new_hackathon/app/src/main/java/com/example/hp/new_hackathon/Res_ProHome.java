package com.example.hp.new_hackathon;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Res_ProHome extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private Spinner spinner,spinner2;
    private ArrayList<String> students;
    private JSONArray result;
    ArrayAdapter<String> datac;
    String result1="",spiner_2_result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_pro_home);
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);
spinner2=(Spinner)findViewById(R.id.spinner4);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Healthcare");
        categories.add("Jam");
        categories.add("Jute Bag");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        List<String> jam= new ArrayList<String>();
        jam.add("Apple");
        jam.add("Sugar");
        jam.add("Container Jar");
        jam.add("Citric acid");
        List<String> jute_bag=new ArrayList<String>();
        jute_bag.add("Jute Fabric");
        jute_bag.add("Dye");
        jute_bag.add("Swing Thread");
        jute_bag.add("Printing Gum");
        List<String> health = new ArrayList<String>();
        health.add("Ambulance");
        health.add("Medicine");
        health.add("Imaging Machine");
        health.add("General Equipments");
        if(item.equals("Jam"))
        {
            datac=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,jam);
            datac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(datac);
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(item.equals("Healthcare"))
        {
            datac=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,health);
            datac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(datac);
        }
        if(item.equals("Jute Bag"))
        {
            datac=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,jute_bag);
            datac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(datac);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
