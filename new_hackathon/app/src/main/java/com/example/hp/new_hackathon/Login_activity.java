package com.example.hp.new_hackathon;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Login_activity extends AppCompatActivity  {
    public static final String EXTRA_MESSAGE = "";
    ListView listView;
    TextView textView;
    String[] listItem;
    String new1="new";
    String exist="exist";
    String result="";
    String value="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        listView=(ListView)findViewById(R.id.listView1);
        textView=(TextView)findViewById(R.id.textView);
        listItem = getResources().getStringArray(R.array.array_technology);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                value=adapter.getItem(position);
                if(value.equals("Resource Provider"))
                {

                   getButtonName();
                }
                if(value.equals("EnterPrise"))
                {
                   getButtonName();
                }
                if(value.equals("Bank"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
                    res_pro.putExtra(EXTRA_MESSAGE, value);
                    startActivity(res_pro);
                }
                if(value.equals("Law authority"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
                    res_pro.putExtra(EXTRA_MESSAGE, value);
                    startActivity(res_pro);
                }
                if(value.equals("End User"))
                {
                    getButtonName();
                }

                if(value.equals("Resarch Oragnistion"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
                    res_pro.putExtra(EXTRA_MESSAGE, value);
                    startActivity(res_pro);
                }





            }
        });
    }
    public void getButtonName(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Login_activity.this);
builder.setMessage("Select Your type");
builder.setPositiveButton("Existing", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
       result="Existing";
        if(value.equals("Resource Provider"))
        {

            Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
            res_pro.putExtra(EXTRA_MESSAGE, value);
            startActivity(res_pro);
        }
        if(value.equals("EnterPrise"))
        {
            Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
             res_pro.putExtra(EXTRA_MESSAGE, value);
            startActivity(res_pro);

        }

        if(value.equals("End User"))
        {
            Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
            res_pro.putExtra(EXTRA_MESSAGE, value);
            startActivity(res_pro);
        }


    }
});
builder.setNegativeButton("New", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        result="New";
        if(value.equals("Resource Provider"))
        {

            Intent res_pro=new Intent(getApplicationContext(),Signup_Resoruce.class);
            res_pro.putExtra(EXTRA_MESSAGE, value);
            startActivity(res_pro);
        }
        if(value.equals("EnterPrise"))
        {
            Intent res_pro=new Intent(getApplicationContext(),Signup_Enter.class);
            res_pro.putExtra(EXTRA_MESSAGE, value);
            startActivity(res_pro);

        }

        if(value.equals("End User"))
        {
            Intent res_pro=new Intent(getApplicationContext(),Signup_User.class);
            res_pro.putExtra(EXTRA_MESSAGE, value);
            startActivity(res_pro);
        }
    }
});
builder.setCancelable(false);
        AlertDialog alertDialog=builder.create();
        try{alertDialog.show();}catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}

