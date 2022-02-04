package com.example.lifemanager.activities.tasks;

import static com.example.lifemanager.model.Constants.formatter;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.DetailedResourceActivity;
import com.example.lifemanager.model.Task;

public class DetailedTaskActivity extends DetailedResourceActivity {

    private TextView taskDetailSubject;
    private TextView taskDetailName;
    private TextView taskDetailDescription;
    private TextView taskDetailStatus;
    private TextView taskDetailPriority;
    private TextView taskDetailDeadline;
    private TextView taskDetailDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppBar = getString(R.string.title_appbar_details_task);
        colorAppBar = getResources().getColor(R.color.color_tasks_item);
        resourceType = getResources().getStringArray(R.array.categories)[2];
        layoutId = R.layout.activity_detailed_task;
        super.onCreate(savedInstanceState);
    }

    protected void getLayoutViews() {
        taskDetailSubject = findViewById(R.id.task_detail_subject);
        taskDetailName = findViewById(R.id.task_detail_name);
        taskDetailDescription = findViewById(R.id.task_detail_description);
        taskDetailStatus = findViewById(R.id.task_detail_status);
        taskDetailPriority = findViewById(R.id.task_detail_priority);
        taskDetailDeadline = findViewById(R.id.task_detail_deadline);
        taskDetailDueDate = findViewById(R.id.task_detail_due_date);
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