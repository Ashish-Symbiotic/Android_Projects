package com.example.gaurav.assetmanage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AssetReceving extends AppCompatActivity {
        EditText  Item_Desc,DOR,total,AssetLife;
    Spinner cate,sub_cate;
    RadioGroup rb1;
    RadioButton New ,Existing;
    Button Save;
    String Item_Desc1,DOR1,total1,AssetLife1;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_receving);
        Item_Desc=(EditText)findViewById(R.id.editText9);
        DOR=(EditText)findViewById(R.id.editText10);
        total=(EditText)findViewById(R.id.editText11);
        AssetLife=(EditText)findViewById(R.id.editText2);
        cate=(Spinner)findViewById(R.id.spinner);
        sub_cate=(Spinner)findViewById(R.id.spinner2);
        rb1=(RadioGroup)findViewById(R.id.Radiogrp);
        New=(RadioButton)findViewById(R.id.radioButton2);
        Existing=(RadioButton)findViewById(R.id.radioButton);
        Save=(Button)findViewById(R.id.SaveAsset);
        adapter=ArrayAdapter.createFromResource(this,R.array.Category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cate.setAdapter(adapter);
        cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void AssetsPhp(View view)
    {
        Item_Desc1=Item_Desc.getText().toString();
        DOR1=DOR.getText().toString();
        total1=total.getText().toString();
        AssetLife1=AssetLife.getText().toString();

    }
}
