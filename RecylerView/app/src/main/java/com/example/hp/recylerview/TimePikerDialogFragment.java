package com.example.hp.recylerview;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;

import java.util.Calendar;

/**
 * Created by HP on 13-Feb-18.
 */

public class TimePikerDialogFragment extends DialogFragment {
    static final String HOUR ="hour";
    static final String MINS ="mins";
    public static TimePikerDialogFragment newInstance(Calendar time){
        TimePikerDialogFragment fragment=new TimePikerDialogFragment();
        Bundle args=new Bundle();
        args.putInt(HOUR,time.get(Calendar.HOUR_OF_DAY));
        args.putInt(MINS,time.get(Calendar.MINUTE));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TimePickerDialog.OnTimeSetListener listener=(TimePickerDialog.OnTimeSetListener)getFragmentManager().findFragmentByTag(TaskEditFragment.DEFAULT_FRAGMENT_TAG);
        Bundle args =getArguments();
        return new TimePickerDialog(getActivity(),listener,args.getInt(HOUR),args.getInt(MINS),false);
    }
}
