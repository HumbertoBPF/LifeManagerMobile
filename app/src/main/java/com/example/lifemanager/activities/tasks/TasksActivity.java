package com.example.lifemanager.activities.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.dao.RoomTaskDAO;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.recycler_view.ListTasksAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;
import com.example.lifemanager.tools.Util;

import java.util.List;

public class TasksActivity extends CategoryActivity {

    private RoomTaskDAO roomTaskDAO;
    private List<Task> tasks;
    private ListTasksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[2];
        colorAppbar = getResources().getColor(R.color.color_tasks_item);
        super.onCreate(savedInstanceState);
        roomTaskDAO = LifeManagerDatabase.getInstance(this).getRoomTaskDAO();
        configureAdapter();
        registerForContextMenu(recyclerViewResources);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        Task task = roomTaskDAO.getTaskById(chosenId);
        if (item.getTitle().equals("Remove")){
            roomTaskDAO.delete(task);
        }else {
            Intent intent = new Intent(this, AddTaskActivity.class);
            intent.putExtra("task",task);
            startActivity(intent);
        }
        configureAdapter();
        return super.onContextItemSelected(item);
    }

    private void configureAdapter() {
        tasks = roomTaskDAO.getAllTasks();
        adapter = new ListTasksAdapter(this, tasks, new ListTasksAdapter.OnClickListener() {
            @Override
            public void onItemClickListener(Task task) {
                Util.showToast(getApplicationContext(),"Show details");
            }
        });
        recyclerViewResources.setAdapter(adapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        configureAdapter();
    }

}