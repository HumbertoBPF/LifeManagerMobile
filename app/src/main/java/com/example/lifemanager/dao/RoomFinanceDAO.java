package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lifemanager.model.Finance;

import java.util.List;

@Dao
public abstract class RoomFinanceDAO extends BaseDAO<Finance>{

    @Query("SELECT * FROM finance WHERE id = :id;")
    public abstract Finance getFinanceById(Long id);

    @Query("SELECT * FROM finance ORDER BY date DESC;")
    public abstract List<Finance> getAllFinances();

    @Insert
    public abstract void save(Finance finance);

    @Update
    public abstract void update(Finance finance);

    @Delete
    public abstract void delete(Finance finance);

}
