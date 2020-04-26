package com.example.hp.newapp1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.reg_user);
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        LoginFragment loginFragment=new LoginFragment();
        ft.add(R.id.frag_con,loginFragment);
        ft.commit();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager= getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                Reg_Lay_Frag reg_lay_frag=new Reg_Lay_Frag();
                fragmentTransaction.replace(R.id.frag_con,reg_lay_frag).addToBackStack("1");
                fragmentTransaction.commit();
            }
        });
    }
}
