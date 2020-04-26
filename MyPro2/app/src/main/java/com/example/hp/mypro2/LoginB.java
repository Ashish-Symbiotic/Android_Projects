package com.example.hp.mypro2;


        import android.content.Context;
        import android.os.AsyncTask;
        import android.view.Gravity;
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

/**
 * Created by HP on 10-Dec-17.
 */

public class LoginB extends AsyncTask<String,String,String> {

    Context context;
    public LoginB (Context context){
        this.context=context;
    }
    @Override
    protected String doInBackground(String... strings) {
        //String loginurl = "https://webhost965.000webhostapp.com/login2.php";
      //String loginurl="http://127.0.0.1:80/new_login2.php";
     // String loginurl="http://10.0.2.2:80/new_login2.php";
        String loginurl="http://192.168.43.108/new_login2.php";
        String User = strings[0];
        String Pass=strings[1];
        String result="";

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
            while ((line = bufferedReader.readLine()) != null)
            {
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
    protected void onPreExecute(){
        super.onPreExecute();
    }
    @Override
    protected void onProgressUpdate(String... values)
    {
        super.onProgressUpdate(values);
    }
   protected void onPostExecute(String result)
    {
        String gh=result.trim();
        super.onPostExecute(gh);



    }
}

