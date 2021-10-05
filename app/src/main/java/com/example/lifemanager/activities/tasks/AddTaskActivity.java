package com.example.lifemanager.activities.tasks;

import static com.example.lifemanager.model.Constants.formatter;
import static com.example.lifemanager.tools.Util.formatFromDateStringToCalendar;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.AddResourceActivity;
import com.example.lifemanager.async_tasks.AsyncTask;
import com.example.lifemanager.dao.RoomTaskDAO;
import com.example.lifemanager.enums.Priority;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;
import com.example.lifemanager.tools.Util;

import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AddResourceActivity {

    private RoomTaskDAO roomTaskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        roomTaskDAO = LifeManagerDatabase.getInstance(this).getRoomTaskDAO();
        titleAppbar = getResources().getString(R.string.title_appbar_task_form);
        colorAppbar = getResources().getColor(R.color.color_tasks_item);
        resourceType = getResources().getStringArray(R.array.categories)[2];
        super.onCreate(savedInstanceState);
    }

    protected void configureFormButton() {
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
                Priority finalPriority = priority;
                loadingDialog.show();
                new AsyncTask(new AsyncTask.AsyncTaskInterface() {
                    @Override
                    public List<Object> doInBackground() {
                        if (idToUpdate == null){
                            roomTaskDAO.save(new Task(subject,name,description,status, finalPriority,deadline,dueDate));
                        }else{
                            roomTaskDAO.update(
                                    new Task(idToUpdate,subject,name,description,status, finalPriority,deadline,dueDate));
                        }
                        return null;
                    }

                    @Override
                    public void onPostExecute(List<Object> objects) {
                        loadingDialog.dismiss();
                        if (idToUpdate == null){
                            Util.showToast(getApplicationContext(),getResources().getString(R.string.add_task_toast_message));
                        }else{
                            Util.showToast(getApplicationContext(),getResources().getString(R.string.update_task_toast_message));
                        }
                        finish();
                    }
                }).execute();
            }
        });
    }

    protected void fillForm(Object object) {
        Task task = (Task) object;
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

    protected void makeFormVisible() {
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