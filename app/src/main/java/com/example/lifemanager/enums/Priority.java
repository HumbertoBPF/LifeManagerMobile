package com.example.lifemanager.enums;

public enum Priority {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    private final String value;

    private Priority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}