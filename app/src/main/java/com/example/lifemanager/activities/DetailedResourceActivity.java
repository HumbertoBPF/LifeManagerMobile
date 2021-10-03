package com.example.lifemanager.activities;

import static com.example.lifemanager.tools.Util.setActionBarColor;
import static com.example.lifemanager.tools.Util.setActionBarTitle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifemanager.R;

public abstract class DetailedResourceActivity extends AppCompatActivity {

    protected String titleAppBar = null;
    protected Integer colorAppBar = null;
    protected String resourceType;

    protected TextView financeDetailName;
    protected TextView financeDetailDate;
    protected TextView financeDetailMonth;
    protected TextView financeDetailYear;
    protected TextView financeDetailValue;
    protected TextView financeDetailSector;
    protected TextView financeDetailTypeFinance;

    protected TextView studyDetailName;
    protected TextView studyDetailLinkCourse;
    protected TextView studyDetailCategory;
    protected TextView studyDetailPosition;
    protected TextView studyDetailStatus;

    protected TextView taskDetailSubject;
    protected TextView taskDetailName;
    protected TextView taskDetailDescription;
    protected TextView taskDetailStatus;
    protected TextView taskDetailPriority;
    protected TextView taskDetailDeadline;
    protected TextView taskDetailDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, titleAppBar);
        setActionBarColor(this, colorAppBar);
        setContentView(R.layout.activity_detailed_resource);
        getLayoutViews();
        makeViewsVisible();
        Intent intent = getIntent();
        Object object = intent.getSerializableExtra(resourceType);
        if (object != null){
            bind(object);
        }
    }

    private void getLayoutViews() {
        financeDetailName = findViewById(R.id.finance_detail_name);
        financeDetailDate = findViewById(R.id.finance_detail_date);
        financeDetailMonth = findViewById(R.id.finance_detail_month);
        financeDetailYear = findViewById(R.id.finance_detail_year);
        financeDetailValue = findViewById(R.id.finance_detail_value);
        financeDetailSector = findViewById(R.id.finance_detail_sector);
        financeDetailTypeFinance = findViewById(R.id.finance_detail_type_finance);

        studyDetailName = findViewById(R.id.study_detail_name);
        studyDetailLinkCourse = findViewById(R.id.study_detail_link_course);
        studyDetailCategory = findViewById(R.id.study_detail_category);
        studyDetailPosition = findViewById(R.id.study_detail_position);
        studyDetailStatus = findViewById(R.id.study_detail_status);

        taskDetailSubject = findViewById(R.id.task_detail_subject);
        taskDetailName = findViewById(R.id.task_detail_name);
        taskDetailDescription = findViewById(R.id.task_detail_description);
        taskDetailStatus = findViewById(R.id.task_detail_status);
        taskDetailPriority = findViewById(R.id.task_detail_priority);
        taskDetailDeadline = findViewById(R.id.task_detail_deadline);
        taskDetailDueDate = findViewById(R.id.task_detail_due_date);
    }

    abstract protected void makeViewsVisible();

    abstract protected void bind(Object object);

}