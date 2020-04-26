package com.example.hp.splashscreentest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by HP on 26-Dec-17.
 */

public class LauncherActivity extends Activity {
    private static int SPLASH_OUT=10000;
    Handler handler;
    Runnable runnable;
    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcheractivity);
      handler=  new Handler();
      runnable =new Runnable() {
          @Override
          public void run() {
              Intent i=new Intent(LauncherActivity.this,MainActivity.class);
              startActivity(i);
              finish();
          }
      };
      handler.postDelayed(runnable,SPLASH_OUT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }
}
