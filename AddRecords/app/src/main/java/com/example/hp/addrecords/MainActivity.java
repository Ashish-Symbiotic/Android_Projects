package com.example.hp.addrecords;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
EditText User,Pass;
    Button Click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User=(EditText)findViewById(R.id.UserName);
        Pass=(EditText)findViewById(R.id.Password);
        Click=(Button)findViewById(R.id.button);

    }
    public void Loginphp(View view)
    {
        Loginphp1 loginphp1= new Loginphp1(getApplicationContext());
       AsyncTask<String,Void,String> asyncTask=loginphp1.execute(User.getText().toString(),Pass.getText().toString());

        try {
            if(asyncTask.get().contains("Success"))
            {
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainPage.class));

            }
            else
            {
                Toast.makeText(MainActivity.this, "Login Error"+ asyncTask.get().toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
