package com.example.hp.frag1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by HP on 21-Dec-17.
 */

public class Frag_Shared extends Fragment  {
    EditText name,pass,viewd;
    Button getit,show,clear;
    String name1;
    String pass2;
    String viesd3;
    SharedPreferences sharedPreferences;
    public static final String Name="name_key";
    public static final String Pass1="pass_key";
    public static final String Views1="views_key";
    OnClickListener1 onClickListener1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        final View v;
        v= inflater.inflate(R.layout.shared_prefrences,container,false);
        name=(EditText)v.findViewById(R.id.name);
        pass=(EditText)v.findViewById(R.id.Pass);
        viewd=(EditText)v.findViewById(R.id.Viewsd);
        getit=(Button)v.findViewById(R.id.Store);
        show=(Button)v.findViewById(R.id.Show);
        clear=(Button)v.findViewById(R.id.Clear);
        sharedPreferences=getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);

        getit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1=name.getText().toString();
                pass2=pass.getText().toString();
                viesd3=viewd.getText().toString();
                onClickListener1.onchanged1(name1,pass2,viesd3);
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name.setText(sharedPreferences.getString(Name,""));
                pass.setText(sharedPreferences.getString(Pass1,""));
                viewd.setText(sharedPreferences.getString(Views1,""));

            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                pass.setText("");
                viewd.setText("");
            }
        });
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onClickListener1=(OnClickListener1)activity;

    }

    @Override
    public void onStart() {
        super.onStart();
    }




    public interface OnClickListener1
    {
        public void onchanged1(String name1, String pass2, String viesd3);

    }

}
