package com.example.lifemanager.activities.studies;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.adapters.StudiesAdapter;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class StudiesActivity extends CategoryActivity<Studies> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[1];
        colorAppbar = getResources().getColor(R.color.color_studies_item);
        titleIconAppbar = getString(R.string.title_appbar_studies_form);
        resourceType = getResources().getStringArray(R.array.categories)[1];
        formAddClass = AddStudyActivity.class;
        super.onCreate(savedInstanceState);
        categoryDAO = LifeManagerDatabase.getInstance(this).getRoomStudiesDAO();
    }

    @Override
    protected RecyclerView.Adapter initializeAdapter(List<Studies> list) {
        return new StudiesAdapter(StudiesActivity.this, list, study -> {
            Intent intent = new Intent(getApplicationContext(), DetailedStudyActivity.class);
            intent.putExtra(resourceType,study);
            startActivity(intent);
        });
    }

}