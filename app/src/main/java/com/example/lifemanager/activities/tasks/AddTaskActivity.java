package com.example.lifemanager.activities.tasks;

import static com.example.lifemanager.model.Constants.formatter;
import static com.example.lifemanager.tools.Util.areToastsEnabled;
import static com.example.lifemanager.tools.Util.formatFromDateStringToCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.AddResourceActivity;
import com.example.lifemanager.dao.RoomTaskDAO;
import com.example.lifemanager.enums.Priority;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;
import com.example.lifemanager.tools.Util;

import java.util.Calendar;

public class AddTaskActivity extends AddResourceActivity {

    private RoomTaskDAO roomTaskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getString(R.string.title_appbar_task_form);
        colorAppbar = getResources().getColor(R.color.color_tasks_item);
        super.onCreate(savedInstanceState);
        roomTaskDAO = LifeManagerDatabase.getInstance(this).getRoomTaskDAO();
        makeTaskFormVisible();

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        if (task != null){
            fillForm(task);
        }

        configureTaskFormButton();

    }

    private void configureTaskFormButton() {
        taskFormButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = taskFormSubject.getText().toString();
                String name = taskFormName.getText().toString();
                String description = taskFormDescription.getText().toString();
                boolean status = taskFormConcluded.isChecked();
                Priority priority = Priority.HIGH;
                if (taskFormMedium.isChecked()){
                    priority = Priority.MEDIUM;
                }else if (taskFormLow.isChecked()){
                    priority = Priority.LOW;
                }
                Calendar deadline = formatFromDateStringToCalendar(taskFormDeadline.getText().toString());
                Calendar dueDate = formatFromDateStringToCalendar(taskFormDueDate.getText().toString());
                if (idToUpdate == null){
                    roomTaskDAO.save(new Task(subject,name,description,status,priority,deadline,dueDate));
                    Util.showToast(getApplicationContext(),"Task successfully added",areToastsEnabled(getApplicationContext()));
                }else{
                    roomTaskDAO.update(
                            new Task(idToUpdate,subject,name,description,status,priority,deadline,dueDate));
                    Util.showToast(getApplicationContext(),"Task successfully updated",areToastsEnabled(getApplicationContext()));
                }
                finish();
            }
        });
    }

    private void fillForm(Task task) {
        idToUpdate = task.getId();
        taskFormSubject.setText(task.getSubject());
        taskFormName.setText(task.getName());
        taskFormDescription.setText(task.getDescription());
        taskFormStatus.check(R.id.task_form_status_pending);
        if (task.isStatus()){
            taskFormStatus.check(R.id.task_form_status_concluded);
        }
        taskFormPriority.check(R.id.task_form_status_high);
        if (task.getPriority().equals(Priority.MEDIUM)){
            taskFormPriority.check(R.id.task_form_status_medium);
        }else if (task.getPriority().equals(Priority.LOW)){
            taskFormPriority.check(R.id.task_form_status_low);
        }
        taskFormDeadline.setText(formatter.format(task.getDeadline().getTime()).replace("-",""));
        taskFormDueDate.setText(formatter.format(task.getDueDate().getTime()).replace("-",""));
    }

    private void makeTaskFormVisible() {
        taskFormSubject.setVisibility(View.VISIBLE);
        taskFormName.setVisibility(View.VISIBLE);
        taskFormDescription.setVisibility(View.VISIBLE);
        taskFormStatusLabel.setVisibility(View.VISIBLE);
        taskFormStatus.setVisibility(View.VISIBLE);
        taskFormConcluded.setVisibility(View.VISIBLE);
        taskFormPending.setVisibility(View.VISIBLE);
        taskFormPriorityLabel.setVisibility(View.VISIBLE);
        taskFormPriority.setVisibility(View.VISIBLE);
        taskFormHigh.setVisibility(View.VISIBLE);
        taskFormMedium.setVisibility(View.VISIBLE);
        taskFormLow.setVisibility(View.VISIBLE);
        taskFormDeadline.setVisibility(View.VISIBLE);
        taskFormDueDate.setVisibility(View.VISIBLE);
        taskFormButtonSubmit.setVisibility(View.VISIBLE);
    }
}