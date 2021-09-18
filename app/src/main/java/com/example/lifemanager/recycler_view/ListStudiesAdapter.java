package com.example.lifemanager.recycler_view;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.model.Studies;

import java.util.List;

public class ListStudiesAdapter extends RecyclerView.Adapter<ListStudiesAdapter.StudyViewHolder> {

    private Context context;
    private List<Studies> studies;
    private OnClickListener onClickListener;
    private Long chosenId;

    public Long getChosenId() {
        return chosenId;
    }

    public void setChosenId(Long chosenId) {
        this.chosenId = chosenId;
    }

    public ListStudiesAdapter(Context context, List<Studies> studies, OnClickListener onClickListener){
        this.context = context;
        this.studies = studies;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public StudyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_study_list, parent,false);
        return new ListStudiesAdapter.StudyViewHolder(view);
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

        private TextView studyItemPosition;
        private TextView studyItemName;
        private TextView studyItemStatus;

        public StudyViewHolder(@NonNull View itemView) {
            super(itemView);
            studyItemPosition = itemView.findViewById(R.id.study_item_position);
            studyItemName = itemView.findViewById(R.id.study_item_name);
            studyItemStatus = itemView.findViewById(R.id.study_item_status);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setChosenId(studies.get(getPosition()).getId());
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Studies study = studies.get(getPosition());
                    onClickListener.onItemClickListener(study);
                }
            });
        }

        public void bind(Studies study){
            studyItemPosition.setText(study.getPosition().toString());
            studyItemName.setText(study.getName());
            studyItemStatus.setText(getStatusString(study.getStatus()));
        }

        private String getStatusString(Boolean status){
            if (status){
                return "Concluded";
            }
            return "Pending";
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add("Update");
            contextMenu.add("Remove");
        }
    }

    public interface OnClickListener{
        void onItemClickListener(Studies study);
    }

}
