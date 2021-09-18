package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lifemanager.model.Studies;

import java.util.List;

@Dao
public interface RoomStudiesDAO {

    @Query("SELECT * FROM studies ORDER BY position ASC")
    List<Studies> getAllStudies();

    @Insert
    void save(Studies studies);

}
