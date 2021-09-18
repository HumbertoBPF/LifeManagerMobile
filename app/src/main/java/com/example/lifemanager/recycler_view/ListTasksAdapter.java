package com.example.lifemanager.recycler_view;

import static com.example.lifemanager.model.Constants.formatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.model.Task;

import java.util.List;

public class ListTasksAdapter extends RecyclerView.Adapter<ListTasksAdapter.TaskViewHolder> {

    private Context context;
    private List<Task> tasks;

    public ListTasksAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ListTasksAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task_list, parent,false);
        return new ListTasksAdapter.TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTasksAdapter.TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView subject;
        private TextView name;
        private TextView dueDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.task_item_subject);
            name = itemView.findViewById(R.id.task_item_name);
            dueDate = itemView.findViewById(R.id.task_item_due_date);
        }

        public void bind(Task task){
            subject.setText(task.getSubject());
            name.setText(task.getName());
            dueDate.setText(formatter.format(task.getDueDate().getTime()));
        }

    }

}
