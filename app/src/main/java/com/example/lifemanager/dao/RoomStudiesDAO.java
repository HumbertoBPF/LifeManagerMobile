package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lifemanager.model.Studies;

import java.util.List;

@Dao
public interface RoomStudiesDAO {

    @Query("SELECT * FROM studies WHERE id = :id")
    Studies getStudyById(Long id);

    @Query("SELECT * FROM studies ORDER BY position ASC")
    List<Studies> getAllStudies();

    @Insert
    void save(Studies study);

    @Delete
    void delete(Studies study);

    @Update
    void update(Studies study);

}
