package com.example.lifemanager.activities;

import static com.example.lifemanager.model.Constants.ENABLE_TOASTS;
import static com.example.lifemanager.model.Constants.USERNAME_FOR_APP;
import static com.example.lifemanager.tools.Util.setActionBarTitle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifemanager.R;
import com.example.lifemanager.dao.RoomSettingDAO;
import com.example.lifemanager.model.Setting;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

public class AppSettingsActivity extends AppCompatActivity {

    private RoomSettingDAO roomSettingDAO;
    private EditText inputUsername;
    private RadioGroup enableToasts;
    private RadioButton enableOption;
    private RadioButton disableOption;
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
    }

    private void fillSettings() {
        fillUsernameSetting();
        fillEnableToastsSetting();
    }

    private void fillEnableToastsSetting() {
        Setting enableToastsSetting = roomSettingDAO.getEnableToastsSetting();
        if (enableToastsSetting != null){
            enableToasts.check(R.id.enable_toasts_false);
            if (enableToastsSetting.getValue().equals("true")){
                enableToasts.check(R.id.enable_toasts_true);
            }
        }
    }

    private void fillUsernameSetting() {
        Setting usernameSetting = roomSettingDAO.getUsernameSetting();
        if (usernameSetting != null){
                inputUsername.setText(usernameSetting.getValue());
        }
    }

    private void configureButtonSaveSettings() {
        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrUpdateUsernameSetting();
                createOrUpdateEnableToastsSetting();
                finish();
            }
        });
    }

    private void createOrUpdateEnableToastsSetting() {
        String enableToastsValue = "false";
        if (enableOption.isChecked()){
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

    private void getLayoutViews() {
        inputUsername = findViewById(R.id.input_username);
        buttonSaveSettings = findViewById(R.id.button_save_settings);
        enableToasts = findViewById(R.id.enable_toasts);
        enableOption = findViewById(R.id.enable_toasts_true);
        disableOption = findViewById(R.id.enable_toasts_false);
    }
}