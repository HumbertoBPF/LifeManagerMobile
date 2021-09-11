package com.example.lifemanager.type_converters;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class ConverterCalendar {

    @TypeConverter
    public String forString(Calendar date){
        return date.toString();
    }

    @TypeConverter
    public Calendar forLocalDate(String date){
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8,10));
        calendar.set(year,month,day);
        return calendar;
    }
}
