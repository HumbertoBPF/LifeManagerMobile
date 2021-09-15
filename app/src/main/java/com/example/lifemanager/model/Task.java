package com.example.lifemanager.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.lifemanager.enums.Priority;

import java.io.Serializable;
import java.util.Calendar;

@Entity
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String subject;
    private String name;
    private String description;
    private Boolean status;
    private Priority priority;
    private Calendar deadline;
    private Calendar dueDate;

    public Task() {
    }

    @Ignore
    public Task(String subject, String name, String description, Boolean status, Priority priority, Calendar deadline,
                Calendar dueDate) {
        this.subject = subject;
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
        this.dueDate = dueDate;
    }

    @Ignore
    public Task(Long id, String subject, String name, String description, Boolean status, Priority priority,
                Calendar deadline, Calendar dueDate) {
        this.id = id;
        this.subject = subject;
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Calendar getDeadline() {
        return deadline;
    }
    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    public Calendar getDueDate() {
        return dueDate;
    }
    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

}