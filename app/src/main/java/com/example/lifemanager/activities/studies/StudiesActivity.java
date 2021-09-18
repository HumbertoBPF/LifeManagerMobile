package com.example.lifemanager.activities.studies;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.dao.RoomStudiesDAO;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.recycler_view.ListStudiesAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class StudiesActivity extends CategoryActivity {

    private RoomStudiesDAO roomStudiesDAO;
    private List<Studies> studies;
    private ListStudiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[1];
        colorAppbar = getResources().getColor(R.color.color_studies_item);
        super.onCreate(savedInstanceState);
        roomStudiesDAO = LifeManagerDatabase.getInstance(this).getRoomStudiesDAO();
        configureAdapter();
    }

    private void configureAdapter() {
        studies = roomStudiesDAO.getAllStudies();
        adapter = new ListStudiesAdapter(this, studies);
        recyclerViewResources.setAdapter(adapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        configureAdapter();
    }

}