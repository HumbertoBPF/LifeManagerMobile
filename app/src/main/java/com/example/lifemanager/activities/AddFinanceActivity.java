package com.example.lifemanager.activities;

import android.os.Bundle;
import android.view.View;

public class AddFinanceActivity extends AddResourceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppbar = "Add finance";
        super.onCreate(savedInstanceState);
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