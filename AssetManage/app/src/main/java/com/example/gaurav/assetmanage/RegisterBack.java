package com.example.gaurav.assetmanage;

/**
 * Created by Gaurav on 12-Sep-16.
 */
import android.content.Context;
import android.os.AsyncTask;
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


public class RegisterBack extends AsyncTask<String,Void,String>
{
    Context ctx;
    RegisterBack (Context context)
    {
        this.ctx=context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String name=params[0];
        String username=params[1];
        String password=params[2];
        String userid=params[3];
        String email=params[4];
        String result="";
        String SUrl="http://assetmanage.netai.net/Signup.php";
        try {
            URL url= new URL(SUrl);
            HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream= httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String PostDtat= URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")+"&"+ URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8")+ "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            bufferedWriter.write(PostDtat);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
        } catch (MalformedURLException e) {
            Toast.makeText(ctx,e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(ctx, "Another Toast for input Output\n"+e.toString(), Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        String e=s;
        e.trim();
        Toast.makeText(ctx, e, Toast.LENGTH_SHORT).show();
        super.onPostExecute(s);


    }
}
