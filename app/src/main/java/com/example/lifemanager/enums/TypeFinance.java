package com.example.lifemanager.enums;

public enum TypeFinance {
    INCOME("Income"),
    EXPENSE("Expense");

    private final String value;

    private TypeFinance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
