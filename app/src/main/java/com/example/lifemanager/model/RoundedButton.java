package com.example.lifemanager.model;

public class RoundedButton {

    private String name;
    private int colorId;
    private Class nextActivity;

    public RoundedButton(String name, int colorId, Class nextActivity) {
        this.name = name;
        this.colorId = colorId;
        this.nextActivity = nextActivity;
    }

    public String getName() {
        return name;
    }

    public int getColorId() {
        return colorId;
    }

    public Class getNextActivity() {
        return nextActivity;
    }
}
