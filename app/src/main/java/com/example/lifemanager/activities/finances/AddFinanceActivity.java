package com.example.lifemanager.activities.finances;

import static com.example.lifemanager.model.Constants.formatter;
import static com.example.lifemanager.tools.Util.formatFromDateStringToCalendar;
import static com.example.lifemanager.tools.Util.showToast;

import android.os.Bundle;
import android.view.View;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.AddResourceActivity;
import com.example.lifemanager.async_tasks.AsyncTask;
import com.example.lifemanager.dao.RoomFinanceDAO;
import com.example.lifemanager.enums.Sector;
import com.example.lifemanager.enums.TypeFinance;
import com.example.lifemanager.model.Finance;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddFinanceActivity extends AddResourceActivity {

    private RoomFinanceDAO roomFinanceDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        roomFinanceDAO = LifeManagerDatabase.getInstance(this).getRoomFinanceDAO();
        titleAppbar = getResources().getString(R.string.title_appbar_finance_form);
        colorAppbar = getResources().getColor(R.color.color_finances_item);
        resourceType = getResources().getStringArray(R.array.categories)[0];
        super.onCreate(savedInstanceState);
    }

    protected void configureFormButton() {
        financeFormButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = financeFormName.getText().toString();
                String dateString = financeFormDate.getText().toString();
                Integer year = Integer.parseInt(dateString.substring(0,4));
                Integer month = Integer.parseInt(dateString.substring(4,6))-1;
                Calendar dateCalendar = formatFromDateStringToCalendar(dateString);
                BigDecimal valueBigDecimal = getBigDecimalValue();
                TypeFinance typeFinance = getTypeFinance();
                Sector sector = getSector();
                new AsyncTask(new AsyncTask.AsyncTaskInterface() {
                    @Override
                    public List<Object> doInBackground() {
                        if (idToUpdate == null){
                            roomFinanceDAO.save(new Finance(name,dateCalendar,month,year,valueBigDecimal,sector,typeFinance));
                        }else{
                            roomFinanceDAO.update(
                                    new Finance(idToUpdate,name,dateCalendar,month,year,valueBigDecimal,sector,typeFinance));
                        }
                        return null;
                    }

                    @Override
                    public void onPostExecute(List<Object> objects) {
                        if (idToUpdate == null){
                            showToast(getApplicationContext(),getResources().getString(R.string.add_finance_toast_message));
                        }else{
                            showToast(getApplicationContext(),getResources().getString(R.string.update_finance_toast_message));
                        }
                        finish();
                    }
                }).execute();
            }
        });
    }

    protected void fillForm(Object object) {
        Finance finance = (Finance) object;
        idToUpdate = finance.getId();
        financeFormName.setText(finance.getName());
        financeFormDate.setText(formatter.format(finance.getDate().getTime()).replace("-",""));
        financeFormValue.setText(finance.getValue()+"");
        financeFormType.check(R.id.finance_form_income);
        if (finance.getTypeFinance().equals(TypeFinance.EXPENSE)){
            financeFormType.check(R.id.finance_form_expense);
        }
        List<String> sectors = Arrays.asList(getResources().getStringArray(R.array.sector_finance));
        financeFormSectorSpinner.setSelection(sectors.indexOf(finance.getSector().getValue()));
    }

    private Sector getSector() {
        switch (spinnerValue) {
            case "Trips":
                return Sector.TRIPS;
            case "Transport":
                return Sector.TRANSPORT;
            case "Housing":
                return Sector.HOUSING;
            case "Other":
                return Sector.OTHER;
        }
        return Sector.FOOD;
    }

    private TypeFinance getTypeFinance() {
        if (financeFormTypeIncome.isChecked()){
            return TypeFinance.INCOME;
        }
        return TypeFinance.EXPENSE;
    }

    private BigDecimal getBigDecimalValue() {
        String valueString = financeFormValue.getText().toString();
        return BigDecimal.valueOf(Double.parseDouble(valueString))
                .setScale(2, RoundingMode.HALF_UP);
    }

    protected void makeFormVisible() {
        financeFormName.setVisibility(View.VISIBLE);
        financeFormDate.setVisibility(View.VISIBLE);
        financeFormValue.setVisibility(View.VISIBLE);
        financeFormTypeLabel.setVisibility(View.VISIBLE);
        financeFormType.setVisibility(View.VISIBLE);
        financeFormTypeIncome.setVisibility(View.VISIBLE);
        financeFormTypeExpense.setVisibility(View.VISIBLE);
        financeFormSectorSpinnerLabel.setVisibility(View.VISIBLE);
        financeFormSectorSpinner.setVisibility(View.VISIBLE);
        financeFormButtonSubmit.setVisibility(View.VISIBLE);
    }

}