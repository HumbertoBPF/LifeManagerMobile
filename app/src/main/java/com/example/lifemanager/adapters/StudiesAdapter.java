package com.example.lifemanager.adapters;

import static com.example.lifemanager.util.Tools.deletionDialog;
import static com.example.lifemanager.util.Tools.loadingDialog;
import static com.example.lifemanager.util.Tools.makeSelector;
import static com.example.lifemanager.util.Tools.showToastIfEnabled;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.lifemanager.activities.studies.DetailedStudyActivity;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class StudiesAdapter extends RecyclerView.Adapter<StudiesAdapter.StudyViewHolder> {

    private Context context;
    private List<Studies> studies;

    public StudiesAdapter(Context context, List<Studies> studies){
        this.context = context;
        this.studies = studies;
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
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailedStudyActivity.class);
                intent.putExtra("Studies",study);
                context.startActivity(intent);
            });
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
            update.setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(context, AddStudyActivity.class);
                intent.putExtra("Studies",studies.get(StudyViewHolder.this.getBindingAdapterPosition()));
                context.startActivity(intent);
                return false;
            });
            MenuItem delete = contextMenu.add(context.getResources().getString(R.string.context_menu_delete_option));
            delete.setOnMenuItemClickListener(item -> {
                AlertDialog deletionDialog = deletionDialog(context, (dialogInterface, i) -> {
                    ProgressDialog loadingDialog = loadingDialog(context);
                    loadingDialog.show();
                    LifeManagerDatabase.getInstance(context).getRoomStudiesDAO().getDeleteAsyncTask(
                            studies.get(StudyViewHolder.this.getBindingAdapterPosition()),
                            () -> {
                                showToastIfEnabled(context, context.getString(R.string.delete_toast_message));
                                studies.remove(StudyViewHolder.this.getBindingAdapterPosition());
                                notifyItemRemoved(StudyViewHolder.this.getBindingAdapterPosition());
                                loadingDialog.dismiss();
                            }
                    ).execute();
                });
                deletionDialog.show();
                return false;
            });
        }
    }

}
