package com.example.lifemanager.activities.tasks;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;

public class TasksActivity extends CategoryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[2];
        colorAppbar = getResources().getColor(R.color.color_tasks_item);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        titleIconAppbar = getResources().getString(R.string.title_appbar_task_form);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        formAddClass = AddTaskActivity.class;
        return super.onOptionsItemSelected(item);
    }
}