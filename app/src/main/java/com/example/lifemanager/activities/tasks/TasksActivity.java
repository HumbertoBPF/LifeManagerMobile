package com.example.lifemanager.activities.tasks;

import android.content.Intent;
import android.os.Bundle;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.async_tasks.AsyncTask;
import com.example.lifemanager.dao.TaskDAO;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.recycler_view.TasksAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.ArrayList;
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
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.addAll(((TaskDAO) categoryDAO).getAllTasks());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                List<Task> tasks = new ArrayList<>();
                for (Object object : objects){
                    tasks.add((Task) object);
                }
                adapter = new TasksAdapter(TasksActivity.this, tasks, new TasksAdapter.OnClickListener() {
                    @Override
                    public void onItemClickListener(Task task) {
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