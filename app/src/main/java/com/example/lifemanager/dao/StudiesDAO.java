package com.example.lifemanager.dao;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.lifemanager.interfaces.OnResultListener;
import com.example.lifemanager.model.Studies;

import java.util.List;

@Dao
public abstract class StudiesDAO extends BaseDAO<Studies>{

    @Query("SELECT * FROM studies ORDER BY position ASC")
    protected abstract List<Studies> getAllStudies();

    @Query("SELECT MAX(position) FROM studies")
    protected abstract Integer getMaxPosition();

    public AsyncTask<Void,Void,List<Studies>> getAllStudiesAsyncTask(OnResultListener<List<Studies>> onResultListener){
        return new AsyncTask<Void, Void, List<Studies>>() {
            @Override
            protected List<Studies> doInBackground(Void... voids) {
                return getAllStudies();
            }

            @Override
            protected void onPostExecute(List<Studies> studies) {
                super.onPostExecute(studies);
                onResultListener.onResult(studies);
            }
        };
    }

    public AsyncTask<Void,Void,Integer> getMaxPositionAsyncTask(OnResultListener<Integer> onResultListener){
        return new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return getMaxPosition();
            }

            @Override
            protected void onPostExecute(Integer maxPosition) {
                super.onPostExecute(maxPosition);
                onResultListener.onResult(maxPosition);
            }
        };
    }

}
