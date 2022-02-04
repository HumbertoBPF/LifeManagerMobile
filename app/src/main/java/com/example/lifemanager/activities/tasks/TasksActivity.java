package com.example.lifemanager.activities.tasks;

import android.content.Intent;
import android.os.Bundle;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.adapters.TasksAdapter;
import com.example.lifemanager.dao.TaskDAO;
import com.example.lifemanager.interfaces.OnItemClickListener;
import com.example.lifemanager.interfaces.OnResultListener;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class TasksActivity extends CategoryActivity<Task> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[2];
        colorAppbar = getResources().getColor(R.color.color_tasks_item);
        titleIconAppbar = getString(R.string.title_appbar_task_form);
        resourceType = getResources().getStringArray(R.array.categories)[2];
        formAddClass = AddTaskActivity.class;
        super.onCreate(savedInstanceState);
        categoryDAO = LifeManagerDatabase.getInstance(this).getRoomTaskDAO();
    }

    protected void configureAdapter() {
        loadingDialog.show();
        ((TaskDAO) categoryDAO).getAllTasksAsyncTask(new OnResultListener<List<Task>>() {
            @Override
            public void onResult(List<Task> result) {
                adapter = new TasksAdapter(TasksActivity.this, result, new OnItemClickListener<Task>() {
                    @Override
                    public void onItemClick(Task task) {
                        Intent intent = new Intent(getApplicationContext(),DetailedTaskActivity.class);
                        intent.putExtra(resourceType,task);
                        startActivity(intent);
                    }
                });
                recyclerViewResources.setAdapter(adapter);
                registerForContextMenu(recyclerViewResources);
                loadingDialog.dismiss();
            }
        }).execute();
    }

}