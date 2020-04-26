package com.example.hp.recylerview;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by HP on 01-Feb-18.
 */

public class TaskEditFragment extends Fragment  implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    public static final String DEFAULT_FRAGMENT_TAG="taskEditFragment";
    View rootView;
    static final String TASK_ID="taskId";
    EditText titleText,notesText;

    static final String Task_DATE_AND_TIME="taskDateAndTime";
    private static final int MENU_SAVE=1;
    /*case MENU_SAVE:
                ((OnEditFinished)getActivity()).finishEditingTask();*/
    ImageView imageView;
    long task_ID;
    TextView dateButton,timeButton;
Calendar taskDateTime;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0,MENU_SAVE,0,R.string.confirm).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
       /* switch(item.getItemId())
        {
            case MENU_SAVE:

                //((OnEditFinished) getActivity()).finishEditingTask();
                return true;
        }*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle argument =getArguments();
        if(argument!=null)
        {

            task_ID=argument.getLong(TaskEditActivity.EXTRA_TASKID,0L);

        }
        if(savedInstanceState != null)
        {
            task_ID=savedInstanceState.getLong(TASK_ID);
            taskDateTime=(Calendar)savedInstanceState.getSerializable(Task_DATE_AND_TIME);
        }
        if(taskDateTime==null){
            taskDateTime=Calendar.getInstance();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_task_edit,container,false);
        rootView = v.getRootView();
        titleText=(EditText)v.findViewById(R.id.title);
        notesText=(EditText)v.findViewById(R.id.notes);
        imageView=(ImageView)v.findViewById(R.id.image);
        dateButton=(TextView)v.findViewById(R.id.task_date);
        timeButton=(TextView)v.findViewById(R.id.task_time);
        Picasso.with(getActivity()).load(TaskListAdapter.getImageUrl(task_ID)).
                into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Activity activity=getActivity();
                        if(activity==null)
                            return;
                        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        Palette palette
                                =Palette.generate(bitmap,32);
                        int bgcolor =palette.getLightMutedColor(0);
                        if(bgcolor!=0){
                            rootView.setBackgroundColor(bgcolor);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
updateDateTime();
dateButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showDatePicker();
    }
});
timeButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showTimePicker();
    }
});
        return v;
    }

private void showDatePicker(){
    FragmentTransaction ft=getFragmentManager().beginTransaction();
    DatePickerDialogFragment newFragment=DatePickerDialogFragment.newInstance(taskDateTime);
    newFragment.show(ft,"datePicker");
}
private void showTimePicker(){
    FragmentTransaction ft=getFragmentManager().beginTransaction();
    TimePikerDialogFragment fragment=TimePikerDialogFragment.newInstance(taskDateTime);
    fragment.show(ft,"timePicker");
}
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(TASK_ID,task_ID);
        outState.putSerializable(Task_DATE_AND_TIME,taskDateTime);
    }

    public static TaskEditFragment newInstance(long id) {
        Bundle args = new Bundle();
args.putLong(TaskEditActivity.EXTRA_TASKID,id);
        TaskEditFragment fragment = new TaskEditFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void updateDateTime()
    {
        DateFormat timeFormat=DateFormat.getTimeInstance(DateFormat.SHORT);
        String timeForButton=timeFormat.format(taskDateTime.getTime());
                timeButton.setText(timeForButton);
        DateFormat dateFormat=DateFormat.getDateInstance();
        String DateForButton=dateFormat.format(taskDateTime.getTime());
        dateButton.setText(DateForButton);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        taskDateTime.set(Calendar.YEAR, year);
        taskDateTime.set(Calendar.MONTH, monthOfYear);
        taskDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDateTime();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        taskDateTime.set(Calendar.HOUR_OF_DAY, hour);
        taskDateTime.set(Calendar.MINUTE, minute);
        updateDateTime();
        //9899650352

    }
}
