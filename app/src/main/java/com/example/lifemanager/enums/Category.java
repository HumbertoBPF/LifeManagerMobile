package com.example.lifemanager.enums;

public enum Category {

    BACKEND("Back-end"),
    FRONTEND("Front-end"),
    MOBILE("Mobile"),
    DATA_SCIENCE("Data Science"),
    DEVOPS("DevOps"),
    GENERAL_PROGRAMMING("General Programming"),
    LANGUAGES("Languages"),
    OTHER("Other");

    private final String value;

    private Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
