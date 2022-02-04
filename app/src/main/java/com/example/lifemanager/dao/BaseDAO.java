package com.example.lifemanager.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import android.os.AsyncTask;

import androidx.room.Delete;
import androidx.room.Insert;

import com.example.lifemanager.interfaces.OnTaskListener;

public abstract class BaseDAO<E> {

    @Insert(onConflict = REPLACE)
    protected abstract void save(E task);

    @Delete
    protected abstract void delete(E task);

    public AsyncTask<Void,Void,E> getSaveAsyncTask(E task, OnTaskListener onTaskListener){
        return new AsyncTask<Void, Void, E>() {
            @Override
            protected E doInBackground(Void... voids) {
                save(task);
                return null;
            }

            @Override
            protected void onPostExecute(E e) {
                super.onPostExecute(e);
                onTaskListener.onTask();
            }
        };
    }

    public AsyncTask<Void,Void,E> getDeleteAsyncTask(E task, OnTaskListener onTaskListener){
        return new AsyncTask<Void, Void, E>() {
            @Override
            protected E doInBackground(Void... voids) {
                delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(E e) {
                super.onPostExecute(e);
                onTaskListener.onTask();
            }
        };
    }

}
