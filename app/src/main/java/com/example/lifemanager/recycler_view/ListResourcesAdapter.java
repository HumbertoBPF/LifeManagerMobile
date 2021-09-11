package com.example.lifemanager.recycler_view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;

import java.util.List;

public class ListResourcesAdapter extends RecyclerView.Adapter<ListResourcesAdapter.ResourceViewHolder> {

    private List<String> resourcesNames;
    private Context context;

    public ListResourcesAdapter(Context context, List<String> resourcesNames) {
        this.context = context;
        this.resourcesNames = resourcesNames;
    }

    @NonNull
    @Override
    public ListResourcesAdapter.ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource_list,parent,false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListResourcesAdapter.ResourceViewHolder holder, int position) {
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
        }

        public void bind(String resourceNameString) {
            resourceName.setText(resourceNameString);
            int idColorResource = context.getResources().getIdentifier("color_"+
                    resourceNameString.toLowerCase()+"_item","string", context.getPackageName());
            String rgbColorString = context.getResources().getString(idColorResource);
            backgroundCardView.setCardBackgroundColor(Color.parseColor(rgbColorString));
        }

    }

}
