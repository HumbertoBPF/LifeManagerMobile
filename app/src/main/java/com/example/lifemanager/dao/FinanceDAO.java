package com.example.lifemanager.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.lifemanager.model.Finance;

import java.util.List;

@Dao
public abstract class FinanceDAO extends BaseDAO<Finance>{

    @Query("SELECT * FROM finance WHERE id = :id;")
    public abstract Finance getFinanceById(Long id);

    @Query("SELECT * FROM finance ORDER BY date DESC;")
    public abstract List<Finance> getAllFinances();

}
