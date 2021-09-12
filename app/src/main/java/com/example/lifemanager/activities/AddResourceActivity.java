package com.example.lifemanager.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifemanager.R;

public class AddResourceActivity extends AppCompatActivity {

    protected EditText studiesFormName;
    protected EditText studiesFormLinkCourse;
    protected EditText studiesFormPosition;
    protected TextView studiesFormStatusLabel;
    protected RadioGroup studiesFormStatus;
    protected RadioButton studiesFormConcluded;
    protected RadioButton studiesFormPending;
    protected TextView studiesFormCategorySpinnerLabel;
    protected Spinner studiesFormCategorySpinner;

    protected EditText financeFormDate;
    protected EditText financeFormValue;
    protected TextView financeFormTypeLabel;
    protected RadioGroup financeFormType;
    protected RadioButton financeFormTypeIncome;
    protected RadioButton financeFormTypeExpense;
    protected TextView financeFormSectorSpinnerLabel;
    protected Spinner financeFormSectorSpinner;

    protected EditText taskFormSubject;
    protected EditText taskFormName;
    protected EditText taskFormDescription;
    protected TextView taskFormStatusLabel;
    protected RadioGroup taskFormStatus;
    protected RadioButton taskFormConcluded;
    protected RadioButton taskFormPending;
    protected TextView taskFormPriorityLabel;
    protected RadioGroup taskFormPriority;
    protected RadioButton taskFormHigh;
    protected RadioButton taskFormMedium;
    protected RadioButton taskFormLow;
    protected EditText taskFormDeadline;
    protected EditText taskFormDueDate;

    protected String titleAppbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (titleAppbar != null){
            setTitle(titleAppbar);
        }
        setContentView(R.layout.activity_add_resource);

        getLayoutViews();

        configureSpinner(R.array.categories_study, studiesFormCategorySpinner);
        configureSpinner(R.array.sector_finance, financeFormSectorSpinner);
    }

    private void configureSpinner(int arrayResourceId, Spinner spinner) {
        ArrayAdapter<CharSequence> adapterCategoryStudy = ArrayAdapter.createFromResource(getApplicationContext(),
                arrayResourceId, android.R.layout.simple_spinner_item);
        adapterCategoryStudy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategoryStudy);
    }

    private void getLayoutViews() {
        studiesFormName = findViewById(R.id.studies_form_name);
        studiesFormLinkCourse = findViewById(R.id.studies_form_link_course);
        studiesFormPosition = findViewById(R.id.studies_form_position);
        studiesFormStatusLabel = findViewById(R.id.studies_form_status_label);
        studiesFormStatus = findViewById(R.id.studies_form_status);
        studiesFormConcluded = findViewById(R.id.studies_form_status_concluded);
        studiesFormPending = findViewById(R.id.studies_form_status_pending);
        studiesFormCategorySpinnerLabel = findViewById(R.id.studies_form_category_label);
        studiesFormCategorySpinner = findViewById(R.id.studies_form_category);

        financeFormDate = findViewById(R.id.finance_form_date);
        financeFormValue = findViewById(R.id.finance_form_value);
        financeFormTypeLabel = findViewById(R.id.finance_form_type_label);
        financeFormType = findViewById(R.id.finance_form_type);
        financeFormTypeIncome = findViewById(R.id.finance_form_income);
        financeFormTypeExpense = findViewById(R.id.finance_form_expense);
        financeFormSectorSpinnerLabel = findViewById(R.id.finance_form_sector_label);
        financeFormSectorSpinner = findViewById(R.id.finance_form_sector);

        taskFormSubject = findViewById(R.id.task_form_subject);
        taskFormName = findViewById(R.id.task_form_name);
        taskFormDescription = findViewById(R.id.task_form_description);
        taskFormStatusLabel = findViewById(R.id.task_form_status_label);
        taskFormStatus = findViewById(R.id.task_form_status);
        taskFormConcluded = findViewById(R.id.task_form_status_concluded);
        taskFormPending = findViewById(R.id.task_form_status_pending);
        taskFormPriorityLabel = findViewById(R.id.task_form_priority_label);
        taskFormPriority = findViewById(R.id.task_form_priority);
        taskFormHigh = findViewById(R.id.task_form_status_high);
        taskFormMedium = findViewById(R.id.task_form_status_medium);
        taskFormLow = findViewById(R.id.task_form_status_low);
        taskFormDeadline = findViewById(R.id.task_form_deadline);
        taskFormDueDate = findViewById(R.id.task_form_due_date);
    }

}