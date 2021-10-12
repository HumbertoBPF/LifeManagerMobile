package com.example.lifemanager.activities;

import static com.example.lifemanager.model.Constants.CURRENCY_TYPE;
import static com.example.lifemanager.model.Constants.ENABLE_TOASTS;
import static com.example.lifemanager.model.Constants.USERNAME_FOR_APP;
import static com.example.lifemanager.tools.Util.loadingDialog;
import static com.example.lifemanager.tools.Util.setActionBarTitle;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifemanager.R;
import com.example.lifemanager.async_tasks.AsyncTask;
import com.example.lifemanager.dao.RoomSettingDAO;
import com.example.lifemanager.model.Setting;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String spinnerValue;
    private RoomSettingDAO roomSettingDAO;
    private EditText inputUsername;
    private CheckBox enableToasts;
    private Spinner currencyTypeSpinner;
    private Button buttonSaveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, getResources().getString(R.string.title_appbar_settings));
        setContentView(R.layout.activity_app_settings);

        roomSettingDAO = LifeManagerDatabase.getInstance(getApplicationContext()).getRoomSettingDAO();
        getLayoutViews();
        fillSettings();
        configureButtonSaveSettings();
        configureSpinner();
    }

    private void configureSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencyTypeSpinner.setAdapter(adapter);
        currencyTypeSpinner.setOnItemSelectedListener(this);
    }

    private void fillSettings() {
        fillUsernameSetting();
        fillEnableToastsSetting();
        fillCurrencyTypeSetting();
    }

    private void fillEnableToastsSetting() {
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(roomSettingDAO.getEnableToastsSetting());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Setting setting = (Setting) objects.get(0);
                if (setting != null){
                    enableToasts.setChecked(false);
                    if (setting.getValue().equals("true")){
                        enableToasts.setChecked(true);
                    }
                }
            }
        }).execute();
    }

    private void fillUsernameSetting() {
        new AsyncTask((new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(roomSettingDAO.getUsernameSetting());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Setting setting = (Setting) objects.get(0);
                if (setting != null){
                    inputUsername.setText(setting.getValue());
                }
            }
        })).execute();
    }

    private void fillCurrencyTypeSetting() {
        new AsyncTask((new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(roomSettingDAO.getCurrencyTypeSetting());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Setting setting = (Setting) objects.get(0);
                if (setting != null){
                    List<String> currencyTypes = Arrays.asList(getResources().getStringArray(R.array.currency_types));
                    currencyTypeSpinner.setSelection(currencyTypes.indexOf(setting.getValue()));
                }
            }
        })).execute();
    }

    private void configureButtonSaveSettings() {
        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog loadingDialog = loadingDialog(AppSettingsActivity.this);
                loadingDialog.show();
                new AsyncTask(new AsyncTask.AsyncTaskInterface() {
                    @Override
                    public List<Object> doInBackground() {
                        createOrUpdateUsernameSetting();
                        createOrUpdateEnableToastsSetting();
                        createOrUpdateCurrencyTypeSetting();
                        return null;
                    }

                    @Override
                    public void onPostExecute(List<Object> objects) {
                        loadingDialog.dismiss();
                        finish();
                    }
                }).execute();
            }
        });
    }

    private void createOrUpdateEnableToastsSetting() {
        String enableToastsValue = "false";
        if (enableToasts.isChecked()){
            enableToastsValue = "true";
        }
        Setting enableToastsSetting = roomSettingDAO.getEnableToastsSetting();
        if (enableToastsSetting == null){
            roomSettingDAO.save(new Setting(ENABLE_TOASTS,enableToastsValue));
        }else{
            roomSettingDAO.update(new Setting(enableToastsSetting.getId(),ENABLE_TOASTS,enableToastsValue));
        }
    }

    private void createOrUpdateUsernameSetting() {
        String username = inputUsername.getText().toString();
        Setting usernameSetting = roomSettingDAO.getUsernameSetting();
        if (usernameSetting == null){
            Log.i("numberOfUsernameSetting","It is null");
            roomSettingDAO.save(new Setting(USERNAME_FOR_APP,username));
        }else{
            roomSettingDAO.update(new Setting(usernameSetting.getId(),USERNAME_FOR_APP,username));
        }
    }

    private void createOrUpdateCurrencyTypeSetting(){
        Setting currencyTypeSetting = roomSettingDAO.getCurrencyTypeSetting();
        if (currencyTypeSetting == null){
            Log.i("numberOfCurrencySetting","It is null");
            roomSettingDAO.save(new Setting(CURRENCY_TYPE,spinnerValue));
        }else{
            roomSettingDAO.update(new Setting(currencyTypeSetting.getId(),CURRENCY_TYPE,spinnerValue));
        }
    }

    private void getLayoutViews() {
        inputUsername = findViewById(R.id.input_username);
        enableToasts = findViewById(R.id.enable_toasts);
        currencyTypeSpinner = findViewById(R.id.currency_type);
        buttonSaveSettings = findViewById(R.id.button_save_settings);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("selectedItemSpinner",(String) parent.getItemAtPosition(position));
        spinnerValue = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}