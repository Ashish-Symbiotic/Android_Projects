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
 * Created by HP on 07-Oct-16.
 */
public class AddDetailsPhp extends AsyncTask<String,Void,String> {
    Context context;
    public AddDetailsPhp(Context context)
    {
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
       String result="";
        String Id=params[0];
        String Name=params[1];
        String Address=params[2];
        String Phone=params[3];
        String Dealing=params[4];
        String AddUrl="http://192.168.56.1/AddDetails.php";
        try {
            URL url=new URL(AddUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream =httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data= URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(Id,"UTF-8")+"&"+URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(Name,"UTF-8")+"&"+URLEncoder.encode("Address","UTF-8")+"="+URLEncoder.encode(Address,"UTF-8")+"&"+URLEncoder.encode("Phone","UTF-8")+  "="  +URLEncoder.encode(Phone,"UTF-8")+ "&" +URLEncoder.encode("Dealing","UTF-8")+"="+URLEncoder.encode(Dealing,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream= httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                result+=line;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
