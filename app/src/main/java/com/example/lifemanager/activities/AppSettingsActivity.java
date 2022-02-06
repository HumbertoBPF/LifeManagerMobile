package com.example.lifemanager.activities;

import static com.example.lifemanager.util.Tools.onViewDrawn;
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
import com.example.lifemanager.Settings;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AppSettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String spinnerValue;
    private EditText inputUsername;
    private CheckBox enableToasts;
    private Spinner currencyTypeSpinner;
    private Button buttonSaveSettings;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, getResources().getString(R.string.title_appbar_settings));
        setContentView(R.layout.activity_app_settings);

        getLayoutViews();
        settings = new Settings(this);
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
        inputUsername.setText(settings.getUsername());
        enableToasts.setChecked(settings.getToastsEnabled());
        String currencyTypeSetting = settings.getCurrencyFormat().equals(NumberFormat.getCurrencyInstance(Locale.FRANCE))?"Euro":"Dollar";
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
            settings.setUsername(inputUsername.getText().toString());
            settings.setToastsEnabled(enableToasts.isChecked());
            settings.setCurrencyFormat(spinnerValue.equals("Euro")?
                    NumberFormat.getCurrencyInstance(Locale.FRANCE):NumberFormat.getCurrencyInstance(Locale.US));
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