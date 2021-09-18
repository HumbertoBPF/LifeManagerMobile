package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lifemanager.model.Task;

import java.util.List;

@Dao
public interface RoomTaskDAO {

    @Query("SELECT * FROM task WHERE id = :id;")
    Task getTaskById(Long id);

    @Query("SELECT * FROM task ORDER BY dueDate;")
    List<Task> getAllTasks();

    @Insert
    void save(Task task);

    @Delete
    void delete(Task task);

}
