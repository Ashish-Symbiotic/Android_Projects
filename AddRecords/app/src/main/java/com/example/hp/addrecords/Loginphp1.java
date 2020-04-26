package com.example.hp.addrecords;

import android.content.Context;
import android.os.AsyncTask;

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

/**
 * Created by HP on 26-Sep-16.
 */
public class Loginphp1 extends AsyncTask<String,Void,String> {
    Context context;
    public Loginphp1(Context context){this.context=context;}
    @Override
    protected String doInBackground(String... params) {
        String User=params[0];
        String Pass=params[1];
        String result="";
        //String loginurl="http://10.0.0.2:80/login2.php";
        String loginurl="http://192.168.43.108/login2.php";
        try {
            URL  url= new URL(loginurl);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String Post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(User, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(Pass, "UTF-8");
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


    @Override
    protected void onPostExecute(String s) {
        String gh=s.trim();
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
