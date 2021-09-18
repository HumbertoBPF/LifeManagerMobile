package com.example.lifemanager.activities.studies;

import static com.example.lifemanager.enums.Category.*;
import static com.example.lifemanager.enums.Category.FRONTEND;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.AddResourceActivity;
import com.example.lifemanager.dao.RoomStudiesDAO;
import com.example.lifemanager.enums.Category;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;
import com.example.lifemanager.tools.Util;

import java.util.Arrays;
import java.util.List;

public class AddStudyActivity extends AddResourceActivity {

    private RoomStudiesDAO roomStudiesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getString(R.string.title_appbar_studies_form);
        colorAppbar = getResources().getColor(R.color.color_studies_item);
        super.onCreate(savedInstanceState);
        roomStudiesDAO = LifeManagerDatabase.getInstance(this).getRoomStudiesDAO();
        makeStudiesFormVisible();

        studiesFormButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = studiesFormName.getText().toString();
                String linkCourse = studiesFormLinkCourse.getText().toString();
                String position = studiesFormPosition.getText().toString();
                boolean status = false;
                if (studiesFormConcluded.isChecked()){
                    status = true;
                }
                Category category = getCategory();
                roomStudiesDAO.save(new Studies(name,linkCourse,category,Integer.parseInt(position),status));
                finish();
            }
        });

    }

    private Category getCategory(){
        switch (spinnerValue){
            case "Front-end":
                return FRONTEND;
            case "Mobile":
                return MOBILE;
            case "Data science":
                return DATA_SCIENCE;
            case "DevOps":
                return DEVOPS;
            case "General programming":
                return GENERAL_PROGRAMMING;
            case "Languages":
                return LANGUAGES;
            case "Other":
                return OTHER;
            default:
                return BACKEND;
        }
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