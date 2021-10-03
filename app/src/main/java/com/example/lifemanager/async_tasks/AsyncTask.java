package com.example.lifemanager.async_tasks;

import java.util.List;

public class AsyncTask extends android.os.AsyncTask<Void,Void, List<Object>> {

    private AsyncTask.AsyncTaskInterface asyncTaskInterface;

    public AsyncTask(AsyncTaskInterface asyncTaskInterface) {
        this.asyncTaskInterface = asyncTaskInterface;
    }

    @Override
    protected List<Object> doInBackground(Void... voids) {
        return asyncTaskInterface.doInBackground();
    }

    @Override
    protected void onPostExecute(List<Object> objects) {
        super.onPostExecute(objects);
        asyncTaskInterface.onPostExecute(objects);
    }

    public interface AsyncTaskInterface{
        List<Object> doInBackground();
        void onPostExecute(List<Object> objects);
    }

}
