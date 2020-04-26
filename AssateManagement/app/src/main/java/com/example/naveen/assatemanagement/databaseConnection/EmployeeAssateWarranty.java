package com.example.naveen.assatemanagement.databaseConnection;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EmployeeAssateWarranty extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url=new URL("https://assate-naveenmaan.c9users.io/POST/EmployAssateWarranty.php");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setDoInput(true);

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuilder builder=new StringBuilder();

            while((line=bufferedReader.readLine())!=null)
            {
                builder.append(line);
            }
            return builder.toString();

        } catch (MalformedURLException e) {
            return e.toString();
        } catch (IOException e) {
            return e.toString();
        }
    }
}
