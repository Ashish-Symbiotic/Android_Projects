package com.example.hp.recylerview;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by HP on 13-Feb-18.
 */

public class DatePickerDialogFragment extends DialogFragment {
    static  final String YEAR="year";
    static  final String MONTH="month";
    static  final String DATE="date";
    public static DatePickerDialogFragment newInstance(Calendar Date){
        DatePickerDialogFragment fragment=new DatePickerDialogFragment();
        Bundle args=new Bundle();
        args.putInt(YEAR, Date.get(Calendar.YEAR));
        args.putInt(MONTH, Date.get(Calendar.MONTH));
        args.putInt(YEAR, Date.get(Calendar.DAY_OF_MONTH));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog.OnDateSetListener callback=(DatePickerDialog.OnDateSetListener)getFragmentManager().findFragmentByTag(TaskEditFragment.DEFAULT_FRAGMENT_TAG);
        Bundle args=getArguments();
        return new DatePickerDialog(getActivity(),callback,args.getInt(YEAR),args.getInt(MONTH),args.getInt(DATE));
    }
}
