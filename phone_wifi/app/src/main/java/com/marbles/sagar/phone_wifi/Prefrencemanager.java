package com.marbles.sagar.phone_wifi;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefrencemanager {
    private Context context;
    private SharedPreferences sharedPreferences;
    public  Prefrencemanager(Context context)
    {
        this.context = context;
        getSharedPrefrences();
    }

    public void getSharedPrefrences()
    {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_prefrence),Context.MODE_PRIVATE);

    }
    public void writePref(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_prefrence_key),"INIT_OK");
        editor.commit();

    }
public boolean checkPref(){
      boolean status =false;
      if(sharedPreferences.getString(context.getString(R.string.my_prefrence_key),"null").equals("null")){
          status = false;
      }
      else
      {
          status = true;
      }
      return status;
}

public void clearPref(){
        sharedPreferences.edit().clear().commit();
}
}
