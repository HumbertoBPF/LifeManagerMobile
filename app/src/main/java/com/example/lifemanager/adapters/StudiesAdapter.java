package com.example.lifemanager.adapters;

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
import com.example.lifemanager.activities.studies.AddStudyActivity;
import com.example.lifemanager.interfaces.OnItemClickListener;
import com.example.lifemanager.interfaces.OnTaskListener;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class StudiesAdapter extends RecyclerView.Adapter<StudiesAdapter.StudyViewHolder> {

    private Context context;
    private List<Studies> studies;
    private OnItemClickListener<Studies> onItemClickListener;

    public StudiesAdapter(Context context, List<Studies> studies, OnItemClickListener<Studies> onItemClickListener){
        this.context = context;
        this.studies = studies;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public StudyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource_list, parent,false);
        return new StudiesAdapter.StudyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyViewHolder holder, int position) {
        Studies study = studies.get(position);
        holder.bind(study);
    }

    @Override
    public int getItemCount() {
        return studies.size();
    }

    class StudyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private CardView rootCardView;
        private TextView studyItemPosition;
        private TextView studyItemName;
        private TextView studyItemStatus;

        public StudyViewHolder(@NonNull View itemView) {
            super(itemView);
            rootCardView = itemView.findViewById(R.id.root_card_view);
            studyItemPosition = itemView.findViewById(R.id.text_view_1);
            studyItemName = itemView.findViewById(R.id.text_view_2);
            studyItemStatus = itemView.findViewById(R.id.text_view_3);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Studies study = studies.get(getPosition());
                    onItemClickListener.onItemClick(study);
                }
            });
        }

        public void bind(Studies study){
            if (study.getStatus()){
                int greenTextColor = context.getResources().getColor(R.color.green_recycler_view_item);
                studyItemPosition.setTextColor(greenTextColor);
                studyItemName.setTextColor(greenTextColor);
                studyItemStatus.setTextColor(greenTextColor);
            }
            studyItemPosition.setText(study.getPosition().toString());
            studyItemName.setText(study.getName());
            studyItemStatus.setText(getStatusString(study.getStatus()));
            rootCardView.setBackground(makeSelector(Color.parseColor("#FFFFFF"),0.95f));
        }

        private String getStatusString(Boolean status){
            if (status){
                return "Concluded";
            }
            return "Pending";
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem update = contextMenu.add(context.getResources().getString(R.string.context_menu_update_option));
            update.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent = new Intent(context, AddStudyActivity.class);
                    intent.putExtra("Studies",studies.get(StudyViewHolder.this.getBindingAdapterPosition()));
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
                            LifeManagerDatabase.getInstance(context).getRoomStudiesDAO().getDeleteAsyncTask(
                                    studies.get(StudyViewHolder.this.getBindingAdapterPosition()),
                                    new OnTaskListener() {
                                        @Override
                                        public void onTask() {
                                            showToastIfEnabled(context, context.getString(R.string.delete_toast_message));
                                            studies.remove(StudyViewHolder.this.getBindingAdapterPosition());
                                            notifyItemRemoved(StudyViewHolder.this.getBindingAdapterPosition());
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
