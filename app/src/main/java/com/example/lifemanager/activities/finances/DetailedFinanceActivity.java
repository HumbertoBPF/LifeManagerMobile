package com.example.lifemanager.activities.finances;

import static com.example.lifemanager.model.Constants.formatter;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.DetailedResourceActivity;
import com.example.lifemanager.model.Finance;

public class DetailedFinanceActivity extends DetailedResourceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppBar = getResources().getString(R.string.title_appbar_details_finance);
        colorAppBar = getResources().getColor(R.color.color_finances_item);
        resourceType = getResources().getStringArray(R.array.categories)[0];
        super.onCreate(savedInstanceState);
    }

    protected void makeViewsVisible() {
        financeDetailName.setVisibility(View.VISIBLE);
        financeDetailDate.setVisibility(View.VISIBLE);
        financeDetailMonth.setVisibility(View.VISIBLE);
        financeDetailYear.setVisibility(View.VISIBLE);
        financeDetailValue.setVisibility(View.VISIBLE);
        financeDetailSector.setVisibility(View.VISIBLE);
        financeDetailTypeFinance.setVisibility(View.VISIBLE);
    }

    protected void bind(Object object){
        Finance finance = (Finance) object;
        String[] months = getResources().getStringArray(R.array.months);
        financeDetailName.setText("Name: "+finance.getName());
        financeDetailDate.setText("Date: "+formatter.format(finance.getDate().getTime()));
        financeDetailMonth.setText("Month: "+months[finance.getMonth()]);
        financeDetailYear.setText("Year: "+finance.getYear().toString());
        financeDetailValue.setText("Value: "+finance.getValue().toString());
        financeDetailSector.setText("Sector: "+finance.getSector().getValue());
        financeDetailTypeFinance.setText("Type: "+finance.getTypeFinance().getValue());
    }

}