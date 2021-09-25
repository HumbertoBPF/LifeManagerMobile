package com.example.lifemanager.async_tasks;

import android.os.AsyncTask;

import com.example.lifemanager.model.Finance;

import java.util.List;

public class FinancesAsyncTask extends AsyncTask<Void,Void, List<Finance>> {

    private FinancesAsyncTaskInterface financesAsyncTaskInterface;

    public FinancesAsyncTask(FinancesAsyncTaskInterface financesAsyncTaskInterface) {
        this.financesAsyncTaskInterface = financesAsyncTaskInterface;
    }

    @Override
    protected List<Finance> doInBackground(Void... voids) {
        return financesAsyncTaskInterface.doInBackground();
    }

    @Override
    protected void onPostExecute(List<Finance> finances) {
        super.onPostExecute(finances);
        financesAsyncTaskInterface.onPostExecute(finances);
    }

    public interface FinancesAsyncTaskInterface{
        List<Finance> doInBackground();
        void onPostExecute(List<Finance> finances);
    }

}
