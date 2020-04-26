package com.example.hp.addrecords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ShowMenu extends AppCompatActivity {
Button AddPayment,AddStock,AddItems,ShowDetails;
    String inte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);
        Intent intent=getIntent();
        inte =intent.getStringExtra("ID");
        AddPayment=(Button)findViewById(R.id.Payinfo);
        AddStock=(Button)findViewById(R.id.StockDetail);
        AddItems=(Button)findViewById(R.id.Item_Details);
        ShowDetails=(Button)findViewById(R.id.Show_Details1);



    }
    public void PayInfo(View view)
    {
        Toast.makeText(ShowMenu.this, inte, Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ShowMenu.this,Payment_Info.class);
        intent.putExtra("inte",inte);
        startActivity(intent);
    }
    public void AddStock1(View view)
    {

    }
    public void AddItem(View view)
    {

    }
    public  void ShowDetail(View view)
    {

    }
}
