package com.example.lifemanager.type_converters;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class ConverterBigDecimal {

    @TypeConverter
    public BigDecimal forBigDecimal(Long value){
        return BigDecimal.valueOf(value);
    }

    @TypeConverter
    public Long forLong(BigDecimal value){
        return value.longValue();
    }

}
