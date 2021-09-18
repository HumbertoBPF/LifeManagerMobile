package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lifemanager.model.Task;

import java.util.List;

@Dao
public interface RoomTaskDAO {

    @Query("SELECT * FROM task ORDER BY dueDate;")
    List<Task> getAllTasks();

    @Insert
    void save(Task task);

}
