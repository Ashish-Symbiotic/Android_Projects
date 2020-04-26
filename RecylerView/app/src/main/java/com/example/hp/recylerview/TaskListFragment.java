package com.example.hp.recylerview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ashish B->B on 30-Jan-18.
 */

public class TaskListFragment extends Fragment {
    RecyclerView recyclerView;
    TaskListAdapter taskListAdapter;

    public TaskListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskListAdapter=new TaskListAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_insert:
                ((OnEditTask) getActivity()).editTask(0);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.fragment_task_list,container,false);
       recyclerView =(RecyclerView)v.findViewById(R.id.recycle1);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerView.setAdapter(taskListAdapter);
       recyclerView.setHasFixedSize(true);
        return v;
    }
}
