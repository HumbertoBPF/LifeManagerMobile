package com.example.lifemanager.activities.finances;

import android.content.Intent;
import android.os.Bundle;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.adapters.FinancesAdapter;
import com.example.lifemanager.dao.FinanceDAO;
import com.example.lifemanager.interfaces.OnItemClickListener;
import com.example.lifemanager.interfaces.OnResultListener;
import com.example.lifemanager.model.Finance;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.List;

public class FinancesActivity extends CategoryActivity<Finance> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getStringArray(R.array.categories)[0];
        colorAppbar = getResources().getColor(R.color.color_finances_item);
        titleIconAppbar = getString(R.string.title_appbar_finance_form);
        resourceType = getResources().getStringArray(R.array.categories)[0];
        formAddClass = AddFinanceActivity.class;
        super.onCreate(savedInstanceState);
        categoryDAO = LifeManagerDatabase.getInstance(this).getRoomFinanceDAO();
    }

    protected void configureAdapter() {
        loadingDialog.show();
        ((FinanceDAO) categoryDAO).getAllFinancesAsyncTask(new OnResultListener<List<Finance>>() {
            @Override
            public void onResult(List<Finance> result) {
                adapter = new FinancesAdapter(FinancesActivity.this, result, new OnItemClickListener<Finance>() {
                    @Override
                    public void onItemClick(Finance finance) {
                        Intent intent = new Intent(getApplicationContext(), DetailedFinanceActivity.class);
                        intent.putExtra(resourceType,finance);
                        startActivity(intent);
                    }
                });
                recyclerViewResources.setAdapter(adapter);
                registerForContextMenu(recyclerViewResources);
                loadingDialog.dismiss();
            }
        }).execute();
    }

}