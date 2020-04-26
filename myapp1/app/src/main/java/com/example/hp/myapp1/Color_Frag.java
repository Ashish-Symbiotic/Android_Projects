package com.example.hp.myapp1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

/**
 * Created by HP on 15-Dec-17.
 */

public class Color_Frag extends Fragment {
    RadioGroup radioGroup;
    OnColorChangeListener onColorChangeListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        view=inflater.inflate(R.layout.color_frag,container,false);
        radioGroup=(RadioGroup)view.findViewById(R.id.Color_grp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i)
                {
                    case R.id.red:
                        onColorChangeListener.colorChanged("RED");
                        break;
                    case R.id.green:
                        onColorChangeListener.colorChanged("GREEN");
                        break;
                    case R.id.blue:
                        onColorChangeListener.colorChanged("BLUE");
                        break;
                }
            }
        });
        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onColorChangeListener = (OnColorChangeListener) activity;
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public interface OnColorChangeListener
    {
        public void colorChanged(String color_name);
    }
}
