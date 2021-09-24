package com.example.lifemanager.activities.tasks;

import static com.example.lifemanager.tools.Util.areToastsEnabled;
import static com.example.lifemanager.tools.Util.confirmDeletionDialog;
import static com.example.lifemanager.tools.Util.showToast;

import android.content.DialogInterface;
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
        if (item.getTitle().equals(getResources().getString(R.string.context_menu_delete_option))){
            confirmDeletionDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    roomTaskDAO.delete(task);
                    showToast(getApplicationContext(),getResources().getString(R.string.delete_task_toast_message),
                            areToastsEnabled(getApplicationContext()));
                    configureAdapter();
                }
            });
        }else {
            Intent intent = new Intent(this, AddTaskActivity.class);
            intent.putExtra("task",task);
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    private void configureAdapter() {
        tasks = roomTaskDAO.getAllTasks();
        adapter = new ListTasksAdapter(this, tasks, new ListTasksAdapter.OnClickListener() {
            @Override
            public void onItemClickListener(Task task) {
                Intent intent = new Intent(getApplicationContext(),DetailedTaskActivity.class);
                intent.putExtra("task",task);
                startActivity(intent);
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