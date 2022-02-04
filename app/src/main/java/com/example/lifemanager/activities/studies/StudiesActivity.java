package com.example.lifemanager.activities.studies;

import android.content.Intent;
import android.os.Bundle;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.async_tasks.AsyncTask;
import com.example.lifemanager.dao.StudiesDAO;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.recycler_view.StudiesAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.ArrayList;
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

    protected void configureAdapter() {
        loadingDialog.show();
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.addAll(((StudiesDAO) categoryDAO).getAllStudies());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                List<Studies> studies = new ArrayList<>();
                for (Object object : objects){
                    studies.add((Studies) object);
                }
                adapter = new StudiesAdapter(StudiesActivity.this, studies, new StudiesAdapter.OnClickListener() {
                    @Override
                    public void onItemClickListener(Studies study) {
                        Intent intent = new Intent(getApplicationContext(), DetailedStudyActivity.class);
                        intent.putExtra(resourceType,study);
                        startActivity(intent);
                    }
                });
                recyclerViewResources.setAdapter(adapter);
                registerForContextMenu(recyclerViewResources);
                loadingDialog.dismiss();
            }
        }).execute();
    }

}