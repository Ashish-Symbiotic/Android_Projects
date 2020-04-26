package com.example.naveen.assatemanagement;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;
import com.example.naveen.assatemanagement.databaseConnection.LoginTry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class AlertLogin extends Activity {

    private static final boolean AUTO_HIDE = true;


    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alert_login);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        final EditText username=(EditText)findViewById(R.id.editText2);
        final EditText password=(EditText)findViewById(R.id.editText3);

        final String file="logIn.dat";

        try {
            JSONObject loginObjecct=new JSONObject(new BufferedReader(new InputStreamReader(openFileInput(file))).readLine());

            if(!loginObjecct.toString().isEmpty())
            {
                username.setText(loginObjecct.getString("username"));
                password.setText(loginObjecct.getString("password"));
            }
            else if(!new LoginDataTempStorage().getUsername().isEmpty())
            {
                username.setText(new LoginDataTempStorage().getUsername());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Button login=(Button)findViewById(R.id.dummy_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty())
                    username.setError("Field must required!");
                else if(password.getText().toString().isEmpty())
                    password.setError("Feld must required!");
                else
                {
                    try {
                        LoginDataTempStorage loginTempData=new LoginDataTempStorage(username.getText().toString(),password.getText().toString());
                        String result=new LoginTry().execute(username.getText().toString(),password.getText().toString()).get();
                        if(result.contains("Username"))
                        {
                            username.setError("Username wrong");
                            username.setText("");
                            password.setText("");
                        }
                        else if(result.contains("Not"))
                        {
                            Snackbar.make(AlertLogin.super.getWindow().getDecorView(),"Server are offline.",Snackbar.LENGTH_LONG).show();
                        }
                        else if(result.contains("Password"))
                        {
                            password.setText("");
                            password.setError("Password wrong");
                        }
                        else
                        {
                            JSONObject jsonObject=new JSONObject().put("username",username.getText().toString()).put("password",password.getText().toString());
                            FileOutputStream fos=openFileOutput(file, Context.MODE_PRIVATE);
                            fos.write(jsonObject.toString().getBytes());
                            loginTempData.setType(result);
                            fos.flush();
                            fos.close();
                            fos = openFileOutput("type.dat", Context.MODE_PRIVATE);
                            fos.write("true".getBytes());
                            fos.flush();
                            fos.close();
                            startActivity(new Intent(AlertLogin.this,MainActivity.class));
                            finish();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ImageButton register=(ImageButton)findViewById(R.id.imageButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AlertLogin.this,Register.class));
            }
        });

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

}
