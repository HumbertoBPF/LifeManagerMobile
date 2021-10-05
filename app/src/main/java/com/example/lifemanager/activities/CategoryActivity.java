package com.example.lifemanager.activities;

import static com.example.lifemanager.tools.Util.loadingDialog;
import static com.example.lifemanager.tools.Util.setActionBarColor;
import static com.example.lifemanager.tools.Util.setActionBarTitle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;

public abstract class CategoryActivity extends AppCompatActivity {

    protected String titleAppbar = null;
    protected Integer colorAppbar = null;
    protected String titleIconAppbar = null;
    protected String resourceType = null;
    private MenuItem addItem;
    protected Context context;
    protected Class<?> formAddClass;
    protected RecyclerView recyclerViewResources;
    protected ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, titleAppbar);
        setActionBarColor(this, colorAppbar);
        setContentView(R.layout.activity_category);
        context = this;
        recyclerViewResources = findViewById(R.id.recycler_view_resources);
        loadingDialog = loadingDialog(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(this,formAddClass));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        addItem = menu.findItem(R.id.action_add);
        if (titleIconAppbar != null){
            addItem.setTitle(titleIconAppbar);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    abstract protected void configureAdapter();

    @Override
    protected void onResume() {
        super.onResume();
        configureAdapter();
    }

}