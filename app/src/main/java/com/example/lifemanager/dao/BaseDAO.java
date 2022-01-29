package com.example.lifemanager.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lifemanager.model.Task;

import java.util.List;

public abstract class BaseDAO<E> {

//    @Query("SELECT * FROM task WHERE id = :id;")
//    public abstract Task getTaskById(Long id);
//
//    @Query("SELECT * FROM task ORDER BY dueDate;")
//    public abstract List<Task> getAllTasks();

    @Insert
    public abstract void save(E task);

    @Delete
    public abstract void delete(E task);

    @Update
    public abstract void update(E task);

}
