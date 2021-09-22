package com.example.lifemanager.recycler_view;

import static com.example.lifemanager.model.Constants.formatter;
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
import com.example.lifemanager.model.Finance;

import java.util.List;

public class ListFinancesAdapter extends RecyclerView.Adapter<ListFinancesAdapter.FinanceViewHolder> {

    private Context context;
    private List<Finance> finances;
    private OnClickListener onClickListener;
    private Long chosenId;

    public Long getChosenId() {
        return chosenId;
    }

    public void setChosenId(Long chosenId) {
        this.chosenId = chosenId;
    }

    public ListFinancesAdapter(Context context, List<Finance> finances, OnClickListener onClickListener) {
        this.context = context;
        this.finances = finances;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ListFinancesAdapter.FinanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource_list, parent,false);
        return new ListFinancesAdapter.FinanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFinancesAdapter.FinanceViewHolder holder, int position) {
        Finance finance = finances.get(position);
        holder.bind(finance);
    }

    @Override
    public int getItemCount() { return finances.size(); }

    class FinanceViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private CardView rootCardView;
        private TextView financeItemName;
        private TextView financeItemValue;
        private TextView financeItemDate;

        public FinanceViewHolder(@NonNull View itemView) {
            super(itemView);
            rootCardView = itemView.findViewById(R.id.root_card_view);
            financeItemName = itemView.findViewById(R.id.text_view_1);
            financeItemValue = itemView.findViewById(R.id.text_view_2);
            financeItemDate = itemView.findViewById(R.id.text_view_3);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setChosenId(finances.get(getPosition()).getId());
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Finance finance = finances.get(getPosition());
                    onClickListener.onItemClickListener(finance);
                }
            });
        }

        public void bind(Finance finance){
            if (finance.getTypeFinance().equals(TypeFinance.INCOME)){
                int greenTextColor = context.getResources().getColor(R.color.green_recycler_view_item);
                financeItemName.setTextColor(greenTextColor);
                financeItemValue.setTextColor(greenTextColor);
                financeItemDate.setTextColor(greenTextColor);
            }
            financeItemName.setText(finance.getName());
            financeItemValue.setText(finance.getValue()+"");
            financeItemDate.setText(formatter.format(finance.getDate().getTime()));
            rootCardView.setBackground(makeSelector(Color.parseColor("#FFFFFF"),0.95f));
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(context.getResources().getString(R.string.context_menu_update_option));
            contextMenu.add(context.getResources().getString(R.string.context_menu_delete_option));
        }

    }

    public interface OnClickListener{
        void onItemClickListener(Finance finance);
    }

}
