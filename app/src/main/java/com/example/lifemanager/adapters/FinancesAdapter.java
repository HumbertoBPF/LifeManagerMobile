package com.example.lifemanager.adapters;

import static com.example.lifemanager.activities.MainMenuActivity.CURRENCY_FORMAT;
import static com.example.lifemanager.model.Constants.formatter;
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
import com.example.lifemanager.activities.finances.AddFinanceActivity;
import com.example.lifemanager.activities.finances.DetailedFinanceActivity;
import com.example.lifemanager.enums.TypeFinance;
import com.example.lifemanager.model.Finance;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class FinancesAdapter extends RecyclerView.Adapter<FinancesAdapter.FinanceViewHolder> {

    private Context context;
    private List<Finance> finances;

    public FinancesAdapter(Context context, List<Finance> finances) {
        this.context = context;
        this.finances = finances;
    }

    @NonNull
    @Override
    public FinancesAdapter.FinanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource_list, parent,false);
        return new FinancesAdapter.FinanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinancesAdapter.FinanceViewHolder holder, int position) {
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
        }

        public void bind(Finance finance){
            if (finance.getTypeFinance().equals(TypeFinance.INCOME)){
                int greenTextColor = context.getResources().getColor(R.color.green_recycler_view_item);
                financeItemName.setTextColor(greenTextColor);
                financeItemValue.setTextColor(greenTextColor);
                financeItemDate.setTextColor(greenTextColor);
            }
            financeItemName.setText(finance.getName());
            financeItemValue.setText(CURRENCY_FORMAT.format(finance.getValue())+"");
            financeItemDate.setText(formatter.format(finance.getDate().getTime()));
            rootCardView.setBackground(makeSelector(Color.parseColor("#FFFFFF"),0.95f));

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailedFinanceActivity.class);
                intent.putExtra("Finances",finance);
                context.startActivity(intent);
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem update = contextMenu.add(context.getResources().getString(R.string.context_menu_update_option));
            update.setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(context, AddFinanceActivity.class);
                intent.putExtra("Finances",finances.get(FinanceViewHolder.this.getBindingAdapterPosition()));
                context.startActivity(intent);
                return false;
            });
            MenuItem delete = contextMenu.add(context.getResources().getString(R.string.context_menu_delete_option));
            delete.setOnMenuItemClickListener(item -> {
                AlertDialog deletionDialog = deletionDialog(context, (dialogInterface, i) -> {
                    ProgressDialog loadingDialog = loadingDialog(context);
                    loadingDialog.show();
                    LifeManagerDatabase.getInstance(context).getRoomFinanceDAO().getDeleteAsyncTask(
                            finances.get(FinanceViewHolder.this.getBindingAdapterPosition()),
                            () -> {
                                showToastIfEnabled(context, context.getString(R.string.delete_toast_message));
                                finances.remove(FinanceViewHolder.this.getBindingAdapterPosition());
                                notifyItemRemoved(FinanceViewHolder.this.getBindingAdapterPosition());
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
