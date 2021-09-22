package com.example.lifemanager.recycler_view;

import static com.example.lifemanager.tools.Util.makeSelector;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.enums.TypeFinance;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource_list, parent,false);
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
            contextMenu.add(context.getResources().getString(R.string.context_menu_update_option));
            contextMenu.add(context.getResources().getString(R.string.context_menu_delete_option));
        }
    }

    public interface OnClickListener{
        void onItemClickListener(Studies study);
    }

}
