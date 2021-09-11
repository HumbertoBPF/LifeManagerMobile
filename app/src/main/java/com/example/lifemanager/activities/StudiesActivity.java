package com.example.lifemanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lifemanager.R;

public class StudiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getStringArray(R.array.categories)[1]);
        setContentView(R.layout.activity_studies);
    }
}