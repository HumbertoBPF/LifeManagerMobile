package com.example.lifemanager.activities;

import static com.example.lifemanager.model.Constants.CURRENCY_TYPE;
import static com.example.lifemanager.model.Constants.ENABLE_TOASTS;
import static com.example.lifemanager.model.Constants.USERNAME_FOR_APP;
import static com.example.lifemanager.util.Tools.getSettingFromSharedPref;
import static com.example.lifemanager.util.Tools.onViewDrawn;
import static com.example.lifemanager.util.Tools.saveSettingOnSharedPref;
import static com.example.lifemanager.util.Tools.setActionBarTitle;

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

import java.util.Arrays;
import java.util.List;

public class AppSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String spinnerValue;
    private EditText inputUsername;
    private CheckBox enableToasts;
    private Spinner currencyTypeSpinner;
    private Button buttonSaveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, getResources().getString(R.string.title_appbar_settings));
        setContentView(R.layout.activity_app_settings);

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
        inputUsername.setText(getSettingFromSharedPref(this,USERNAME_FOR_APP));
        enableToasts.setChecked(getSettingFromSharedPref(this,ENABLE_TOASTS).equals("true"));
        String currencyTypeSetting = getSettingFromSharedPref(this,CURRENCY_TYPE);
        Log.i("HELLO","currencyType = "+currencyTypeSetting);
        onViewDrawn(currencyTypeSpinner, () -> {
            if (!currencyTypeSetting.isEmpty()){
                List<String> currencyTypes = Arrays.asList(getResources().getStringArray(R.array.currency_types));
                Log.i("HELLO",currencyTypes.indexOf(currencyTypeSetting)+"");
                currencyTypeSpinner.setSelection(currencyTypes.indexOf(currencyTypeSetting));
            }
        });
    }

    private void configureButtonSaveSettings() {
        buttonSaveSettings.setOnClickListener(view -> {
            saveSettingOnSharedPref(getApplicationContext(),USERNAME_FOR_APP,inputUsername.getText().toString());
            saveSettingOnSharedPref(getApplicationContext(),ENABLE_TOASTS,enableToasts.isChecked()?"true":"false");
            saveSettingOnSharedPref(getApplicationContext(),CURRENCY_TYPE,spinnerValue);
            finish();
        });
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