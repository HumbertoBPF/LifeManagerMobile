package com.example.lifemanager.type_converters;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class ConverterBigDecimal {

    @TypeConverter
    public BigDecimal forBigDecimal(Double value){
        return BigDecimal.valueOf(value);
    }

    @TypeConverter
    public Double forDouble(BigDecimal value){
        return value.doubleValue();
    }

}
