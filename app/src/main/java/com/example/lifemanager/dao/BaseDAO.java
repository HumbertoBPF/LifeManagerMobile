package com.example.lifemanager.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

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
