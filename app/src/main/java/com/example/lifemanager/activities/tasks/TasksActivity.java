package com.example.lifemanager.activities.tasks;

import static com.example.lifemanager.tools.Util.deletionDialog;
import static com.example.lifemanager.tools.Util.showToast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.async_tasks.TasksAsyncTask;
import com.example.lifemanager.dao.RoomTaskDAO;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.recycler_view.ListTasksAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends CategoryActivity {

    private RoomTaskDAO roomTaskDAO;
    private ListTasksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[2];
        colorAppbar = getResources().getColor(R.color.color_tasks_item);
        super.onCreate(savedInstanceState);
        roomTaskDAO = LifeManagerDatabase.getInstance(this).getRoomTaskDAO();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        new TasksAsyncTask(new TasksAsyncTask.TasksAsyncTaskInterface() {
            @Override
            public List<Task> doInBackground() {
                List<Task> tasks = new ArrayList<>();
                tasks.add(roomTaskDAO.getTaskById(chosenId));
                return tasks;
            }

            @Override
            public void onPostExecute(List<Task> tasks) {
                if (item.getTitle().equals(getResources().getString(R.string.context_menu_delete_option))){
                    AlertDialog deletionDialog = deletionDialog(context, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItemFromRecyclerView(tasks.get(0));
                        }
                    });
                    deletionDialog.show();
                }else {
                    Intent intent = new Intent(context, AddTaskActivity.class);
                    intent.putExtra("task",tasks.get(0));
                    startActivity(intent);
                }
            }
        }).execute();
        return super.onContextItemSelected(item);
    }

    private void deleteItemFromRecyclerView(Task task) {
        new TasksAsyncTask(new TasksAsyncTask.TasksAsyncTaskInterface() {
            @Override
            public List<Task> doInBackground() {
                roomTaskDAO.delete(task);
                return null;
            }

            @Override
            public void onPostExecute(List<Task> tasks) {
                showToast(getApplicationContext(),getResources().getString(R.string.delete_task_toast_message));
                configureAdapter();
            }
        }).execute();
    }

    private void configureAdapter() {
        new TasksAsyncTask(new TasksAsyncTask.TasksAsyncTaskInterface() {
            @Override
            public List<Task> doInBackground() {
                return roomTaskDAO.getAllTasks();
            }

            @Override
            public void onPostExecute(List<Task> tasks) {
                adapter = new ListTasksAdapter(context, tasks, new ListTasksAdapter.OnClickListener() {
                    @Override
                    public void onItemClickListener(Task task) {
                        Intent intent = new Intent(getApplicationContext(),DetailedTaskActivity.class);
                        intent.putExtra("task",task);
                        startActivity(intent);
                    }
                });
                recyclerViewResources.setAdapter(adapter);
                registerForContextMenu(recyclerViewResources);
            }
        }).execute();
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