package com.marbles.sagar.phone_wifi;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class content {
    Context context;
    content(Context context ){
        this.context=context;

    }
    public float getvalues(float rs, float unit){
        return rs*unit;
    }
}
