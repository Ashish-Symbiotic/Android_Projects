package com.example.hp.new_hackathon;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class Login_Main extends AppCompatActivity {
    EditText User,Pass;
    Button Click;
    String message="";
    public static final String msg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        User=(EditText)findViewById(R.id.Login_id);
        Pass=(EditText)findViewById(R.id.pass);
        Click=(Button)findViewById(R.id.button);

        Intent intent = getIntent();

                message=intent.getStringExtra(Login_activity.EXTRA_MESSAGE);

    }

    public void Login_php1(View view) {
        Intent res_pro1=new Intent(getApplicationContext(),EnterP_Home.class);

        startActivity(res_pro1);
        Login_php Login_php= new Login_php(getApplicationContext());
        AsyncTask<String,Void,String> asyncTask=Login_php.execute(User.getText().toString(),Pass.getText().toString(),message.toString());

        try {
            if(asyncTask.get().contains("Success"))
            {
                if(message.equals("Resource Provider"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),Res_ProHome.class);
                    res_pro.putExtra(msg,message);
                    startActivity(res_pro);
                }
                if(message.equals("EnterPrise"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),EnterP_Home.class);
                    res_pro.putExtra(msg,message);
                    startActivity(res_pro);
                }
                if(message.equals("Bank"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),Bank_Home.class);
                    res_pro.putExtra(msg,message);
                    startActivity(res_pro);
                }
                if(message.equals("Law authority"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),LawA_Home.class);
                    res_pro.putExtra(msg,message);
                    startActivity(res_pro);
                }
                if(message.equals(" New User"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),User_home.class);
                    res_pro.putExtra(msg,message);
                    startActivity(res_pro);
                }

                if(message.equals("Resarch Oragnistion"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),Resch_ProHome.class);
                    res_pro.putExtra(msg,message);
                    startActivity(res_pro);
                }

            }
            else
            {
                Toast.makeText(this, "Login Error"+ asyncTask.get().toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    }

