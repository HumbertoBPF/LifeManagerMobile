package com.example.lifemanager.converters;

import static com.example.lifemanager.enums.Category.BACKEND;
import static com.example.lifemanager.enums.Category.DATA_SCIENCE;
import static com.example.lifemanager.enums.Category.DEVOPS;
import static com.example.lifemanager.enums.Category.FRONTEND;
import static com.example.lifemanager.enums.Category.GENERAL_PROGRAMMING;
import static com.example.lifemanager.enums.Category.LANGUAGES;
import static com.example.lifemanager.enums.Category.MOBILE;
import static com.example.lifemanager.enums.Category.OTHER;

import androidx.room.TypeConverter;

import com.example.lifemanager.enums.Category;

public class ConverterCategory {

    @TypeConverter
    public String forString(Category category){
        return category.getValue();
    }

    @TypeConverter
    public Category forCategory(String category){
        if (category.equals(BACKEND.getValue())){
            return BACKEND;
        }else if (category.equals(FRONTEND.getValue())){
            return FRONTEND;
        }else if (category.equals(MOBILE.getValue())){
            return MOBILE;
        }else if (category.equals(DATA_SCIENCE.getValue())){
            return DATA_SCIENCE;
        }else if (category.equals(DEVOPS.getValue())){
            return DEVOPS;
        }else if (category.equals(GENERAL_PROGRAMMING.getValue())){
            return GENERAL_PROGRAMMING;
        }else if (category.equals(LANGUAGES.getValue())){
            return LANGUAGES;
        }else{
            return OTHER;
        }
    }

}
