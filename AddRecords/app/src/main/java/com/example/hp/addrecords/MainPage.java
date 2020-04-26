package com.example.hp.addrecords;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainPage extends AppCompatActivity {
Button AddDetails,EditDetails,ViewDetails,AddSale,ViewSale,EditSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        AddDetails=(Button)findViewById(R.id.AddPerson);
        EditDetails=(Button)findViewById(R.id.Edit_Person);
        ViewDetails=(Button)findViewById(R.id.Show_Details);
        AddSale=(Button)findViewById(R.id.Add_Sales);
        ViewSale=(Button)findViewById(R.id.View_Sales);
        EditSale=(Button)findViewById(R.id.Edit_Sales);
    }
public void AddDetail(View view)
{
    startActivity(new Intent(getApplicationContext(),Add_Detail.class));
}
    public void EditDetail(View view)
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Enter Details");
        final EditText Id=new EditText(this);
        alert.setView(Id);
        alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           String Id1=Id.getEditableText().toString();
              SearchPhp searchPhp=new SearchPhp();
                AsyncTask<String,Void,String> asyncTask=searchPhp.execute(Id1);
                try {
                    if(!searchPhp.get().equals(""))
                    {
                        Intent intent=new Intent(MainPage.this,Edit_Detail.class);
                        intent.putExtra("mystr",searchPhp.get());
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(MainPage.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           dialog.cancel();
            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();
    }

        public void ShowDetail(View view)
        {
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Enter Details");
            final EditText Id=new EditText(this);
            alert.setView(Id);
            alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String Id1=Id.getEditableText().toString();
                    CheckIdPhp checkIdPhp=new CheckIdPhp(getApplicationContext());
                    AsyncTask<String,Void,String> asyncTask=checkIdPhp.execute(Id1);
                    try {
                        if(checkIdPhp.get().contains("Success"))
                        {
                            Intent intent=new Intent(MainPage.this,ShowMenu.class);
                            intent.putExtra("ID",Id.getEditableText().toString());
                            Toast.makeText(MainPage.this, Id1, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(MainPage.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=alert.create();
            try{alertDialog.show();}catch (Exception e){
                Toast.makeText(MainPage.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    public void AddSales(View view)
    {
        startActivity(new Intent(getApplicationContext(),Add_Sales.class));
    }
    public void ViewSales(View view)
    {
        startActivity(new Intent(getApplicationContext(),View_Sales.class));
    }
    public void EditSales(View view)
    {
        startActivity(new Intent(getApplicationContext(),Edit_Sales.class));
    }
    public class SearchPhp extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
           String result="";
            String id=params[0];
            String loginurl="http://192.168.56.1/Search.php";
            try {
                URL url= new URL(loginurl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String Post_data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") ;
                bufferedWriter.write(Post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  result;
        }
    }
}
/*Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
                    res_pro.putExtra(EXTRA_MESSAGE, value);
                    startActivity(res_pro);


*/


/* if(value.equals("Resource Provider"))
                {
                        
                        if(result.equals("NEW"))
                        {
                            Intent res_pro=new Intent(getApplicationContext(),Signup_Resoruce.class);
                            res_pro.putExtra(EXTRA_MESSAGE, value);
                            startActivity(res_pro);
                        }
                        else if(result.equals("Existing"))
                        {
                            Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
                            res_pro.putExtra(EXTRA_MESSAGE, value);
                            startActivity(res_pro);
                        }
                        else
                        {
                            Toast.makeText(Login_activity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                }
                if(value.equals("EnterPrise"))
                {
                   //Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
                   // res_pro.putExtra(EXTRA_MESSAGE, value);
                    //startActivity(res_pro);

                    if(result.equals("NEW"))
                    {
                        Intent res_pro=new Intent(getApplicationContext(),Signup_Enter.class);

                        startActivity(res_pro);
                    }
                    else if(result.equals("Existing"))
                    {
                        Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
                        res_pro.putExtra(EXTRA_MESSAGE, value);
                        startActivity(res_pro);
                    }
                    else
                    {
                        Toast.makeText(Login_activity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
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
                if(value.equals("User"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
                    res_pro.putExtra(EXTRA_MESSAGE, value);
                    startActivity(res_pro);
                }

                if(value.equals("Resarch Oragnistion"))
                {
                    Intent res_pro=new Intent(getApplicationContext(),Login_Main.class);
                    res_pro.putExtra(EXTRA_MESSAGE, value);
                    startActivity(res_pro);
                }
*/