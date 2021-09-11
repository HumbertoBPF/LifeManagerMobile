package com.example.lifemanager.roomConfig;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.lifemanager.dao.RoomSettingDAO;
import com.example.lifemanager.model.Setting;

@Database(entities = {Setting.class}, version = 1, exportSchema = false)
public abstract class LifeManagerDatabase extends RoomDatabase {

    private static final String NAME_DATABASE = "lifeManager.db";

    public abstract RoomSettingDAO getRoomSettingDAO();

    public static LifeManagerDatabase getInstance(Context context){
        return Room
                .databaseBuilder(context,LifeManagerDatabase.class,NAME_DATABASE)
                .allowMainThreadQueries().build();
    }

}
