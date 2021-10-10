package com.example.lifemanager.activities;

import static com.example.lifemanager.model.Constants.ENABLE_TOASTS;
import static com.example.lifemanager.model.Constants.USERNAME_FOR_APP;
import static com.example.lifemanager.tools.Util.yesOrNoDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.finances.FinancesActivity;
import com.example.lifemanager.activities.studies.StudiesActivity;
import com.example.lifemanager.activities.tasks.TasksActivity;
import com.example.lifemanager.async_tasks.AsyncTask;
import com.example.lifemanager.dao.RoomSettingDAO;
import com.example.lifemanager.model.Setting;
import com.example.lifemanager.recycler_view.ListResourcesMenuAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    private List<String> resourcesNames = new ArrayList<>();
    private ConstraintLayout root;
    private RecyclerView resourcesList;
    private TextView dateTextView;
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;
    private RoomSettingDAO roomSettingDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roomSettingDAO = LifeManagerDatabase.getInstance(getApplicationContext()).getRoomSettingDAO();
        getLayoutViews();
        getScreenDimensions();
        placeViews();
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
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(roomSettingDAO.getUsernameSetting());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Setting setting = (Setting) objects.get(0);
                SimpleDateFormat formatter = new SimpleDateFormat("H");
                String currentHourString =formatter.format(Calendar.getInstance().getTime());
                int currentHour = Integer.parseInt(currentHourString);

                String username = "";
                if (setting != null){
                    username = setting.getValue();
                }
                if (currentHour < 12) {
                    dateTextView.setText(getResources().getString(R.string.greeting_morning)+username);
                }else if (currentHour < 18) {
                    dateTextView.setText(getResources().getString(R.string.greeting_afternoon)+username);
                }else {
                    dateTextView.setText(getResources().getString(R.string.greeting_evening)+username);
                }
            }
        }).execute();
    }

    private void askForSettings() {
        Context context = this;
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(roomSettingDAO.getUsernameSetting());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Setting setting = (Setting) objects.get(0);
                if (setting == null){
                    Log.i("settingsDialog","Launch settings dialog");
                    AlertDialog settingsDialog = yesOrNoDialog(context, getResources().getString(R.string.settings_dialog_title),
                            getResources().getString(R.string.settings_dialog_message),
                            getResources().getString(R.string.settings_dialog_yes),
                            getResources().getString(R.string.settings_dialog_no),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getApplicationContext(), AppSettingsActivity.class));
                                }
                            },
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    defaultSettings();
                                }
                            });
                    settingsDialog.setCanceledOnTouchOutside(false);
                    settingsDialog.show();
                }
            }
        }).execute();
    }

    private void defaultSettings() {
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                roomSettingDAO.save(new Setting(USERNAME_FOR_APP,""));
                roomSettingDAO.save(new Setting(ENABLE_TOASTS,"true"));
                return null;
            }

            @Override
            public void onPostExecute(List<Object> objects) {

            }
        }).execute();
    }

    private void placeViews() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(root);

        constraintSet.connect(resourcesList.getId(), ConstraintSet.TOP, dateTextView.getId(), ConstraintSet.BOTTOM, 15);
        constraintSet.connect(resourcesList.getId(), ConstraintSet.BOTTOM, root.getId(), ConstraintSet.BOTTOM, 15);
        constraintSet.connect(resourcesList.getId(), ConstraintSet.START, root.getId(), ConstraintSet.START, 0);
        constraintSet.connect(resourcesList.getId(), ConstraintSet.END, root.getId(), ConstraintSet.END, 0);
        constraintSet.constrainHeight(resourcesList.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(resourcesList.getId(), SCREEN_WIDTH);

        constraintSet.applyTo(root);
    }

    private void configureAdapter() {
        resourcesNames.addAll(Arrays.asList(getResources().getStringArray(R.array.categories)));
        resourcesList.setAdapter(new ListResourcesMenuAdapter(this, resourcesNames, new ListResourcesMenuAdapter.OnClickListener() {
            @Override
            public void onItemClickListener(String category) {
                if (category.equals(getResources().getStringArray(R.array.categories)[0])){
                    startActivity(new Intent(getApplicationContext(), FinancesActivity.class));
                }else if (category.equals(getResources().getStringArray(R.array.categories)[1])){
                    startActivity(new Intent(getApplicationContext(), StudiesActivity.class));
                }else if (category.equals(getResources().getStringArray(R.array.categories)[2])){
                    startActivity(new Intent(getApplicationContext(), TasksActivity.class));
                }
            }
        }));
    }

    private void getScreenDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;
    }

    private void getLayoutViews() {
        root = findViewById(R.id.root);
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