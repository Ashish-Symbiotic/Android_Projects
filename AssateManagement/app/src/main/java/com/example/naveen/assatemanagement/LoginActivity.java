package com.example.naveen.assatemanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.naveen.assatemanagement.databaseConnection.LoginDataTempStorage;
import com.example.naveen.assatemanagement.databaseConnection.LoginTry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username=(EditText)findViewById(R.id.editText10);
        final EditText password=(EditText)findViewById(R.id.editText11);

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


        Button login=(Button)findViewById(R.id.dummy_button2);

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
                            Snackbar.make(LoginActivity.super.getWindow().getDecorView(),"Server are offline.",Snackbar.LENGTH_LONG).show();
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
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
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

        ImageButton register=(ImageButton)findViewById(R.id.imageButton3);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Register.class));
            }
        });
    }
}
