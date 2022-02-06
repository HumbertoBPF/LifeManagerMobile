package com.example.lifemanager.activities;

import static com.example.lifemanager.util.Tools.loadingDialog;
import static com.example.lifemanager.util.Tools.setActionBarColor;
import static com.example.lifemanager.util.Tools.setActionBarTitle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.dao.BaseDAO;

import java.util.List;

public abstract class CategoryActivity<E> extends AppCompatActivity {

    protected String titleAppbar = null;
    protected Integer colorAppbar = null;
    protected String titleIconAppbar = null;
    protected String resourceType = null;

    protected Class<?> formAddClass;
    protected BaseDAO<E> categoryDAO;

    protected RecyclerView recyclerViewResources;
    protected ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, titleAppbar);
        setActionBarColor(this, colorAppbar);
        setContentView(R.layout.activity_category);
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
        if (titleIconAppbar != null){
            menu.findItem(R.id.action_add).setTitle(titleIconAppbar);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void configureAdapter() {
        loadingDialog.show();
        categoryDAO.getAllRecordsTask(result -> {
            recyclerViewResources.setAdapter(initializeAdapter(result));
            registerForContextMenu(recyclerViewResources);
            loadingDialog.dismiss();
        }).execute();
    }

    protected abstract RecyclerView.Adapter initializeAdapter(List<E> list);

    @Override
    protected void onResume() {
        super.onResume();
        configureAdapter();
    }

}