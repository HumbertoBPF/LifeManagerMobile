package com.example.lifemanager;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.NumberFormat;
import java.util.Locale;

public class Settings {

    private final Context context;
    private String username;
    private Boolean toastsEnabled;
    private NumberFormat currencyFormat;

    private final String USERNAME_FOR_APP = "usernameForApp";
    private final String ENABLE_TOASTS = "enableToasts";
    private final String CURRENCY_TYPE = "currencyType";

    public Settings(Context context){
        this.context = context;
        this.username = getSettingFromSharedPref(context,USERNAME_FOR_APP);
        if (!getSettingFromSharedPref(context,ENABLE_TOASTS).isEmpty() && !getSettingFromSharedPref(context,CURRENCY_TYPE).isEmpty()){
            this.toastsEnabled = getSettingFromSharedPref(context,ENABLE_TOASTS).equals("true");
            this.currencyFormat = getSettingFromSharedPref(context,CURRENCY_TYPE).equals("Euro")?
                    NumberFormat.getCurrencyInstance(Locale.FRANCE):NumberFormat.getCurrencyInstance(Locale.US);
        }
    }

    public String getUsername() {
        return username;
    }

    public Boolean getToastsEnabled() {
        return toastsEnabled;
    }

    public NumberFormat getCurrencyFormat() {
        return currencyFormat;
    }

    public void setUsername(String username) {
        this.username = username;
        saveSettingOnSharedPref(context,USERNAME_FOR_APP,this.username);
    }

    public void setToastsEnabled(Boolean toastsEnabled) {
        this.toastsEnabled = toastsEnabled;
        saveSettingOnSharedPref(context,ENABLE_TOASTS,this.toastsEnabled?"true":"false");
    }

    public void setCurrencyFormat(NumberFormat currencyFormat) {
        this.currencyFormat = currencyFormat;
        saveSettingOnSharedPref(context,CURRENCY_TYPE,
                this.currencyFormat.equals(NumberFormat.getCurrencyInstance(Locale.FRANCE))?"Euro":"Dollar");
    }

    public void defaultSettings() {
        setUsername("");
        setToastsEnabled(true);
        setCurrencyFormat(NumberFormat.getCurrencyInstance(Locale.FRANCE));
    }

    public void saveSettingOnSharedPref(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(key,value);
        myEdit.apply();
    }

    public String getSettingFromSharedPref(Context context, String key){
        SharedPreferences sh = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sh.getString(key, "");
    }

}
