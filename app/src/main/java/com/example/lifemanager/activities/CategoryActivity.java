package com.example.lifemanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.tools.Util;

public class CategoryActivity extends AppCompatActivity {

    protected String titleAppbar = null;
    protected String titleIconAppbar = null;
    private MenuItem addItem;
    protected Class<?> formAddClass;
    protected RecyclerView recyclerViewResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (titleAppbar != null){
            setTitle(titleAppbar);
        }
        setContentView(R.layout.activity_category);
        recyclerViewResources = findViewById(R.id.recycler_view_resources);
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

}