package com.example.hp.myapp1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Color_Frag.OnColorChangeListener {
LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=(LinearLayout)findViewById(R.id.main_layout) ;
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        Color_Frag color_frag= new Color_Frag();
        ft.add(R.id.frag_cont,color_frag);
        ft.commit();

    }

    @Override
    public void colorChanged(String color_name) {
        if(color_name.equals("RED"))
        {
            linearLayout.setBackgroundColor(Color.RED);
        }
        else if(color_name.equals("GREEN"))
        {
            linearLayout.setBackgroundColor(Color.GREEN);
        }
        else if(color_name.equals("BLUE"))
        {
            linearLayout.setBackgroundColor(Color.BLUE);
        }
        else
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        }
    }
}
