package com.example.lifemanager.activities.finances;

import static com.example.lifemanager.activities.MainMenuActivity.CURRENCY_FORMAT;
import static com.example.lifemanager.model.Constants.formatter;

import android.os.Bundle;
import android.widget.TextView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.DetailedResourceActivity;
import com.example.lifemanager.model.Finance;

public class DetailedFinanceActivity extends DetailedResourceActivity {

    private TextView financeDetailName;
    private TextView financeDetailDate;
    private TextView financeDetailMonth;
    private TextView financeDetailYear;
    private TextView financeDetailValue;
    private TextView financeDetailSector;
    private TextView financeDetailTypeFinance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titleAppBar = getResources().getString(R.string.title_appbar_details_finance);
        colorAppBar = getResources().getColor(R.color.color_finances_item);
        resourceType = getResources().getStringArray(R.array.categories)[0];
        layoutId = R.layout.activity_detailed_finance;
        super.onCreate(savedInstanceState);
    }

    protected void getLayoutViews() {
        financeDetailName = findViewById(R.id.finance_detail_name);
        financeDetailDate = findViewById(R.id.finance_detail_date);
        financeDetailMonth = findViewById(R.id.finance_detail_month);
        financeDetailYear = findViewById(R.id.finance_detail_year);
        financeDetailValue = findViewById(R.id.finance_detail_value);
        financeDetailSector = findViewById(R.id.finance_detail_sector);
        financeDetailTypeFinance = findViewById(R.id.finance_detail_type_finance);
    }

    protected void bind(Object object){
        Finance finance = (Finance) object;
        String[] months = getResources().getStringArray(R.array.months);
        financeDetailName.setText("Name: "+finance.getName());
        financeDetailDate.setText("Date: "+formatter.format(finance.getDate().getTime()));
        financeDetailMonth.setText("Month: "+months[finance.getMonth()]);
        financeDetailYear.setText("Year: "+finance.getYear().toString());
        financeDetailValue.setText("Value: "+ CURRENCY_FORMAT.format(finance.getValue()));
        financeDetailSector.setText("Sector: "+finance.getSector().getValue());
        financeDetailTypeFinance.setText("Type: "+finance.getTypeFinance().getValue());
    }

}