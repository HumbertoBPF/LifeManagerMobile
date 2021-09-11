package com.example.lifemanager.activities;

import android.os.Bundle;
import android.view.Menu;

import com.example.lifemanager.R;

public class StudiesActivity extends CategoryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[1];
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        titleIconAppbar = "Add study";
        return super.onPrepareOptionsMenu(menu);
    }
}