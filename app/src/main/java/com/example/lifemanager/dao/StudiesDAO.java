package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.lifemanager.model.Studies;

import java.util.List;

@Dao
public abstract class StudiesDAO extends BaseDAO<Studies>{

    @Query("SELECT * FROM studies WHERE id = :id")
    public abstract Studies getStudyById(Long id);

    @Query("SELECT * FROM studies ORDER BY position ASC")
    public abstract List<Studies> getAllStudies();

    @Query("SELECT MAX(position) FROM studies")
    public abstract Integer getMaxPosition();

}
