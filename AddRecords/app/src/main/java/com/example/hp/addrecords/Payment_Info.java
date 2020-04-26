package com.example.hp.addrecords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Payment_Info extends AppCompatActivity {
EditText Payid,PartyName,Payment,Date;
    Button AddPayInfo;
String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__info);
        Intent intent=getIntent();
       id= intent.getStringExtra("inte");
        Payid=(EditText)findViewById(R.id.PayId);
        Payid.setText(id);
        Toast.makeText(Payment_Info.this, id, Toast.LENGTH_SHORT).show();
    }
}
