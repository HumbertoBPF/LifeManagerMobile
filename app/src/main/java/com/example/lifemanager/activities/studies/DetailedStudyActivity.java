package com.example.lifemanager.activities.studies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.DetailedResourceActivity;
import com.example.lifemanager.model.Studies;

public class DetailedStudyActivity extends DetailedResourceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppBar = getResources().getString(R.string.title_appbar_details_study);
        colorAppBar = getResources().getColor(R.color.color_studies_item);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Studies study = (Studies) intent.getSerializableExtra("study");
        makeViewsVisible();
        bind(study);
    }

    protected void makeViewsVisible() {
        studyDetailName.setVisibility(View.VISIBLE);
        studyDetailLinkCourse.setVisibility(View.VISIBLE);
        studyDetailPosition.setVisibility(View.VISIBLE);
        studyDetailCategory.setVisibility(View.VISIBLE);
        studyDetailStatus.setVisibility(View.VISIBLE);
    }

    private void bind(Studies study){
        studyDetailName.setText("Name: "+study.getName());
        studyDetailLinkCourse.setText("Link associated: "+study.getLinkCourse());
        studyDetailPosition.setText("Priority position: "+study.getPosition().toString());
        studyDetailCategory.setText("Category: "+study.getCategory().getValue());
        String statusString = "Pending";
        if (study.getStatus()){
            statusString = "Concluded";
        }
        studyDetailStatus.setText("Status: "+statusString);
    }

}