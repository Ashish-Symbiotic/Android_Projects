package com.example.hp.addrecords;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Edit_Detail extends AppCompatActivity {
EditText id,name,address,dealing,phone;
    Button update;
    String srt,ID,name1,phone1,dealing1,address1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__detail);
        id=(EditText)findViewById(R.id.ID1);
        name=(EditText)findViewById(R.id.Name1);
        address=(EditText)findViewById(R.id.Address1);
        dealing=(EditText)findViewById(R.id.Dealing1);
        phone=(EditText)findViewById(R.id.Phone1);
        Intent intent=getIntent();
      srt=  intent.getStringExtra("mystr");

        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(srt);
            JSONArray jsonArray=jsonObject.getJSONArray("Server1");
            for(int i =0; i<jsonArray.length();i++)
            {
                JSONObject c= jsonArray.getJSONObject(i);
                 ID=c.getString("ID");
                name1=c.getString("Name");
                 dealing1=c.getString("Dealing");
                phone1= c.getString("Phone");
                address1=c.getString("Address");

            }
            id.setText(ID);
            name.setText(name1);
            phone.setText(phone1);
            dealing.setText(dealing1);
            address.setText(address1);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void UpdateRecord(final View view)
    {
       ID=id.getText().toString();
        name1=name.getText().toString();
        phone1=phone.getText().toString();
        dealing1=dealing.getText().toString();
        address1=address.getText().toString();
        UpdatePhp updatePhp=new UpdatePhp(this);
        updatePhp.execute(ID,name1,phone1,dealing1,address1);
        try {
            if(updatePhp.get().contains("Success"))
            {
                Toast.makeText(Edit_Detail.this, "Records Updated", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this,MainPage.class));
            }
            else
            {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Error in Adding Details" + updatePhp.get().toString());
                alert.setCancelable(true);
                alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            UpdateRecord(view);
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
                    Toast.makeText(Edit_Detail.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
