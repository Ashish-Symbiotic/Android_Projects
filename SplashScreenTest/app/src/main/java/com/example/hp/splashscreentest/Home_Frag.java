package com.example.hp.splashscreentest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by HP on 27-Dec-17.
 */

public class Home_Frag extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.home_frag_con,container,false);
        Toast.makeText(getActivity(), "Home Activated", Toast.LENGTH_SHORT).show();
        return v;
    }
}
