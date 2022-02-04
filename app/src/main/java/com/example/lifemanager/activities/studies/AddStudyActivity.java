package com.example.lifemanager.activities.studies;

import static com.example.lifemanager.enums.Category.BACKEND;
import static com.example.lifemanager.enums.Category.DATA_SCIENCE;
import static com.example.lifemanager.enums.Category.DEVOPS;
import static com.example.lifemanager.enums.Category.FRONTEND;
import static com.example.lifemanager.enums.Category.GENERAL_PROGRAMMING;
import static com.example.lifemanager.enums.Category.LANGUAGES;
import static com.example.lifemanager.enums.Category.MOBILE;
import static com.example.lifemanager.enums.Category.OTHER;
import static com.example.lifemanager.util.Tools.onViewDrawn;
import static com.example.lifemanager.util.Tools.showToast;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.AddResourceActivity;
import com.example.lifemanager.async_tasks.AsyncTask;
import com.example.lifemanager.dao.StudiesDAO;
import com.example.lifemanager.enums.Category;
import com.example.lifemanager.interfaces.OnTaskListener;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;
import com.example.lifemanager.util.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddStudyActivity extends AddResourceActivity<Studies> {

    private EditText studiesFormName;
    private EditText studiesFormLinkCourse;
    private EditText studiesFormPosition;
    private RadioGroup studiesFormStatus;
    private RadioButton studiesFormConcluded;
    private Spinner studiesFormCategorySpinner;
    private Button studiesFormButtonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        categoryDAO = LifeManagerDatabase.getInstance(this).getRoomStudiesDAO();
        titleAppbar = getString(R.string.title_appbar_studies_form);
        colorAppbar = getResources().getColor(R.color.color_studies_item);
        resourceType = getResources().getStringArray(R.array.categories)[1];
        layoutId = R.layout.activity_add_study;
        super.onCreate(savedInstanceState);
        configureSpinner(R.array.categories_study, studiesFormCategorySpinner);
        if (idToUpdate == null){
            fillWithNextPosition();
        }
    }

    private void fillWithNextPosition() {
        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                List<Object> objects = new ArrayList<>();
                objects.add(((StudiesDAO) categoryDAO).getMaxPosition());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Integer maxPosition = (Integer) objects.get(0);
                if (maxPosition != null){
                    studiesFormPosition.setText(((maxPosition) + 1)+"");
                }
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
                if (name.equals("")){
                    showToast(getApplicationContext(),"The name field is required");
                }else{
                    try{
                        Integer positionInteger = Integer.parseInt(position);
                        loadingDialog.show();
                        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
                            @Override
                            public List<Object> doInBackground() {
                                if (idToUpdate == null){
                                    categoryDAO.save(new Studies(name,linkCourse,category,positionInteger,status));
                                }else{
                                    categoryDAO.update(new Studies(idToUpdate,name,linkCourse,category,positionInteger,status));
                                }
                                return null;
                            }

                            @Override
                            public void onPostExecute(List<Object> objects) {
                                loadingDialog.dismiss();
                                if (idToUpdate == null){
                                    Tools.showToastIfEnabled(getApplicationContext(),getResources().getString(R.string.add_study_toast_message));
                                }else{
                                    Tools.showToastIfEnabled(getApplicationContext(),getResources().getString(R.string.update_study_toast_message));
                                }
                                finish();
                            }
                        }).execute();
                    }catch (Exception e){
                        showToast(getApplicationContext(),"The position field is required");
                    }
                }
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
        onViewDrawn(studiesFormCategorySpinner, new OnTaskListener() {
            @Override
            public void onTask() {
                List<String> categories = Arrays.asList(getResources().getStringArray(R.array.categories_study));
                studiesFormCategorySpinner.setSelection(categories.indexOf(study.getCategory().getValue()));
            }
        });
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

    protected void getLayoutViews() {
        studiesFormName = findViewById(R.id.studies_form_name);
        studiesFormLinkCourse = findViewById(R.id.studies_form_link_course);
        studiesFormPosition = findViewById(R.id.studies_form_position);
        studiesFormStatus = findViewById(R.id.studies_form_status);
        studiesFormConcluded = findViewById(R.id.studies_form_status_concluded);
        studiesFormCategorySpinner = findViewById(R.id.studies_form_category);
        studiesFormButtonSubmit = findViewById(R.id.studies_form_button_submit);
    }

}