package com.example.lifemanager.activities.tasks;

import static com.example.lifemanager.tools.Util.formatFromDateStringToCalendar;

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
                roomTaskDAO.save(new Task(subject,name,description,status,priority,deadline,dueDate));
                finish();
            }
        });

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