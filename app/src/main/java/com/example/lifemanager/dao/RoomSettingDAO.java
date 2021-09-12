package com.example.lifemanager.dao;

import static com.example.lifemanager.model.Constants.USERNAME_FOR_APP;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lifemanager.model.Setting;

import java.util.List;

@Dao
public interface RoomSettingDAO {

    @Query("SELECT * FROM setting WHERE name = '"+ USERNAME_FOR_APP+"'")
    List<Setting> getUsernameSetting();

    @Insert
    void save(Setting setting);

    @Update
    void update(Setting setting);

}
