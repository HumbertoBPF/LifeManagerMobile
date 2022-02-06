package com.example.lifemanager.activities.finances;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.CategoryActivity;
import com.example.lifemanager.adapters.FinancesAdapter;
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

    @Override
    protected RecyclerView.Adapter initializeAdapter(List<Finance> list) {
        return new FinancesAdapter(FinancesActivity.this, list, finance -> {
            Intent intent = new Intent(getApplicationContext(), DetailedFinanceActivity.class);
            intent.putExtra(resourceType,finance);
            startActivity(intent);
        });
    }

}