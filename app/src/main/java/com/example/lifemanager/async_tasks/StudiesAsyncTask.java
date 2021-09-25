package com.example.lifemanager.async_tasks;

import android.os.AsyncTask;

import com.example.lifemanager.model.Studies;

import java.util.List;

public class StudiesAsyncTask extends AsyncTask<Void,Void, List<Studies>> {

    private StudiesAsyncTaskInterface studiesAsyncTaskInterface;

    public StudiesAsyncTask(StudiesAsyncTaskInterface studiesAsyncTaskInterface) {
        this.studiesAsyncTaskInterface = studiesAsyncTaskInterface;
    }

    @Override
    protected List<Studies> doInBackground(Void... voids) {
        return studiesAsyncTaskInterface.doInBackground();
    }

    @Override
    protected void onPostExecute(List<Studies> studies) {
        super.onPostExecute(studies);
        studiesAsyncTaskInterface.onPostExecute(studies);
    }

    public interface StudiesAsyncTaskInterface{
        List<Studies> doInBackground();
        void onPostExecute(List<Studies> studies);
    }

}
