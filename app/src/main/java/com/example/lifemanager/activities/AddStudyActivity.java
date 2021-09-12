package com.example.lifemanager.activities;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;

public class AddStudyActivity extends AddResourceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getString(R.string.title_appbar_studies_form);
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