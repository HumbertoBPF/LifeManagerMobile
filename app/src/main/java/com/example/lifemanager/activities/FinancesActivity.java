package com.example.lifemanager.activities;

import android.os.Bundle;
import android.view.Menu;

import com.example.lifemanager.R;

public class FinancesActivity extends CategoryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[0];
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        titleIconAppbar = "Add finance";
        return super.onPrepareOptionsMenu(menu);
    }
}