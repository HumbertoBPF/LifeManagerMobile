package com.example.lifemanager.activities.tasks;

import static com.example.lifemanager.model.Constants.formatter;
import static com.example.lifemanager.util.Tools.configureDatePicker;
import static com.example.lifemanager.util.Tools.formatFromDateStringToCalendar;
import static com.example.lifemanager.util.Tools.getDateFromPicker;
import static com.example.lifemanager.util.Tools.showToast;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.AddResourceActivity;
import com.example.lifemanager.enums.Priority;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;
import com.example.lifemanager.util.Tools;

import java.util.Calendar;

public class AddTaskActivity extends AddResourceActivity<Task> {

    private EditText taskFormSubject;
    private EditText taskFormName;
    private EditText taskFormDescription;
    private RadioGroup taskFormStatus;
    private RadioButton taskFormConcluded;
    private RadioGroup taskFormPriority;
    private RadioButton taskFormMedium;
    private RadioButton taskFormLow;
    private TextView taskFormDeadline;
    private TextView taskFormDueDate;
    private Button taskFormButtonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        categoryDAO = LifeManagerDatabase.getInstance(this).getRoomTaskDAO();
        titleAppbar = getString(R.string.title_appbar_task_form);
        colorAppbar = getResources().getColor(R.color.color_tasks_item);
        resourceType = getResources().getStringArray(R.array.categories)[2];
        layoutId = R.layout.activity_add_task;
        super.onCreate(savedInstanceState);

        configureDatePicker(getSupportFragmentManager(), taskFormDeadline, getResources().getString(R.string.form_deadline_label),
                "taskDatePickerDeadline");
        configureDatePicker(getSupportFragmentManager(), taskFormDueDate, getResources().getString(R.string.form_due_date_label),
                "taskDatePickerDueDate");
    }

    protected void configureFormButton() {
        taskFormButtonSubmit.setOnClickListener(view -> {
            String subject = taskFormSubject.getText().toString();
            String name = taskFormName.getText().toString();
            String description = taskFormDescription.getText().toString();
            boolean status = taskFormConcluded.isChecked();
            Priority priority = getPriority();
            String deadlineString = getDateFromPicker(taskFormDeadline, getResources().getString(R.string.form_deadline_label));
            String dueDateString = getDateFromPicker(taskFormDueDate, getResources().getString(R.string.form_due_date_label));
            if (subject.equals("")){
                showToast(getApplicationContext(),"The field subject is required");
            }else if (name.equals("")){
                showToast(getApplicationContext(),"The field name is required");
            }else {
                try {
                    Calendar deadline = formatFromDateStringToCalendar(deadlineString);
                    Calendar dueDate = formatFromDateStringToCalendar(dueDateString);
                    loadingDialog.show();
                    Task task;
                    if (idToUpdate == null){
                        task = new Task(subject,name,description,status,priority,deadline,dueDate);
                    }else{
                        task = new Task(idToUpdate,subject,name,description,status,priority,deadline,dueDate);
                    }
                    categoryDAO.getSaveAsyncTask(task, () -> {
                        loadingDialog.dismiss();
                        if (idToUpdate == null){
                            Tools.showToastIfEnabled(getApplicationContext(),getResources().getString(R.string.add_task_toast_message));
                        }else{
                            Tools.showToastIfEnabled(getApplicationContext(),getResources().getString(R.string.update_task_toast_message));
                        }
                        finish();
                    }).execute();
                }catch (Exception e){
                    showToast(getApplicationContext(),"The date fields are required");
                }
            }
        });
    }

    @NonNull
    private Priority getPriority() {
        if (taskFormMedium.isChecked()){
            return Priority.MEDIUM;
        }else if (taskFormLow.isChecked()){
            return Priority.LOW;
        }
        return Priority.HIGH;
    }

    protected void fillForm(Task entity) {
        idToUpdate = entity.getId();
        taskFormSubject.setText(entity.getSubject());
        taskFormName.setText(entity.getName());
        taskFormDescription.setText(entity.getDescription());
        taskFormStatus.check(R.id.task_form_status_pending);
        if (entity.isStatus()){
            taskFormStatus.check(R.id.task_form_status_concluded);
        }
        taskFormPriority.check(R.id.task_form_status_high);
        if (entity.getPriority().equals(Priority.MEDIUM)){
            taskFormPriority.check(R.id.task_form_status_medium);
        }else if (entity.getPriority().equals(Priority.LOW)){
            taskFormPriority.check(R.id.task_form_status_low);
        }
        taskFormDeadline.setText(getResources().getString(R.string.form_deadline_label)+" "+formatter.format(entity.getDeadline().getTime()));
        taskFormDueDate.setText(getResources().getString(R.string.form_due_date_label)+" "+formatter.format(entity.getDueDate().getTime()));
    }

    protected void getLayoutViews() {
        taskFormSubject = findViewById(R.id.task_form_subject);
        taskFormName = findViewById(R.id.task_form_name);
        taskFormDescription = findViewById(R.id.task_form_description);
        taskFormStatus = findViewById(R.id.task_form_status);
        taskFormConcluded = findViewById(R.id.task_form_status_concluded);
        taskFormPriority = findViewById(R.id.task_form_priority);
        taskFormMedium = findViewById(R.id.task_form_status_medium);
        taskFormLow = findViewById(R.id.task_form_status_low);
        taskFormDeadline = findViewById(R.id.task_form_deadline);
        taskFormDueDate = findViewById(R.id.task_form_due_date);
        taskFormButtonSubmit = findViewById(R.id.task_form_button_submit);
    }
}