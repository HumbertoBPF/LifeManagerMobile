package com.example.lifemanager.activities;

import static com.example.lifemanager.model.Constants.CURRENCY_TYPE;
import static com.example.lifemanager.model.Constants.ENABLE_TOASTS;
import static com.example.lifemanager.model.Constants.USERNAME_FOR_APP;
import static com.example.lifemanager.util.Tools.getSettingFromSharedPref;
import static com.example.lifemanager.util.Tools.saveSettingOnSharedPref;
import static com.example.lifemanager.util.Tools.yesOrNoDialog;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.finances.FinancesActivity;
import com.example.lifemanager.activities.studies.StudiesActivity;
import com.example.lifemanager.activities.tasks.TasksActivity;
import com.example.lifemanager.adapters.ListResourcesMenuAdapter;
import com.example.lifemanager.model.RoundedButton;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity {

    private List<RoundedButton> roundedButtons = new ArrayList<>();
    private RecyclerView resourcesList;
    private TextView dateTextView;
    private AlertDialog settingsDialog;
    public static boolean ARE_TOASTS_ENABLED;
    public static NumberFormat CURRENCY_FORMAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLayoutViews();
        configureAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_syncro:
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, AppSettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureGreeting() {
        SimpleDateFormat formatter = new SimpleDateFormat("H");
        String currentHourString =formatter.format(Calendar.getInstance().getTime());
        int currentHour = Integer.parseInt(currentHourString);
        String username = getSettingFromSharedPref(this,USERNAME_FOR_APP);
        if (currentHour < 12) {
            dateTextView.setText(getString(R.string.greeting_morning)+username);
        }else if (currentHour < 18) {
            dateTextView.setText(getString(R.string.greeting_afternoon)+username);
        }else {
            dateTextView.setText(getString(R.string.greeting_evening)+username);
        }
    }

    private void askForSettings() {
        String enableToastsSetting = getSettingFromSharedPref(this,ENABLE_TOASTS);
        String currencyTypeSetting = getSettingFromSharedPref(this,CURRENCY_TYPE);
        if (enableToastsSetting.isEmpty()||currencyTypeSetting.isEmpty()){
            settingsDialog = yesOrNoDialog(this, getString(R.string.settings_dialog_title),
                    getString(R.string.settings_dialog_message), getString(R.string.settings_dialog_yes),
                    getString(R.string.settings_dialog_no),
                    (dialogInterface, i) -> startActivity(new Intent(getApplicationContext(), AppSettingsActivity.class)),
                    (dialogInterface, i) -> {
                        defaultSettings();
                        settingsDialog.dismiss();
                    });
            settingsDialog.setCanceledOnTouchOutside(false);
            settingsDialog.show();
        }else{
            CURRENCY_FORMAT = getCurrencyFormat(currencyTypeSetting);
            ARE_TOASTS_ENABLED = enableToastsSetting.equals("true");
        }
    }

    private NumberFormat getCurrencyFormat(String currencyTypeSetting) {
        String[] currencyTypes = getResources().getStringArray(R.array.currency_types);
        if (currencyTypeSetting.equals(currencyTypes[0])){
            return NumberFormat.getCurrencyInstance(Locale.FRANCE);
        }
        return NumberFormat.getCurrencyInstance(Locale.US);
    }

    private void defaultSettings() {
        saveSettingOnSharedPref(this,USERNAME_FOR_APP,"");
        saveSettingOnSharedPref(this,ENABLE_TOASTS,"true");
        saveSettingOnSharedPref(this,CURRENCY_TYPE,getResources().getStringArray(R.array.currency_types)[0]);
        CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);
        ARE_TOASTS_ENABLED = true;
    }

    private void configureAdapter() {
        roundedButtons.clear();
        roundedButtons.add(new RoundedButton("Finances",R.color.color_finances_item,FinancesActivity.class));
        roundedButtons.add(new RoundedButton("Studies",R.color.color_studies_item,StudiesActivity.class));
        roundedButtons.add(new RoundedButton("Tasks",R.color.color_tasks_item,TasksActivity.class));
        resourcesList.setAdapter(new ListResourcesMenuAdapter(this, roundedButtons));
    }

    private void getLayoutViews() {
        resourcesList = findViewById(R.id.resources_list);
        dateTextView = findViewById(R.id.date_text_view);
    }

    @Override
    protected void onResume() {
        askForSettings();
        configureGreeting();
        super.onResume();
    }
}