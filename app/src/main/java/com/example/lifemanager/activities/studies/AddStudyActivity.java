package com.example.lifemanager.activities.studies;

import static com.example.lifemanager.enums.Category.BACKEND;
import static com.example.lifemanager.enums.Category.DATA_SCIENCE;
import static com.example.lifemanager.enums.Category.DEVOPS;
import static com.example.lifemanager.enums.Category.FRONTEND;
import static com.example.lifemanager.enums.Category.GENERAL_PROGRAMMING;
import static com.example.lifemanager.enums.Category.LANGUAGES;
import static com.example.lifemanager.enums.Category.MOBILE;
import static com.example.lifemanager.enums.Category.OTHER;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.AddResourceActivity;
import com.example.lifemanager.async_tasks.AsyncTask;
import com.example.lifemanager.dao.RoomStudiesDAO;
import com.example.lifemanager.enums.Category;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;
import com.example.lifemanager.tools.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddStudyActivity extends AddResourceActivity {

    private RoomStudiesDAO roomStudiesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        roomStudiesDAO = LifeManagerDatabase.getInstance(this).getRoomStudiesDAO();
        titleAppbar = getResources().getString(R.string.title_appbar_studies_form);
        colorAppbar = getResources().getColor(R.color.color_studies_item);
        resourceType = getResources().getStringArray(R.array.categories)[1];
        super.onCreate(savedInstanceState);

        if (idToUpdate == null){
            fillWithNextPosition();
        }
    }

    private void fillWithNextPosition() {
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(roomStudiesDAO.getMaxPosition());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                studiesFormPosition.setText((((Integer) objects.get(0)) + 1)+"");
            }
        }).execute();
    }

    protected void configureFormButton() {
        studiesFormButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = studiesFormName.getText().toString();
                String linkCourse = studiesFormLinkCourse.getText().toString();
                String position = studiesFormPosition.getText().toString();
                boolean status = studiesFormConcluded.isChecked();
                Category category = getCategory();
                new AsyncTask(new AsyncTask.AsyncTaskInterface() {
                    @Override
                    public List<Object> doInBackground() {
                        if (idToUpdate == null){
                            roomStudiesDAO.save(new Studies(name,linkCourse,category,Integer.parseInt(position),status));
                        }else{
                            roomStudiesDAO.update(
                                    new Studies(idToUpdate,name,linkCourse,category, Integer.parseInt(position),status));
                        }
                        return null;
                    }

                    @Override
                    public void onPostExecute(List<Object> objects) {
                        if (idToUpdate == null){
                            Util.showToast(getApplicationContext(),getResources().getString(R.string.add_study_toast_message));
                        }else{
                            Util.showToast(getApplicationContext(),getResources().getString(R.string.update_study_toast_message));
                        }
                        finish();
                    }
                }).execute();
            }
        });
    }

    protected void fillForm(Object object) {
        Studies study = (Studies) object;
        idToUpdate = study.getId();
        studiesFormName.setText(study.getName());
        studiesFormLinkCourse.setText(study.getLinkCourse());
        studiesFormPosition.setText(study.getPosition().toString());
        studiesFormStatus.check(R.id.studies_form_status_pending);
        if (study.getStatus()){
            studiesFormStatus.check(R.id.studies_form_status_concluded);
        }
        List<String> categories = Arrays.asList(getResources().getStringArray(R.array.categories_study));
        studiesFormCategorySpinner.setSelection(categories.indexOf(study.getCategory().getValue()));
    }

    private Category getCategory(){
        switch (spinnerValue){
            case "Front-end":
                return FRONTEND;
            case "Mobile":
                return MOBILE;
            case "Data Science":
                return DATA_SCIENCE;
            case "DevOps":
                return DEVOPS;
            case "General Programming":
                return GENERAL_PROGRAMMING;
            case "Languages":
                return LANGUAGES;
            case "Other":
                return OTHER;
        }
        return BACKEND;
    }

    protected void makeFormVisible() {
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