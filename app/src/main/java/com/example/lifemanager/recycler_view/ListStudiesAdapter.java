package com.example.lifemanager.recycler_view;

import android.content.Context;
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

    Context context;
    List<Studies> studies;

    public ListStudiesAdapter(Context context, List<Studies> studies){
        this.context = context;
        this.studies = studies;
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

    class StudyViewHolder extends RecyclerView.ViewHolder{

        private TextView studyItemPosition;
        private TextView studyItemName;
        private TextView studyItemStatus;

        public StudyViewHolder(@NonNull View itemView) {
            super(itemView);
            studyItemPosition = itemView.findViewById(R.id.study_item_position);
            studyItemName = itemView.findViewById(R.id.study_item_name);
            studyItemStatus = itemView.findViewById(R.id.study_item_status);
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

    }

}
