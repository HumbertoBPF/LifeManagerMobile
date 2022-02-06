package com.example.lifemanager.dao;

import androidx.room.Dao;

import com.example.lifemanager.model.Finance;

@Dao
public abstract class FinanceDAO extends BaseDAO<Finance>{

    protected FinanceDAO() {
        super("Finance", "ORDER BY date DESC");
    }

}
