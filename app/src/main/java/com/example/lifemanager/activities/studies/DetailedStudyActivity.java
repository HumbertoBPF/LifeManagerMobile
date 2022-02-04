package com.example.lifemanager.activities.studies;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.DetailedResourceActivity;
import com.example.lifemanager.model.Studies;

public class DetailedStudyActivity extends DetailedResourceActivity {

    private TextView studyDetailName;
    private TextView studyDetailLinkCourse;
    private TextView studyDetailCategory;
    private TextView studyDetailPosition;
    private TextView studyDetailStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppBar = getString(R.string.title_appbar_details_study);
        colorAppBar = getResources().getColor(R.color.color_studies_item);
        resourceType = getResources().getStringArray(R.array.categories)[1];
        layoutId = R.layout.activity_detailed_study;
        super.onCreate(savedInstanceState);
    }

    protected void getLayoutViews() {
        studyDetailName = findViewById(R.id.study_detail_name);
        studyDetailLinkCourse = findViewById(R.id.study_detail_link_course);
        studyDetailCategory = findViewById(R.id.study_detail_category);
        studyDetailPosition = findViewById(R.id.study_detail_position);
        studyDetailStatus = findViewById(R.id.study_detail_status);
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