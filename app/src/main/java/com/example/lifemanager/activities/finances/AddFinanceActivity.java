package com.example.lifemanager.activities.finances;

import static com.example.lifemanager.model.Constants.formatter;
import static com.example.lifemanager.util.Tools.configureDatePicker;
import static com.example.lifemanager.util.Tools.formatFromDateStringToCalendar;
import static com.example.lifemanager.util.Tools.getDateFromPicker;
import static com.example.lifemanager.util.Tools.onViewDrawn;
import static com.example.lifemanager.util.Tools.showToast;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lifemanager.R;
import com.example.lifemanager.activities.AddResourceActivity;
import com.example.lifemanager.enums.Sector;
import com.example.lifemanager.enums.TypeFinance;
import com.example.lifemanager.model.Finance;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddFinanceActivity extends AddResourceActivity<Finance> {

    private EditText financeFormName;
    private TextView financeFormDate;
    private EditText financeFormValue;
    private RadioGroup financeFormType;
    private RadioButton financeFormTypeIncome;
    private Spinner financeFormSectorSpinner;
    private Button financeFormButtonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        categoryDAO = LifeManagerDatabase.getInstance(this).getRoomFinanceDAO();
        titleAppbar = getString(R.string.title_appbar_finance_form);
        colorAppbar = getResources().getColor(R.color.color_finances_item);
        resourceType = getResources().getStringArray(R.array.categories)[0];
        layoutId = R.layout.activity_add_finance;
        super.onCreate(savedInstanceState);
        configureSpinner(R.array.sector_finance, financeFormSectorSpinner);
        configureDatePicker(getSupportFragmentManager(), financeFormDate, getResources().getString(R.string.form_date_label),
                "financeDatePicker");
    }

    protected void configureFormButton() {
        financeFormButtonSubmit.setOnClickListener(view -> {
            String name = financeFormName.getText().toString();
            TypeFinance typeFinance = getTypeFinance();
            Sector sector = getSector();
            if (name.equals("")) {
                showToast(getApplicationContext(), "All fields are required");
            } else {
                try {
                    String dateString = getDateFromPicker(financeFormDate, getResources().getString(R.string.form_date_label));
                    Integer year = Integer.parseInt(dateString.substring(0, 4));
                    Integer month = Integer.parseInt(dateString.substring(4, 6)) - 1;
                    Calendar dateCalendar = formatFromDateStringToCalendar(dateString);
                    BigDecimal valueBigDecimal = getBigDecimalValue();
                    loadingDialog.show();
                    Finance finance;
                    if (idToUpdate == null) {
                        finance = new Finance(name, dateCalendar, month, year, valueBigDecimal, sector, typeFinance);
                    } else {
                        finance = new Finance(idToUpdate, name, dateCalendar, month, year, valueBigDecimal, sector, typeFinance);
                    }
                    categoryDAO.getSaveAsyncTask(finance, () -> {
                        loadingDialog.dismiss();
                        if (idToUpdate == null) {
                            showToast(getApplicationContext(), getResources().getString(R.string.add_finance_toast_message));
                        } else {
                            showToast(getApplicationContext(), getResources().getString(R.string.update_finance_toast_message));
                        }
                        finish();
                    }).execute();
                } catch (Exception e) {
                    showToast(getApplicationContext(), "All fields are required");
                }
            }
        });
    }

    protected void fillForm(Finance entity) {
        idToUpdate = entity.getId();
        financeFormName.setText(entity.getName());
        financeFormDate.setText(getResources().getString(R.string.form_date_label)+" "+formatter.format(entity.getDate().getTime()));
        financeFormValue.setText(entity.getValue()+"");
        financeFormType.check(R.id.finance_form_income);
        if (entity.getTypeFinance().equals(TypeFinance.EXPENSE)){
            financeFormType.check(R.id.finance_form_expense);
        }
        onViewDrawn(financeFormSectorSpinner, () -> {
            List<String> sectors = Arrays.asList(getResources().getStringArray(R.array.sector_finance));
            financeFormSectorSpinner.setSelection(sectors.indexOf(entity.getSector().getValue()));
        });

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

    protected void getLayoutViews() {
        financeFormName = findViewById(R.id.finance_form_name);
        financeFormDate = findViewById(R.id.finance_form_date);
        financeFormValue = findViewById(R.id.finance_form_value);
        financeFormType = findViewById(R.id.finance_form_type);
        financeFormTypeIncome = findViewById(R.id.finance_form_income);
        financeFormSectorSpinner = findViewById(R.id.finance_form_sector);
        financeFormButtonSubmit = findViewById(R.id.finance_form_button_submit);
    }

}