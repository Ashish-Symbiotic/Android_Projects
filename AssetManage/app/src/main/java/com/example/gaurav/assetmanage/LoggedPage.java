package com.example.gaurav.assetmanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoggedPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_page);
    }
    public void startAct(View view)
    {
        startActivity(new Intent(this,AssetReceving.class));
    }
    public void startWar(View view)
    {
        startActivity(new Intent(this,Warranty.class));
    }
public void startR(View view)
{
    startActivity(new Intent(this,Reports.class));
}
}
