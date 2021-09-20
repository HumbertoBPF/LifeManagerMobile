package com.example.lifemanager.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;

import java.util.List;

public class ListResourcesMenuAdapter extends RecyclerView.Adapter<ListResourcesMenuAdapter.ResourceViewHolder> {

    private List<String> resourcesNames;
    private Context context;
    private OnClickListener onClickListener;

    public ListResourcesMenuAdapter(Context context, List<String> resourcesNames, OnClickListener onClickListener) {
        this.context = context;
        this.resourcesNames = resourcesNames;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ListResourcesMenuAdapter.ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resources_menu_list,parent,false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListResourcesMenuAdapter.ResourceViewHolder holder, int position) {
        String resourceNameString = resourcesNames.get(position);
        holder.bind(resourceNameString);
    }

    @Override
    public int getItemCount() {
        return resourcesNames.size();
    }

    class ResourceViewHolder extends RecyclerView.ViewHolder{

        private TextView resourceName;
        private CardView backgroundCardView;

        public ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            resourceName = itemView.findViewById(R.id.resource_name);
            backgroundCardView = itemView.findViewById(R.id.background_card_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onItemClickListener(resourceName.getText().toString());
                }
            });
        }

        public void bind(String resourceNameString) {
            resourceName.setText(resourceNameString);
            int idColorResource = context.getResources().getIdentifier("color_"+
                    resourceNameString.toLowerCase()+"_item","color", context.getPackageName());
            int color = context.getResources().getColor(idColorResource);
            backgroundCardView.setCardBackgroundColor(color);
        }

    }

    public interface OnClickListener{
        void onItemClickListener(String category);
    }

}
