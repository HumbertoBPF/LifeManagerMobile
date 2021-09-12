package com.example.lifemanager.activities;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;

public class AddTaskActivity extends AddResourceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getString(R.string.title_appbar_task_form);
        super.onCreate(savedInstanceState);
        makeTaskFormVisible();
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
    }
}