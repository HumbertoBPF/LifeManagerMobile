package com.example.lifemanager.activities.tasks;

import static com.example.lifemanager.tools.Util.deletionDialog;
import static com.example.lifemanager.tools.Util.showToast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.async_tasks.AsyncTask;
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
        titleIconAppbar = getResources().getString(R.string.title_appbar_task_form);
        resourceType = getResources().getStringArray(R.array.categories)[2];
        formAddClass = AddTaskActivity.class;
        super.onCreate(savedInstanceState);
        roomTaskDAO = LifeManagerDatabase.getInstance(this).getRoomTaskDAO();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(roomTaskDAO.getTaskById(chosenId));
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Task task = (Task) objects.get(0);
                if (item.getTitle().equals(getResources().getString(R.string.context_menu_delete_option))){
                    AlertDialog deletionDialog = deletionDialog(context, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItemFromRecyclerView(task);
                        }
                    });
                    deletionDialog.show();
                }else {
                    Intent intent = new Intent(context, AddTaskActivity.class);
                    intent.putExtra(resourceType,task);
                    startActivity(intent);
                }
            }
        }).execute();
        return super.onContextItemSelected(item);
    }

    private void deleteItemFromRecyclerView(Task task) {
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                roomTaskDAO.delete(task);
                return null;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                showToast(getApplicationContext(),getResources().getString(R.string.delete_task_toast_message));
                configureAdapter();
            }
        }).execute();
    }

    protected void configureAdapter() {
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.addAll(roomTaskDAO.getAllTasks());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                List<Task> tasks = new ArrayList<>();
                for (Object object : objects){
                    tasks.add((Task) object);
                }
                adapter = new ListTasksAdapter(context, tasks, new ListTasksAdapter.OnClickListener() {
                    @Override
                    public void onItemClickListener(Task task) {
                        Intent intent = new Intent(getApplicationContext(),DetailedTaskActivity.class);
                        intent.putExtra(resourceType,task);
                        startActivity(intent);
                    }
                });
                recyclerViewResources.setAdapter(adapter);
                registerForContextMenu(recyclerViewResources);
            }
        }).execute();
    }

}