package com.example.lifemanager.activities;

import static com.example.lifemanager.tools.Util.setActionBarColor;
import static com.example.lifemanager.tools.Util.setActionBarTitle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifemanager.R;

public abstract class AddResourceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    protected String spinnerValue;

    protected EditText studiesFormName;
    protected EditText studiesFormLinkCourse;
    protected EditText studiesFormPosition;
    protected TextView studiesFormStatusLabel;
    protected RadioGroup studiesFormStatus;
    protected RadioButton studiesFormConcluded;
    protected RadioButton studiesFormPending;
    protected TextView studiesFormCategorySpinnerLabel;
    protected Spinner studiesFormCategorySpinner;
    protected Button studiesFormButtonSubmit;

    protected EditText financeFormName;
    protected TextView financeFormDate;
    protected EditText financeFormValue;
    protected TextView financeFormTypeLabel;
    protected RadioGroup financeFormType;
    protected RadioButton financeFormTypeIncome;
    protected RadioButton financeFormTypeExpense;
    protected TextView financeFormSectorSpinnerLabel;
    protected Spinner financeFormSectorSpinner;
    protected Button financeFormButtonSubmit;

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
    protected Button taskFormButtonSubmit;

    protected String titleAppbar = null;
    protected Integer colorAppbar = null;
    protected String resourceType = null;

    protected Long idToUpdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle(this, titleAppbar);
        setActionBarColor(this,colorAppbar);
        setContentView(R.layout.activity_add_resource);

        getLayoutViews();

        configureSpinner(R.array.categories_study, studiesFormCategorySpinner);
        configureSpinner(R.array.sector_finance, financeFormSectorSpinner);

        makeFormVisible();
        configureFormButton();

        Intent intent = getIntent();
        Object object = intent.getSerializableExtra(resourceType);
        if (object != null){
            fillForm(object);
        }
    }

    protected abstract void configureFormButton();

    protected abstract void makeFormVisible();

    protected abstract void fillForm(Object object);

    private void configureSpinner(int arrayResourceId, Spinner spinner) {
        ArrayAdapter<CharSequence> adapterCategoryStudy = ArrayAdapter.createFromResource(getApplicationContext(),
                arrayResourceId, android.R.layout.simple_spinner_item);
        adapterCategoryStudy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategoryStudy);
        spinner.setOnItemSelectedListener(this);
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
        studiesFormButtonSubmit = findViewById(R.id.studies_form_button_submit);

        financeFormName = findViewById(R.id.finance_form_name);
        financeFormDate = findViewById(R.id.finance_form_date);
        financeFormValue = findViewById(R.id.finance_form_value);
        financeFormTypeLabel = findViewById(R.id.finance_form_type_label);
        financeFormType = findViewById(R.id.finance_form_type);
        financeFormTypeIncome = findViewById(R.id.finance_form_income);
        financeFormTypeExpense = findViewById(R.id.finance_form_expense);
        financeFormSectorSpinnerLabel = findViewById(R.id.finance_form_sector_label);
        financeFormSectorSpinner = findViewById(R.id.finance_form_sector);
        financeFormButtonSubmit = findViewById(R.id.finance_form_button_submit);

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
        taskFormButtonSubmit = findViewById(R.id.task_form_button_submit);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerValue = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}