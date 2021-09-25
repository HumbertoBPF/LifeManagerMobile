package com.example.lifemanager.async_tasks;

import android.os.AsyncTask;

import com.example.lifemanager.model.Setting;

public class SettingsAsyncTask extends AsyncTask<Void,Void, Setting> {

    private SettingsAsyncTaskInterface settingsAsyncTaskInterface;

    public SettingsAsyncTask(SettingsAsyncTaskInterface settingsAsyncTaskInterface) {
        this.settingsAsyncTaskInterface = settingsAsyncTaskInterface;
    }

    @Override
    protected Setting doInBackground(Void... voids) {
        return settingsAsyncTaskInterface.doInBackground();
    }

    @Override
    protected void onPostExecute(Setting setting) {
        super.onPostExecute(setting);
        settingsAsyncTaskInterface.onPostExecute(setting);
    }

    public interface SettingsAsyncTaskInterface{
        Setting doInBackground();
        void onPostExecute(Setting setting);
    }

}
