package com.example.lifemanager.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import android.os.AsyncTask;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.RawQuery;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.lifemanager.interfaces.OnResultListener;
import com.example.lifemanager.interfaces.OnTaskListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<E> {

    private String getAllRecordsQuery;

    protected BaseDAO(String tableName, String suffixQuery) {
        if (tableName != null){
            if (!tableName.isEmpty()){
                this.getAllRecordsQuery = "SELECT * FROM "+ tableName;
                if (suffixQuery != null){
                    this.getAllRecordsQuery += (" " + suffixQuery);
                }
            }
        }
    }

    @RawQuery
    protected abstract List<E> getAllRecords(SupportSQLiteQuery sqLiteQuery);

    @Insert(onConflict = REPLACE)
    protected abstract void save(E task);

    @Delete
    protected abstract void delete(E task);

    public AsyncTask<Void,Void,List<E>> getAllRecordsTask(OnResultListener<List<E>> onResultListener){
        return new AsyncTask<Void, Void, List<E>>() {
            @Override
            protected List<E> doInBackground(Void... voids) {
                if (getAllRecordsQuery != null){
                    SupportSQLiteQuery sqLiteQuery = new SimpleSQLiteQuery(getAllRecordsQuery);
                    return getAllRecords(sqLiteQuery);
                }
                return new ArrayList<>();
            }

            @Override
            protected void onPostExecute(List<E> result) {
                super.onPostExecute(result);
                onResultListener.onResult(result);
            }
        };
    }

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
