package com.example.gaurav.assetmanage;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class RegisterIt extends AppCompatActivity {
    EditText name, usrname,password,Repass,email,userid;
    String count;    Button b1;
    TextView t1;
    String name1,username1,password1,Repass1,email1,userid1;
    String s1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_it);
        name=(EditText)findViewById(R.id.editText3);
        usrname=(EditText)findViewById(R.id.editText4);
        password=(EditText)findViewById(R.id.editText5);
        Repass=(EditText)findViewById(R.id.editText6);
        userid=(EditText)findViewById(R.id.editText7);
        email=(EditText)findViewById(R.id.editText8);
        b1=(Button)findViewById(R.id.RegButton);
        t1=(TextView)findViewById(R.id.textView);
    }
    public  void Regi(final View view)
    {
        try{
            name1=name.getText().toString();
            username1=usrname.getText().toString();
            password1=password.getText().toString();
            Repass1=Repass.getText().toString();
            userid1=userid.getText().toString();
            email1=email.getText().toString();


            if(name1.equals(s1))
            {
                show();
            }
            else if(username1.equals(s1))
            {
                show();
            }
            else if(password1.equals(s1))
            {
                show();
            }
            else if(Repass1.equals(s1))
            {
                show();
            }
            else if(!(Repass1.equals(password1)))
            {
                t1.setText("Password Does not match");
            }
            else if (userid1.equals(s1))
            {
                show();
            }
            else if(email1.equals(s1))
            {
                show();
            }
            else
            {
                t1.setText("");
                RegisterBack registerBack= new RegisterBack(this);
                registerBack.execute(name1,username1,password1,userid1,email1);
                try {

                    if(registerBack.get().contains("Success"))
                    {
                        Toast.makeText(RegisterIt.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    else
                    {
                        AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
                        alert.setMessage(registerBack.get().toString());
                        alert.setCancelable(true);
                        alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    Regi(view);


                                } catch (Exception e) {
                                    Toast.makeText(RegisterIt.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog=alert.create();
                        alertDialog.setTitle(" Signup Error");
                        alertDialog.show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }}catch(Exception e){
            Toast.makeText(RegisterIt.this,e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void show()
    {
        t1.setText("Empty Field Detectd");
    }
    public Context getActivity()
    {
        return this;
    }
}

