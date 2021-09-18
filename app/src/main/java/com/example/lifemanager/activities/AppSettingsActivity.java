package com.example.lifemanager.activities;

import static com.example.lifemanager.model.Constants.USERNAME_FOR_APP;
import static com.example.lifemanager.tools.Util.setActionBarTitle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifemanager.R;
import com.example.lifemanager.dao.RoomSettingDAO;
import com.example.lifemanager.model.Setting;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class AppSettingsActivity extends AppCompatActivity {

    private RoomSettingDAO roomSettingDAO;
    private EditText inputUsername;
    private Button buttonSaveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, getResources().getString(R.string.title_appbar_settings));
        setContentView(R.layout.activity_app_settings);

        roomSettingDAO = LifeManagerDatabase.getInstance(getApplicationContext()).getRoomSettingDAO();
        getLayoutViews();
        fillUsernameSetting();
        configureButtonSaveSettings();
    }

    private void fillUsernameSetting() {
        List<Setting> usernameSetting = roomSettingDAO.getUsernameSetting();
        if (usernameSetting != null){
            if (!usernameSetting.isEmpty()){
                inputUsername.setText(usernameSetting.get(0).getValue());
            }
        }
    }

    private void configureButtonSaveSettings() {
        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrUpdateUsernameSetting();
                finish();
            }
        });
    }

    private void createOrUpdateUsernameSetting() {
        String username = inputUsername.getText().toString();
        List<Setting> usernameSetting = roomSettingDAO.getUsernameSetting();
        if (usernameSetting == null){
            Log.i("numberOfUsernameSetting","It is null");
            roomSettingDAO.save(new Setting(USERNAME_FOR_APP,username));
        }else{
            Log.i("numberOfUsernameSetting",roomSettingDAO.getUsernameSetting().size()+"");
            if (usernameSetting.isEmpty()){
                roomSettingDAO.save(new Setting(USERNAME_FOR_APP,username));
            }else{
                roomSettingDAO.update(new Setting(usernameSetting.get(0).getId(),USERNAME_FOR_APP,username));
            }
        }
    }

    private void getLayoutViews() {
        inputUsername = findViewById(R.id.input_username);
        buttonSaveSettings = findViewById(R.id.button_save_settings);
    }
}