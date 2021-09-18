package com.example.lifemanager.activities.studies;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;

public class StudiesActivity extends CategoryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[1];
        colorAppbar = getResources().getColor(R.color.color_studies_item);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        titleIconAppbar = getResources().getString(R.string.title_appbar_studies_form);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        formAddClass = AddStudyActivity.class;
        return super.onOptionsItemSelected(item);
    }
}