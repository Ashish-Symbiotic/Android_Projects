package com.hp.balancecalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button addbill,showt,paybutton,BalanceButt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addbill=(Button)findViewById(R.id.button);
    }
    public void AddBill(View view)
    {
      Intent i=new Intent(this,BillAct.class);
        startActivity(i);

    }



}
