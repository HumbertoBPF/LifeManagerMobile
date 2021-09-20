package com.example.lifemanager.type_converters;

import static com.example.lifemanager.model.Constants.formatter;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class ConverterCalendar {

    @TypeConverter
    public String forString(Calendar date){
        return formatter.format(date.getTime());
    }

    @TypeConverter
    public Calendar forCalendar(String date){
        Log.i("stringToConvert",date);
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5,7))-1;
        int day = Integer.parseInt(date.substring(8,10));
        calendar.set(year,month,day);
        return calendar;
    }
}
