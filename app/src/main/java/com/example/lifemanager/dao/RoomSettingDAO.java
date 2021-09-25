package com.example.lifemanager.dao;

import static com.example.lifemanager.model.Constants.ENABLE_TOASTS;
import static com.example.lifemanager.model.Constants.USERNAME_FOR_APP;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lifemanager.model.Setting;

@Dao
public interface RoomSettingDAO {

    @Query("SELECT * FROM setting WHERE name = '"+ USERNAME_FOR_APP+"'")
    Setting getUsernameSetting();

    @Query("SELECT * FROM setting WHERE name = '"+ ENABLE_TOASTS+"'")
    Setting getEnableToastsSetting();

    @Insert
    void save(Setting setting);

    @Update
    void update(Setting setting);

}
