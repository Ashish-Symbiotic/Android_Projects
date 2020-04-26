package com.marbles.sagar.phone_wifi;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class counter_one extends Fragment {
    private SharedPreferences sharedPreferences;
    ImageButton allB,w_B,p_B;
    TextView allT,w_T,b_T,an_T;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.counter_layout,container,false);

        return v;
    }
}
