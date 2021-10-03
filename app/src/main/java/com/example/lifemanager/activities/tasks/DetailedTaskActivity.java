package com.example.lifemanager.activities.tasks;

import static com.example.lifemanager.model.Constants.formatter;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.DetailedResourceActivity;
import com.example.lifemanager.model.Task;

public class DetailedTaskActivity extends DetailedResourceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppBar = getResources().getString(R.string.title_appbar_details_task);
        colorAppBar = getResources().getColor(R.color.color_tasks_item);
        resourceType = "task";
        super.onCreate(savedInstanceState);
    }

    protected void makeViewsVisible() {
        taskDetailSubject.setVisibility(View.VISIBLE);
        taskDetailName.setVisibility(View.VISIBLE);
        taskDetailDescription.setVisibility(View.VISIBLE);
        taskDetailStatus.setVisibility(View.VISIBLE);
        taskDetailPriority.setVisibility(View.VISIBLE);
        taskDetailDeadline.setVisibility(View.VISIBLE);
        taskDetailDueDate.setVisibility(View.VISIBLE);
    }

    protected void bind(Object object){
        Task task = (Task) object;
        taskDetailSubject.setText("Subject: "+task.getSubject());
        taskDetailName.setText("Name: "+task.getName());
        taskDetailDescription.setText("Description: "+task.getDescription());
        String statusString = "Pending";
        if (task.isStatus()){
            statusString = "Concluded";
        }
        taskDetailStatus.setText("Status: "+statusString);
        taskDetailPriority.setText("Priority: "+task.getPriority().getValue());
        taskDetailDeadline.setText("Deadline: "+formatter.format(task.getDeadline().getTime()));
        taskDetailDueDate.setText("Due date: "+formatter.format(task.getDueDate().getTime()));
    }

}