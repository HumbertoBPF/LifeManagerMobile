package com.example.lifemanager.enums;

public enum Sector {
    FOOD("Food"),
    TRIPS("Trips"),
    TRANSPORT("Transport"),
    HOUSING("Housing"),
    OTHER("Other");

    private final String value;

    private Sector(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
