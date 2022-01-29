package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.lifemanager.model.Task;

import java.util.List;

@Dao
public abstract class RoomTaskDAO extends BaseDAO<Task>{

    @Query("SELECT * FROM task WHERE id = :id;")
    public abstract Task getTaskById(Long id);

    @Query("SELECT * FROM task ORDER BY dueDate;")
    public abstract List<Task> getAllTasks();

}
