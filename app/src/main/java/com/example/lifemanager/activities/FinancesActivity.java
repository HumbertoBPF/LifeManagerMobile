package com.example.lifemanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.lifemanager.R;
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
        super.onCreate(savedInstanceState);
        roomFinanceDAO = LifeManagerDatabase.getInstance(this).getRoomFinanceDAO();
        configureAdapter();
        registerForContextMenu(recyclerViewResources);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Long chosenId = adapter.getChosenId();
        Finance finance = roomFinanceDAO.getFinanceById(chosenId);
        if (item.getTitle().equals("Remove")){
            roomFinanceDAO.delete(finance);
        }else {
            Intent intent = new Intent(this,AddFinanceActivity.class);
            intent.putExtra("finance",finance);
            startActivity(intent);
        }
        configureAdapter();
        return super.onContextItemSelected(item);
    }

    private void configureAdapter() {
        finances = roomFinanceDAO.getAllFinances();
        adapter = new ListFinancesAdapter(this, finances);
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