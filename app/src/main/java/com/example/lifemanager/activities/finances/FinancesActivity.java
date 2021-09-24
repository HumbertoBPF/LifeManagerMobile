package com.example.lifemanager.activities.finances;

import static com.example.lifemanager.tools.Util.areToastsEnabled;
import static com.example.lifemanager.tools.Util.confirmDeletionDialog;
import static com.example.lifemanager.tools.Util.showToast;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.dao.RoomFinanceDAO;
import com.example.lifemanager.model.Finance;
import com.example.lifemanager.recycler_view.ListFinancesAdapter;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class FinancesActivity extends CategoryActivity {

    private RoomFinanceDAO roomFinanceDAO;
    private List<Finance> finances;
    private ListFinancesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[0];
        colorAppbar = getResources().getColor(R.color.color_finances_item);
        super.onCreate(savedInstanceState);
        roomFinanceDAO = LifeManagerDatabase.getInstance(this).getRoomFinanceDAO();
        configureAdapter();
        registerForContextMenu(recyclerViewResources);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        Finance finance = roomFinanceDAO.getFinanceById(chosenId);
        if (item.getTitle().equals(getResources().getString(R.string.context_menu_delete_option))){
            confirmDeletionDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    roomFinanceDAO.delete(finance);
                    showToast(getApplicationContext(),
                            getResources().getString(R.string.delete_finance_toast_message),
                            areToastsEnabled(getApplicationContext()));
                    configureAdapter();
                }
            });
        }else {
            Intent intent = new Intent(this,AddFinanceActivity.class);
            intent.putExtra("finance",finance);
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    private void configureAdapter() {
        finances = roomFinanceDAO.getAllFinances();
        adapter = new ListFinancesAdapter(this, finances, new ListFinancesAdapter.OnClickListener() {
            @Override
            public void onItemClickListener(Finance finance) {
                Intent intent = new Intent(getApplicationContext(), DetailedFinanceActivity.class);
                intent.putExtra("finance",finance);
                startActivity(intent);
            }
        });
        recyclerViewResources.setAdapter(adapter);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        titleIconAppbar = getResources().getString(R.string.title_appbar_finance_form);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        formAddClass = AddFinanceActivity.class;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        configureAdapter();
    }
}