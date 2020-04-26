package com.example.hp.jsonparser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private String TAG=MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private static  String url="http://192.168.56.1/json1.php";
    ArrayList<HashMap<String,String>> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList=new ArrayList<>();
        lv=(ListView)findViewById(R.id.list);
        new GetContacts().execute();

    }
    private class GetContacts extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please Wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String jsonStr= sh.makeServiceCall(url);
            Log.e(TAG,"Response From Url"+jsonStr);
            if(jsonStr!=null)
            {
                try {
                    JSONObject jsonObject=new JSONObject(jsonStr);
                    JSONArray jsonArray=jsonObject.getJSONArray("Server1");
                    for(int i =0; i<jsonArray.length();i++)
                    {
                        JSONObject c= jsonArray.getJSONObject(i);
                        String Uid=c.getString("Uid");
                        String Pass=c.getString("pass");
                        String SecuQ= c.getString("SecuQ");
                        String Ans=c.getString("Ans");
                        HashMap<String ,String> contact= new HashMap<>();
                        contact.put("Uid",Uid);
                        contact.put("Pass",Pass);
                        contact.put("SecuQ",SecuQ);
                        contact.put("Ans",Ans);
                        contactList.add(contact);



                    }
                } catch (final JSONException e) {
                    e.printStackTrace();

                }

            }
            else
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(pDialog.isShowing()){
                pDialog.dismiss();
            }
            ListAdapter adapter=new SimpleAdapter(MainActivity.this,contactList,R.layout.cells,new String[]{"Uid","Pass","SecuQ","Ans"},new int[]{R.id.uid,R.id.Pass,R.id.SecQ,R.id.Ans});
            lv.setAdapter(adapter);
        }
    }
}
