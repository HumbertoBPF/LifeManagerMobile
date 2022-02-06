package com.example.lifemanager.dao;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.lifemanager.interfaces.OnResultListener;
import com.example.lifemanager.model.Studies;

@Dao
public abstract class StudiesDAO extends BaseDAO<Studies>{

    protected StudiesDAO() {
        super("Studies", "ORDER BY position ASC");
    }

    @Query("SELECT MAX(position) FROM studies")
    protected abstract Integer getMaxPosition();

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
