package com.example.lifemanager.dao;

import androidx.room.Dao;

import com.example.lifemanager.model.Task;

@Dao
public abstract class TaskDAO extends BaseDAO<Task>{

    protected TaskDAO() {
        super("Task", "ORDER BY dueDate");
    }

}
