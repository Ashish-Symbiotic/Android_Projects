package com.example.hp.splashscreentest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar=getSupportActionBar();
        BottomNavigationView navigationView=(BottomNavigationView)findViewById(R.id.navbi);

        navigationView.setOnNavigationItemSelectedListener(onnevi);
        actionBar.setTitle("Shop");
        loadfrag(new Home_Frag());
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onnevi= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch(item.getItemId()){
                case R.id.Home:
                    actionBar.setTitle("Home");
                    fragment =new Home_Frag();
                    loadfrag(fragment);
                    return true;
                case R.id.Sale:
                    actionBar.setTitle("Sale");
                    fragment =new Sale_Frag();
                    loadfrag(fragment);
                    return true;
                case R.id.Purchase:
                    actionBar.setTitle("Purchase");
                    fragment =new Purchase_frag();
                    loadfrag(fragment);
                    return true;
                case R.id.Bill:
                    actionBar.setTitle("Bill");
                    fragment =new Bill_Frag();
                    loadfrag(fragment);
                    return true;
                case R.id.Search:
                    actionBar.setTitle("Search");
                    fragment =new Search_Frag();
                    loadfrag(fragment);
                    return true;
            }

            return false;
        }
    };
    private void loadfrag(Fragment fragment){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_con,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
}
