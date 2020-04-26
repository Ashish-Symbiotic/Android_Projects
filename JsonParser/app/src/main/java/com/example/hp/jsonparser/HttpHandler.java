package com.example.hp.jsonparser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class HttpHandler
{
    private static final String TAG=HttpHandler.class.getSimpleName();
    public HttpHandler(){}
    public String makeServiceCall(String reqUrl)
    {
        String response="";
        try {
            URL url=new URL(reqUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            InputStream in=new BufferedInputStream(conn.getInputStream());
            response=convertStreamToString(in);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
return response;
    }
    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader =new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb= new StringBuilder();
        try {
            while((line=reader.readLine())!=null)
            {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            is.close();

        }
        return sb.toString();
    }
}
