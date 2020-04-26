package com.example.hp.recylerview;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class TaskEditActivity extends Activity implements OnEditFinished  {
    public static final String EXTRA_TASKID = "taskId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        setActionBar((Toolbar)findViewById(R.id.toolbar));
        long id =getIntent().getLongExtra(TaskEditActivity.EXTRA_TASKID,0L);
        Fragment fragment=TaskEditFragment.newInstance(id);
        String fragmentTag=TaskEditFragment.DEFAULT_FRAGMENT_TAG;
        if(savedInstanceState==null){
            getFragmentManager().beginTransaction().add(
                    R.id.container,fragment,
                    fragmentTag
            ).commit();
        }
    }


    @Override
    public void finishEditingTask() {
        finish();
    }
}
