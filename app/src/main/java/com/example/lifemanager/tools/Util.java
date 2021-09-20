package com.example.lifemanager.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Util {

    public static void showToast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }

    public static void setActionBarTitle(Activity activity, String title){
        if (title != null){
            activity.setTitle(title);
        }
    }

    public static void setActionBarColor(AppCompatActivity activity, Integer color){
        if (color != null){
            ColorDrawable colorDrawable = new ColorDrawable(color);
            activity.getSupportActionBar().setBackgroundDrawable(colorDrawable);
        }
    }

    public static Calendar formatFromDateStringToCalendar(String dateString){
        Integer year = Integer.parseInt(dateString.substring(0,4));
        Integer month = Integer.parseInt(dateString.substring(4,6))-1;
        Integer day = Integer.parseInt(dateString.substring(6,8));
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.set(year,month,day);
        return dateCalendar;
    }

}
