package com.example.hp.addrecords;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import static com.example.hp.addrecords.MainPage.*;

public class Add_Detail extends AppCompatActivity {
EditText ID,Name,Address,Phone,Dealing;
    Button Addit;
    String s1="";

    String Phone1,ID1,Name1,Address1,Dealing1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__detail);
        ID=(EditText)findViewById(R.id.ID);
        Name=(EditText)findViewById(R.id.Name);
        Address=(EditText)findViewById(R.id.Address);
        Phone=(EditText)findViewById(R.id.Phone);
        Dealing=(EditText)findViewById(R.id.Dealing);
        Addit=(Button)findViewById(R.id.AddDetails);


    }
    public void AddDetail1(final View view)
    {
        Phone1=Phone.getText().toString();
        ID1=ID.getText().toString();
        Name1=Name.getText().toString();
        Dealing1=Dealing.getText().toString();
        Address1=Address.getText().toString();
        if(Name1.equals(s1))
        {
            show();
            Name.requestFocus();
        }
        else if(ID1.equals(s1))
        {
            show();
        }else if (Address1.equals(s1))
        {
            show();
        }else if(Phone1.equals(s1))
        {
            show();
        }
        else if(Dealing1.equals(s1))
        {
            show();
        }
        else {
            AddDetailsPhp addDetailsPhp = new AddDetailsPhp(this);
            addDetailsPhp.execute(ID.getText().toString(), Name.getText().toString(), Address.getText().toString(), Phone.getText().toString(), Dealing.getText().toString());
            try {
                if (addDetailsPhp.get().contains("Success")) {
                    Toast.makeText(Add_Detail.this, "Details Added", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(Add_Detail.this, MainPage.class));
                } else {
                    //Toast.makeText(Add_Detail.this, addDetailsPhp.get().toString(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setMessage("Error in Adding Details" + addDetailsPhp.get().toString());
                    alert.setCancelable(true);
                    alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                AddDetail1(view);
                            } catch (Exception e) {
                            }
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();


                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.setTitle("Error");
                    try {
                        alertDialog.show();
                    } catch (Exception e) {
                        Toast.makeText(Add_Detail.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(Add_Detail.this, e.toString(), Toast.LENGTH_SHORT).show();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    public void show()
    {
        Toast.makeText(Add_Detail.this, "Empty Field Detected", Toast.LENGTH_SHORT).show();
    }
    public void GeneId(View view) {
        Random random = new Random();
        int id = random.nextInt(10000);

        String ID2 = Name.getText().toString();
        if (ID2.equals("")) {
            Toast.makeText(this, "Set Name First", Toast.LENGTH_SHORT).show();
        } else {
            String ID1 = (ID2 + id).toString();

            ID.setText(ID1);
            Toast.makeText(this, "Get Your ID Ready", Toast.LENGTH_SHORT).show();
            CheckIdPhp checkIdPhp = new CheckIdPhp(this);
            checkIdPhp.execute(ID1);
            try {
                if (checkIdPhp.get().contains("Success")) {
                    GeneId(view);
                } else {
                    Toast.makeText(Add_Detail.this, "ID Generated", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }
    }
}
