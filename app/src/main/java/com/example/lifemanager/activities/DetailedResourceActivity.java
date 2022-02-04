package com.example.lifemanager.activities;

import static com.example.lifemanager.tools.Util.setActionBarColor;
import static com.example.lifemanager.tools.Util.setActionBarTitle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class DetailedResourceActivity extends AppCompatActivity {

    protected String titleAppBar = null;
    protected Integer colorAppBar = null;
    protected String resourceType = null;

    protected int layoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, titleAppBar);
        setActionBarColor(this, colorAppBar);
        setContentView(layoutId);
        getLayoutViews();
        Intent intent = getIntent();
        Object object = intent.getSerializableExtra(resourceType);
        if (object != null){
            bind(object);
        }
    }

    protected abstract void getLayoutViews();

    abstract protected void bind(Object object);

}