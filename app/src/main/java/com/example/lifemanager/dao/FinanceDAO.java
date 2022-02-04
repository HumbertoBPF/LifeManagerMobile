package com.example.lifemanager.dao;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.lifemanager.interfaces.OnResultListener;
import com.example.lifemanager.model.Finance;

import java.util.List;

@Dao
public abstract class FinanceDAO extends BaseDAO<Finance>{

    @Query("SELECT * FROM finance ORDER BY date DESC;")
    protected abstract List<Finance> getAllFinances();

    public AsyncTask<Void,Void,List<Finance>> getAllFinancesAsyncTask(OnResultListener<List<Finance>> onResultListener){
        return new AsyncTask<Void, Void, List<Finance>>() {
            @Override
            protected List<Finance> doInBackground(Void... voids) {
                return getAllFinances();
            }

            @Override
            protected void onPostExecute(List<Finance> finances) {
                super.onPostExecute(finances);
                onResultListener.onResult(finances);
            }
        };
    }

}
