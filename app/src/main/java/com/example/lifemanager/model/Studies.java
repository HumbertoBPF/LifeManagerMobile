package com.example.lifemanager.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.lifemanager.enums.Category;

import java.io.Serializable;

@Entity
public class Studies implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name;
    private String linkCourse;
    private Category category;
    private Integer position;
    private Boolean status;

    public Studies() {

    }

    @Ignore
    public Studies(String name, String linkCourse, Category category, Integer position, Boolean status) {
        super();
        this.name = name;
        this.linkCourse = linkCourse;
        this.category = category;
        this.position = position;
        this.status = status;
    }

    @Ignore
    public Studies(Long id, String name, String linkCourse, Category category, Integer position, Boolean status) {
        this.id = id;
        this.name = name;
        this.linkCourse = linkCourse;
        this.category = category;
        this.position = position;
        this.status = status;
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

    public String getLinkCourse() {
        return linkCourse;
    }
    public void setLinkCourse(String linkCourse) {
        this.linkCourse = linkCourse;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }

}
