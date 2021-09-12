package com.example.lifemanager.activities;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;

public class AddFinanceActivity extends AddResourceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = getResources().getString(R.string.title_appbar_finance_form);
        super.onCreate(savedInstanceState);
        makeFinanceFormVisible();
    }

    private void makeFinanceFormVisible() {
        financeFormDate.setVisibility(View.VISIBLE);
        financeFormValue.setVisibility(View.VISIBLE);
        financeFormTypeLabel.setVisibility(View.VISIBLE);
        financeFormType.setVisibility(View.VISIBLE);
        financeFormTypeIncome.setVisibility(View.VISIBLE);
        financeFormTypeExpense.setVisibility(View.VISIBLE);
        financeFormSectorSpinnerLabel.setVisibility(View.VISIBLE);
        financeFormSectorSpinner.setVisibility(View.VISIBLE);
    }
}