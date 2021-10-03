package com.example.lifemanager.activities.studies;

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
        resourceType = getResources().getStringArray(R.array.categories)[1];
        super.onCreate(savedInstanceState);
    }

    protected void makeViewsVisible() {
        studyDetailName.setVisibility(View.VISIBLE);
        studyDetailLinkCourse.setVisibility(View.VISIBLE);
        studyDetailPosition.setVisibility(View.VISIBLE);
        studyDetailCategory.setVisibility(View.VISIBLE);
        studyDetailStatus.setVisibility(View.VISIBLE);
    }

    protected void bind(Object object){
        Studies study = (Studies) object;
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