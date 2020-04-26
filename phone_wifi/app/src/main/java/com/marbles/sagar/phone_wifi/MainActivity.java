package com.marbles.sagar.phone_wifi;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import junit.framework.Test;


public class MainActivity extends AppCompatActivity  {
    private boolean exit =false;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    public final static String id="Main";
Button btn;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hide();
            mDrawerLayout =findViewById(R.id.drawer_layout);
            mNavigationView=findViewById(R.id.nav_view);
            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            final ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            btn=findViewById(R.id.btndel);
       /* WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        final String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());*/
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_units:

                        main_frag f1= new main_frag();
                        f1.getApplication(getApplicationContext(),id);
                            //Toast_View(ip);
                        init_frag(f1);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.counter:
                        counter_one c_one= new counter_one();
                        init_frag(c_one);
                        mDrawerLayout.closeDrawers();
                        return  true;
                }
                return false;
            }
        });




    }


    public void hide(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


public void display(String msg)
{
    Toast.makeText(this, "msg", Toast.LENGTH_SHORT).show();
}
    public void init_frag(Fragment fragment){
        Toast.makeText(this, "init_frag called", Toast.LENGTH_SHORT).show();

        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame1,fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onBackPressed() {

        if(exit)
        {

            super.onBackPressed();
            hide();
            finish();
            System.exit(0);

            return ;

        }
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            exit=true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                exit=false;

                }
            },5*1000);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onRestoreInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
hide();
       /* if(MainActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            // Portrait Mode
        } else {
            // Landscape Mode
        }
*/

    }

    @Override
    protected void onRestart() {
        super.onRestart();
hide();


    }
    public void Toast_View(String ip)
    {
        Toast t= Toast.makeText(getApplicationContext(), ip+"", Toast.LENGTH_SHORT);
        t.setDuration(100*100000000);
        t.setGravity(Gravity.CENTER_HORIZONTAL,15,20);

        t.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
