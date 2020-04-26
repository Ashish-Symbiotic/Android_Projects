package com.example.hp.frag1;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements Frag_Shared.OnClickListener1 {
Button b1,b2;
    SharedPreferences sharedPreferences;
    public static final String Name="name_key";
    public static final String Pass1="pass_key";
    public static final String Views1="views_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.B1);
        b2=(Button)findViewById(R.id.B2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Frag_Shared f1= new Frag_Shared();

                Fragment fa= fm.findFragmentById(R.id.fr1);
                if(fa==null)
                {
                    ft.add(R.id.fr1,f1);
                }
                else {
                    ft.replace(R.id.fr1, f1);
                    ft.commit();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Frag2 f2= new Frag2();
                ft.replace(R.id.fr2,f2);


                ft.commit();
            }
        });
    }


    @Override
    public void onchanged1(String name1, String pass2, String viesd3) {
        sharedPreferences=this.getSharedPreferences("pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Name, name1);
        editor.putString(Pass1, pass2);
        editor.putString(Views1,viesd3);
        editor.commit();
        Toast.makeText(this, "Main Activity"+name1, Toast.LENGTH_SHORT).show();
    }
}
