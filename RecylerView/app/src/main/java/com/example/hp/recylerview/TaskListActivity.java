package com.example.hp.recylerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class TaskListActivity extends AppCompatActivity implements OnEditTask {
//MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar((Toolbar)findViewById(R.id.toolbar));

    }

    @Override
    public void editTask(long id) {
        startActivity(new Intent(this,TaskEditActivity.class).
                putExtra(TaskEditActivity.EXTRA_TASKID,id));
    }
}
