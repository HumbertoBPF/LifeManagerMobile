package com.example.lifemanager.recycler_view;

import static com.example.lifemanager.model.Constants.formatter;

import android.content.Context;
import android.view.ContextMenu;
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
    private OnClickListener onClickListener;
    private Long chosenId;

    public Long getChosenId() {
        return chosenId;
    }

    public void setChosenId(Long chosenId) {
        this.chosenId = chosenId;
    }

    public ListTasksAdapter(Context context, List<Task> tasks, OnClickListener onClickListener) {
        this.context = context;
        this.tasks = tasks;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ListTasksAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource_list, parent,false);
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

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView subject;
        private TextView name;
        private TextView dueDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.text_view_1);
            name = itemView.findViewById(R.id.text_view_2);
            dueDate = itemView.findViewById(R.id.text_view_3);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setChosenId(tasks.get(getPosition()).getId());
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Task task = tasks.get(getPosition());
                    onClickListener.onItemClickListener(task);
                }
            });
        }

        public void bind(Task task){
            subject.setText(task.getSubject());
            name.setText(task.getName());
            dueDate.setText(formatter.format(task.getDueDate().getTime()));
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add("Update");
            contextMenu.add("Remove");
        }

    }

    public interface OnClickListener{
        void onItemClickListener(Task task);
    }

}
