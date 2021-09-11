package com.example.lifemanager.roomConfig;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.lifemanager.converters.ConverterCategory;
import com.example.lifemanager.dao.RoomSettingDAO;
import com.example.lifemanager.dao.RoomStudiesDAO;
import com.example.lifemanager.model.Setting;
import com.example.lifemanager.model.Studies;

@Database(entities = {Setting.class, Studies.class}, version = 1, exportSchema = false)
@TypeConverters({ConverterCategory.class})
public abstract class LifeManagerDatabase extends RoomDatabase {

    private static final String NAME_DATABASE = "lifeManager.db";

    public abstract RoomSettingDAO getRoomSettingDAO();
    public abstract RoomStudiesDAO getRoomStudiesDAO();

    public static LifeManagerDatabase getInstance(Context context){
        return Room
                .databaseBuilder(context,LifeManagerDatabase.class,NAME_DATABASE)
                .allowMainThreadQueries().build();
    }

}
