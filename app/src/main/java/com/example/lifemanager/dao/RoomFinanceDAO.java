package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lifemanager.model.Finance;

import java.util.List;

@Dao
public interface RoomFinanceDAO {

    @Query("SELECT * FROM finance WHERE id = :id;")
    Finance getFinanceById(Long id);

    @Query("SELECT * FROM finance ORDER BY date DESC;")
    List<Finance> getAllFinances();

    @Insert
    void save(Finance finance);

    @Update
    void update(Finance finance);

    @Delete
    void delete(Finance finance);

}
