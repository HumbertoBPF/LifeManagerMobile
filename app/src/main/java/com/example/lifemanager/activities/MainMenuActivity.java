package com.example.lifemanager.activities;

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
import com.example.lifemanager.Settings;
import com.example.lifemanager.activities.finances.FinancesActivity;
import com.example.lifemanager.activities.studies.StudiesActivity;
import com.example.lifemanager.activities.tasks.TasksActivity;
import com.example.lifemanager.adapters.ListResourcesMenuAdapter;
import com.example.lifemanager.model.RoundedButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    private List<RoundedButton> roundedButtons = new ArrayList<>();
    private RecyclerView resourcesList;
    private TextView dateTextView;
    private AlertDialog settingsDialog;
    private Settings settings;

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
        if (currentHour < 12) {
            dateTextView.setText(getString(R.string.greeting_morning)+settings.getUsername());
        }else if (currentHour < 18) {
            dateTextView.setText(getString(R.string.greeting_afternoon)+settings.getUsername());
        }else {
            dateTextView.setText(getString(R.string.greeting_evening)+settings.getUsername());
        }
    }

    private void askForSettings() {
        if (settings.getCurrencyFormat() == null||settings.getToastsEnabled() == null){
            settingsDialog = yesOrNoDialog(this, getString(R.string.settings_dialog_title),
                    getString(R.string.settings_dialog_message), getString(R.string.settings_dialog_yes),
                    getString(R.string.settings_dialog_no),
                    (dialogInterface, i) -> startActivity(new Intent(getApplicationContext(), AppSettingsActivity.class)),
                    (dialogInterface, i) -> {
                        settings.defaultSettings();
                        settingsDialog.dismiss();
                    });
            settingsDialog.setCanceledOnTouchOutside(false);
            settingsDialog.show();
        }
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
        settings = new Settings(this);
        askForSettings();
        configureGreeting();
        super.onResume();
    }
}