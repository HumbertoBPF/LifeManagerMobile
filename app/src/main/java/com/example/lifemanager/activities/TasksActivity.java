package com.example.lifemanager.activities;

import android.os.Bundle;
import android.view.Menu;

import com.example.lifemanager.R;

public class TasksActivity extends CategoryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[2];
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        titleIconAppbar = "Add task";
        return super.onPrepareOptionsMenu(menu);
    }
}