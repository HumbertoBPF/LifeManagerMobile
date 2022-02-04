package com.example.lifemanager.adapters;

import static com.example.lifemanager.model.Constants.formatter;
import static com.example.lifemanager.util.Tools.deletionDialog;
import static com.example.lifemanager.util.Tools.loadingDialog;
import static com.example.lifemanager.util.Tools.makeSelector;
import static com.example.lifemanager.util.Tools.showToastIfEnabled;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.tasks.AddTaskActivity;
import com.example.lifemanager.interfaces.OnItemClickListener;
import com.example.lifemanager.interfaces.OnTaskListener;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private Context context;
    private List<Task> tasks;
    private OnItemClickListener<Task> onItemClickListener;

    public TasksAdapter(Context context, List<Task> tasks, OnItemClickListener<Task> onItemClickListener) {
        this.context = context;
        this.tasks = tasks;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TasksAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource_list, parent,false);
        return new TasksAdapter.TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private CardView rootCardView;
        private TextView taskItemSubject;
        private TextView taskItemName;
        private TextView taskItemDueDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            rootCardView = itemView.findViewById(R.id.root_card_view);
            taskItemSubject = itemView.findViewById(R.id.text_view_1);
            taskItemName = itemView.findViewById(R.id.text_view_2);
            taskItemDueDate = itemView.findViewById(R.id.text_view_3);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Task task = tasks.get(getPosition());
                    onItemClickListener.onItemClick(task);
                }
            });
        }

        public void bind(Task task){
            if (task.isStatus()){
                int greenTextColor = context.getResources().getColor(R.color.green_recycler_view_item);
                taskItemSubject.setTextColor(greenTextColor);
                taskItemName.setTextColor(greenTextColor);
                taskItemDueDate.setTextColor(greenTextColor);
            }
            taskItemSubject.setText(task.getSubject());
            taskItemName.setText(task.getName());
            taskItemDueDate.setText(formatter.format(task.getDueDate().getTime()));
            rootCardView.setBackground(makeSelector(Color.parseColor("#FFFFFF"),0.95f));
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem update = contextMenu.add(context.getResources().getString(R.string.context_menu_update_option));
            update.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent = new Intent(context, AddTaskActivity.class);
                    intent.putExtra("Tasks",tasks.get(TaskViewHolder.this.getBindingAdapterPosition()));
                    context.startActivity(intent);
                    return false;
                }
            });
            MenuItem delete = contextMenu.add(context.getResources().getString(R.string.context_menu_delete_option));
            delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    AlertDialog deletionDialog = deletionDialog(context, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ProgressDialog loadingDialog = loadingDialog(context);
                            loadingDialog.show();
                            LifeManagerDatabase.getInstance(context).getRoomTaskDAO().getDeleteAsyncTask(
                                    tasks.get(TaskViewHolder.this.getBindingAdapterPosition()),
                                    new OnTaskListener() {
                                        @Override
                                        public void onTask() {
                                            showToastIfEnabled(context, context.getString(R.string.delete_toast_message));
                                            tasks.remove(TaskViewHolder.this.getBindingAdapterPosition());
                                            notifyItemRemoved(TaskViewHolder.this.getBindingAdapterPosition());
                                            loadingDialog.dismiss();
                                        }
                                    }
                            ).execute();
                        }
                    });
                    deletionDialog.show();
                    return false;
                }
            });
        }

    }
}
