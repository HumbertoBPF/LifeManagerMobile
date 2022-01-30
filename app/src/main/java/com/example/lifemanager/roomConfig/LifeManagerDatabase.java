package com.example.lifemanager.roomConfig;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.lifemanager.dao.RoomFinanceDAO;
import com.example.lifemanager.dao.RoomStudiesDAO;
import com.example.lifemanager.dao.RoomTaskDAO;
import com.example.lifemanager.model.Finance;
import com.example.lifemanager.model.Studies;
import com.example.lifemanager.model.Task;
import com.example.lifemanager.type_converters.ConverterBigDecimal;
import com.example.lifemanager.type_converters.ConverterCalendar;

@Database(entities = {Studies.class, Finance.class, Task.class}, version = 1, exportSchema = false)
@TypeConverters({ConverterBigDecimal.class, ConverterCalendar.class})
public abstract class LifeManagerDatabase extends RoomDatabase {

    private static final String NAME_DATABASE = "lifeManager.db";

    public abstract RoomStudiesDAO getRoomStudiesDAO();
    public abstract RoomFinanceDAO getRoomFinanceDAO();
    public abstract RoomTaskDAO getRoomTaskDAO();

    public static LifeManagerDatabase getInstance(Context context){
        return Room
                .databaseBuilder(context,LifeManagerDatabase.class,NAME_DATABASE).build();
    }

}
