package com.marbles.sagar.phone_wifi;

import android.accessibilityservice.GestureDescription;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.WIFI_SERVICE;

public class main_frag extends Fragment {
    Button b;
    float rs, spin_int;
    private  Context ctx;
    private ArrayList<String> units;
    ArrayAdapter<CharSequence> data;
    String rs1,unit1,spin1_item,spin2_item,rs2,unit2;
    private View v;
    View layout;
    boolean alltrue=false;
    public static String id="";
    private boolean exit =false;
    private  float unit,unit1_val,unit2_val,rs1_val,rs2_val,one_gm_val,Kg_val,gm_val;
    EditText unitText , unitText2,rsText,rsText2;
    Spinner unit_s,unit_s2;
   public void getApplication(Context ctx,String id)
   {

       this.ctx=ctx;
       this.id=id;
   }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.toast_view,container,false);

        rsText =(EditText)v.findViewById(R.id.rs_text);
        Button x = (Button)v.findViewById(R.id.btndel);
        rsText2=(EditText)v.findViewById(R.id.rs_text2);
        unitText =(EditText)v.findViewById(R.id.unit_text);
        unitText2=(EditText)v.findViewById(R.id.unit_Text2);
        unit_s = (Spinner)v.findViewById(R.id.units_spinner);
        unit_s2= (Spinner)v.findViewById(R.id.units_spinner2);
        data = data.createFromResource(getActivity(),R.array.units_array,android.R.layout.simple_spinner_item);
        data.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        b=(Button)v.findViewById(R.id.get_res1);
        unit_s.setAdapter(data);
        unit_s2.setAdapter(data);

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new Prefrencemanager(ctx).clearPref();
                    startActivity(new Intent(ctx,wel_activity.class));
                    container.removeView(v);

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
                        @Override
                            public void onClick(View view) {
                            rs1= String.valueOf(rsText.getText());
                            rs2= String.valueOf(rsText2.getText());
                            unit1 =String.valueOf(unitText.getText());
                            unit2 =String.valueOf(unitText2.getText());
                            spin1_item = String.valueOf(unit_s.getSelectedItem());
                            spin2_item = String.valueOf(unit_s2.getSelectedItem());


                            if(rs1.equals("") && rs2.equals("") && unit1.equals("") && unit2.equals(""))
                            {
                                rsText.requestFocus();
                                Toast.makeText(ctx, "kuch to daalo kisi mein ", Toast.LENGTH_SHORT).show();
                            }
                           else  if(!rs1.equals("") && !rs2.equals("") && !unit1.equals("") && !unit2.equals(""))
                            {
                                rsText.requestFocus();
                                Toast.makeText(ctx, "Kya Serach Krna hai \n Ek field empty rakho ", Toast.LENGTH_SHORT).show();
                            }

                            else  if((rs1.equals("") && rs2.equals("")) && (unit1.equals("") || !unit2.equals("") || !unit1.equals("") || unit2.equals("")))
                            {
                                rsText.requestFocus();
                                Toast.makeText(ctx, "rs. to fill karo ", Toast.LENGTH_SHORT).show();
                            }
                            else  if((!rs1.equals("") || !rs2.equals("") ||rs1.equals("") || rs2.equals("")  ) && (unit1.equals("") && unit2.equals("")))
                            {
                                unitText.requestFocus();
                                Toast.makeText(ctx, "units to fill karo ", Toast.LENGTH_SHORT).show();
                            }
                            else  if(((!rs1.equals("") && !unit1.equals("")) &&(unit2.equals("") && rs2.equals("") ) ) || (!unit2.equals("") && !rs2.equals("") && ( unit1.equals("")) && rs1.equals("")))
                            {
                                unitText.requestFocus();
                                Toast.makeText(ctx, "Kya serach krna hai  ", Toast.LENGTH_SHORT).show();
                            }



                            else
                                {
                                    if(spin1_item.equals("Select") || spin2_item.equals("Select"))
                                    {
                                        unit_s.requestFocus();
                                        Toast t= Toast.makeText(getActivity(), "Units to select karo koi", Toast.LENGTH_SHORT);
                                        t.setGravity(Gravity.CENTER_HORIZONTAL,15,20);
                                        t.show();
                                    }

                                    else
                                    {
                                        /*rs wali first field empty*/
                                        if(rs1.equals(""))
                                        {
                                            rs2_val=Float.valueOf(rs2);
                                            unit1_val=Float.valueOf(unit1);
                                            unit2_val=Float.valueOf(unit2);



                                                if(spin1_item.equals("Kg"))
                                                {
                                                    if(spin2_item.equals("Kg"))
                                                    {
                                                        one_gm_val=rs2_val/(unit2_val*1000);
                                                        Kg_val = one_gm_val * 1000 * unit1_val;
                                                        Toast_rs(Kg_val);
                                                    }
                                                    else if(spin2_item.equals("g"))
                                                    {
                                                        one_gm_val=rs2_val/(unit2_val);
                                                        Kg_val = one_gm_val * unit1_val*1000;
                                                        Toast_rs(Kg_val);
                                                    }
                                                    else
                                                    {

                                                    }
                                                }
                                                if(spin1_item.equals("g"))
                                                {
                                                   if(spin2_item.equals("g"))
                                                   {
                                                       one_gm_val=rs2_val/(unit2_val);
                                                       gm_val=one_gm_val*unit1_val;
                                                      Toast_rs(gm_val);
                                                   }
                                                   else if(spin2_item.equals("Kg"))
                                                   {
                                                       one_gm_val=rs2_val/(unit2_val*1000);
                                                       gm_val=one_gm_val*unit1_val;
                                                       Toast_rs(gm_val);
                                                   }
                                                   else
                                                   {

                                                   }
                                                }

                                        }


                                      /*Yahan Kahatm hai dusra suru*/
                                        if(rs2.equals(""))
                                        {
                                            rs1_val=Float.valueOf(rs1);
                                           // rs2_val=Float.valueOf(rs2);
                                            unit1_val=Float.valueOf(unit1);
                                            unit2_val=Float.valueOf(unit2);
                                            one_gm_val=rs1_val/(unit1_val*1000);
                                            if(spin2_item.equals("Kg"))
                                            {
                                                if(spin1_item.equals("Kg"))
                                                {
                                                    one_gm_val=rs1_val/(unit1_val*1000);
                                                    Kg_val = one_gm_val * 1000 * unit2_val;
                                                    Toast_rs(Kg_val);
                                                }
                                                else if(spin1_item.equals("g"))
                                                {
                                                    one_gm_val=rs1_val/(unit1_val);
                                                    Kg_val = one_gm_val * unit2_val*1000;
                                                    Toast_rs(Kg_val);
                                                }
                                                else
                                                {

                                                }
                                            }
                                            if(spin2_item.equals("g"))
                                            {
                                                if(spin1_item.equals("g"))
                                                {
                                                    one_gm_val=rs1_val/(unit1_val);
                                                    gm_val=one_gm_val*unit2_val;
                                                    Toast_rs(gm_val);
                                                }
                                                else if(spin1_item.equals("Kg"))
                                                {
                                                    one_gm_val=rs1_val/(unit1_val*1000);
                                                    gm_val=one_gm_val*unit2_val;
                                                    Toast_rs(gm_val);
                                                }
                                                else
                                                {

                                                }
                                            }
                                        }


                                        /*unit(1) wali field empty */
                                        if(unit1.equals(""))
                                        {
                                            rs1_val=Float.valueOf(rs1);
                                            rs2_val=Float.valueOf(rs2);
                                            //unit1_val=Float.valueOf(unit1);
                                            unit2_val=Float.valueOf(unit2);


                                            if(spin1_item.equals("Kg"))
                                            {
                                                if(spin2_item.equals("Kg")) {
                                                    one_gm_val=(unit2_val*1000)/rs2_val;
                                                    Kg_val = (one_gm_val * rs1_val) / 1000;
                                                   Toast_unit_Kg(Kg_val);
                                                }
                                                else if(spin2_item.equals("g"))
                                                {
                                                    one_gm_val=(unit2_val)/rs2_val;
                                                    Kg_val = (one_gm_val * rs1_val) / 1000;
                                                    Toast_unit_Kg(Kg_val);
                                                }
                                                else {

                                                }
                                            }
                                            if(spin1_item.equals("g"))
                                            {
                                                if(spin2_item.equals("Kg"))
                                                {
                                                    one_gm_val=(unit2_val)/rs2_val;
                                                    gm_val=one_gm_val*rs1_val*1000;
                                                    Toast_unit_gm(gm_val);
                                                }
                                                else if (spin2_item.equals("g"))
                                                {
                                                    one_gm_val=(unit2_val)/rs2_val;
                                                    gm_val=one_gm_val*rs1_val;
                                                    Toast_unit_gm(gm_val);
                                                }
                                            }
                                        }



                                        /*Unit(2) wali empty field*/
                                        if(unit2.equals(""))
                                        {
                                            rs1_val=Float.valueOf(rs1);
                                            rs2_val=Float.valueOf(rs2);
                                            unit1_val=Float.valueOf(unit1);
                                            //unit2_val=Float.valueOf(unit2);
                                            one_gm_val=(unit1_val*1000)/rs1_val;

                                            if(spin2_item.equals("Kg"))
                                            {
                                                if(spin1_item.equals("Kg")) {
                                                one_gm_val=(unit1_val*1000)/rs1_val;
                                                Kg_val = (one_gm_val * rs2_val) / 1000;
                                                    Toast_unit_Kg(Kg_val);
                                            }
                                                else if(spin1_item.equals("g"))
                                            {
                                                one_gm_val=(unit1_val)/rs1_val;
                                                Kg_val = (one_gm_val * rs2_val) / 1000;
                                                Toast_unit_Kg(Kg_val);
                                            }
                                            else {

                                            }
                                            }
                                            if(spin2_item.equals("g"))
                                            {
                                                if(spin1_item.equals("Kg"))
                                                {
                                                    one_gm_val=(unit1_val)/rs1_val;
                                                    gm_val=one_gm_val*rs2_val*1000;
                                                    Toast_unit_gm(gm_val);
                                                }
                                                else if (spin2_item.equals("g"))
                                                {
                                                    one_gm_val=(unit1_val)/rs1_val;
                                                    gm_val=one_gm_val*rs2_val;
                                                    Toast_unit_gm(gm_val);
                                                }
                                            }
                                        }
                                        /*content content = new content(getActivity());
                                        rs1_val=Float.valueOf(rs1);
                                        rs2_val=Float.valueOf(rs2);
                                        unit1_val=Float.valueOf(unit1);
                                        unit2_val=Float.valueOf(unit2);

                                         float rel=content.getvalues(rs1_val,unit1_val);
                                        LayoutInflater inflater =getActivity().getLayoutInflater();
                                        layout = inflater.inflate(R.layout.toast_view_new,
                                                (ViewGroup) view.findViewById(R.id.toast_view_container));


                                    Toast.makeText(getActivity(), ""+rel , Toast.LENGTH_SHORT).show();*/
                                    }




                                    }

    }
});
        return v;
    }
    public void Toast_rs(Float kg_vall){

    Toast t = Toast.makeText(getActivity(),kg_vall+ "  rs.", Toast.LENGTH_SHORT);
    t.setGravity(Gravity.CENTER_HORIZONTAL, 15, 20);
    t.show();
}


    public void Toast_unit_Kg(Float kg_vall){

        Toast t = Toast.makeText(getActivity(),  kg_vall+" Kg." , Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER_HORIZONTAL, 15, 20);
        t.show();
    }

    public void Toast_unit_gm(Float kg_vall){

        Toast t = Toast.makeText(getActivity(), " " + kg_vall+" gm", Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER_HORIZONTAL, 15, 20);
        t.show();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
