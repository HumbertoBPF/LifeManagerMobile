package com.example.lifemanager.dao;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.lifemanager.interfaces.OnResultListener;
import com.example.lifemanager.model.Task;

import java.util.List;

@Dao
public abstract class TaskDAO extends BaseDAO<Task>{

    @Query("SELECT * FROM task ORDER BY dueDate;")
    protected abstract List<Task> getAllTasks();

    public AsyncTask<Void,Void,List<Task>> getAllTasksAsyncTask(OnResultListener<List<Task>> onResultListener){
        return new AsyncTask<Void, Void, List<Task>>() {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                return getAllTasks();
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                onResultListener.onResult(tasks);
            }
        };
    }

}
