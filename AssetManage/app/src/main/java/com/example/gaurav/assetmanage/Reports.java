package com.example.gaurav.assetmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Reports extends AppCompatActivity {
String Json_String;
    Button getJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        getJson=(Button)findViewById(R.id.getJson);
    }
    public  void getJSon(View view)
    {

    }
}
