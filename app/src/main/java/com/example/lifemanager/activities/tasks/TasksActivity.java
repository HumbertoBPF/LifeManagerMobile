package com.example.lifemanager.activities.tasks;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.adapters.TasksAdapter;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class TasksActivity extends CategoryActivity<Task> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[2];
        colorAppbar = getResources().getColor(R.color.color_tasks_item);
        titleIconAppbar = getString(R.string.title_appbar_task_form);
        formAddClass = AddTaskActivity.class;
        super.onCreate(savedInstanceState);
        categoryDAO = LifeManagerDatabase.getInstance(this).getRoomTaskDAO();
    }

    @Override
    protected RecyclerView.Adapter initializeAdapter(List<Task> list) {
        return new TasksAdapter(TasksActivity.this, list);
    }

}