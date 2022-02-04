package com.example.lifemanager.activities;

import static com.example.lifemanager.tools.Util.loadingDialog;
import static com.example.lifemanager.tools.Util.setActionBarColor;
import static com.example.lifemanager.tools.Util.setActionBarTitle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifemanager.R;
import com.example.lifemanager.dao.BaseDAO;

public abstract class AddResourceActivity<E> extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    protected BaseDAO<E> categoryDAO;

    protected ProgressDialog loadingDialog;

    protected String spinnerValue;

    protected String titleAppbar = null;
    protected Integer colorAppbar = null;
    protected String resourceType = null;

    protected Long idToUpdate = null;

    protected int layoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, titleAppbar);
        setActionBarColor(this,colorAppbar);
        setContentView(layoutId);

        getLayoutViews();

        configureFormButton();

        Intent intent = getIntent();
        Object object = intent.getSerializableExtra(resourceType);
        if (object != null){
            fillForm(object);
        }

        loadingDialog = loadingDialog(this);
    }

    protected abstract void getLayoutViews();

    protected abstract void fillForm(Object object);

    protected abstract void configureFormButton();

    protected void configureSpinner(int arrayResourceId, Spinner spinner) {
        ArrayAdapter<CharSequence> adapterCategoryStudy = ArrayAdapter.createFromResource(getApplicationContext(),
                arrayResourceId, android.R.layout.simple_spinner_item);
        adapterCategoryStudy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategoryStudy);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerValue = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}