package com.example.lifemanager.async_tasks;

import android.os.AsyncTask;

import com.example.lifemanager.model.Task;

import java.util.List;

public class TasksAsyncTask extends AsyncTask<Void,Void, List<Task>> {

    private TasksAsyncTaskInterface tasksAsyncTaskInterface;

    public TasksAsyncTask(TasksAsyncTaskInterface tasksAsyncTaskInterface) {
        this.tasksAsyncTaskInterface = tasksAsyncTaskInterface;
    }

    @Override
    protected List<Task> doInBackground(Void... voids) {
        return tasksAsyncTaskInterface.doInBackground();
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
        tasksAsyncTaskInterface.onPostExecute(tasks);
    }

    public interface TasksAsyncTaskInterface{
        List<Task> doInBackground();
        void onPostExecute(List<Task> tasks);
    }

}
