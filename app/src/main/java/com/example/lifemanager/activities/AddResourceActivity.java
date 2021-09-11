package com.example.lifemanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lifemanager.R;

public class AddResourceActivity extends AppCompatActivity {

    protected Spinner categoryStudySpinner;
    protected String titleAppbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (titleAppbar != null){
            setTitle(titleAppbar);
        }
        setContentView(R.layout.activity_add_resource);
        categoryStudySpinner = findViewById(R.id.studies_form_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_study, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryStudySpinner.setAdapter(adapter);
    }

}