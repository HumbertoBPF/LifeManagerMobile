package com.example.lifemanager.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Setting {

    @PrimaryKey(autoGenerate = true)
    private Long id = 0L;
    private String name;
    private String value;

    public Setting() {

    }

    @Ignore
    public Setting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Ignore
    public Setting(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
