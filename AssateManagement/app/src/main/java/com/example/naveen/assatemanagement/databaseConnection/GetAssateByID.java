package com.example.naveen.assatemanagement.databaseConnection;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class GetAssateByID extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {

        try {
            URL url=new URL("https://assate-naveenmaan.c9users.io/POST/AssateByID.php");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));


            String post= URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8");
            bufferedWriter.write(post);
            bufferedWriter.flush();
            bufferedWriter.close();

            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder=new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            return e.toString();
        } catch (IOException e) {
            return e.toString();
        }
    }
}