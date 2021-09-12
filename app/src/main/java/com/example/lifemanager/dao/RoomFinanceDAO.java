package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lifemanager.model.Finance;

import java.util.List;

@Dao
public interface RoomFinanceDAO {

    @Query("SELECT * FROM finance;")
    List<Finance> getAllFinances();

    @Insert
    void save(Finance finance);

}
