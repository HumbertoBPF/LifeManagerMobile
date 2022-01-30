package com.example.lifemanager.recycler_view;

import static com.example.lifemanager.tools.Util.makeSelector;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.model.RoundedButton;

import java.util.List;

public class ListResourcesMenuAdapter extends RecyclerView.Adapter<ListResourcesMenuAdapter.ResourceViewHolder> {

    private List<RoundedButton> roundedButtons;
    private Context context;

    public ListResourcesMenuAdapter(Context context, List<RoundedButton> roundedButtons) {
        this.context = context;
        this.roundedButtons = roundedButtons;
    }

    @NonNull
    @Override
    public ListResourcesMenuAdapter.ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resources_menu_list,parent,false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListResourcesMenuAdapter.ResourceViewHolder holder, int position) {
        RoundedButton roundedButton = roundedButtons.get(position);
        holder.bind(roundedButton);
    }

    @Override
    public int getItemCount() {
        return roundedButtons.size();
    }

    class ResourceViewHolder extends RecyclerView.ViewHolder{

        private TextView resourceName;
        private CardView backgroundCardView;

        public ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            resourceName = itemView.findViewById(R.id.resource_name);
            backgroundCardView = itemView.findViewById(R.id.background_card_view);
        }

        public void bind(RoundedButton roundedButton) {
            resourceName.setText(roundedButton.getName());
            int color = context.getResources().getColor(roundedButton.getColorId());
            backgroundCardView.setBackground(makeSelector(color,0.8f));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,roundedButton.getNextActivity()));
                }
            });
        }

    }

}
