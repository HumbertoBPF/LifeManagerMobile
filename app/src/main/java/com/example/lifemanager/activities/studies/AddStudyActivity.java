package com.example.lifemanager.activities.studies;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.AddResourceActivity;

public class AddStudyActivity extends AddResourceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getString(R.string.title_appbar_studies_form);
        colorAppbar = getResources().getColor(R.color.color_studies_item);
        super.onCreate(savedInstanceState);
        makeStudiesFormVisible();
    }

    private void makeStudiesFormVisible() {
        studiesFormName.setVisibility(View.VISIBLE);
        studiesFormLinkCourse.setVisibility(View.VISIBLE);
        studiesFormPosition.setVisibility(View.VISIBLE);
        studiesFormStatusLabel.setVisibility(View.VISIBLE);
        studiesFormStatus.setVisibility(View.VISIBLE);
        studiesFormConcluded.setVisibility(View.VISIBLE);
        studiesFormPending.setVisibility(View.VISIBLE);
        studiesFormCategorySpinnerLabel.setVisibility(View.VISIBLE);
        studiesFormCategorySpinner.setVisibility(View.VISIBLE);
        studiesFormButtonSubmit.setVisibility(View.VISIBLE);
    }
}