package com.example.lifemanager.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifemanager.dao.RoomSettingDAO;
import com.example.lifemanager.model.Setting;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.Calendar;

public class Util {

    public static void showToast(Context context,String text, boolean showToast){
        if (showToast){
            Toast.makeText(context,text,Toast.LENGTH_LONG).show();
        }
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

    public static boolean areToastsEnabled(Context context){
        RoomSettingDAO roomSettingDAO = LifeManagerDatabase.getInstance(context).getRoomSettingDAO();
        Setting toastsEnabledSetting =  roomSettingDAO.getEnableToastsSetting();
        return !toastsEnabledSetting.getValue().equals("false");
    }

    public static StateListDrawable makeSelector(int color, float factorPressedColor) {
        StateListDrawable res = new StateListDrawable();
        GradientDrawable colorPressed = new GradientDrawable();
        colorPressed.setColor(manipulateColor(color,factorPressedColor));
        colorPressed.setCornerRadius(20);
        GradientDrawable normalColor = new GradientDrawable();
        normalColor.setColor(color);
        normalColor.setCornerRadius(20);
        res.addState(new int[]{android.R.attr.state_pressed}, colorPressed);
        res.addState(new int[]{}, normalColor);
        return res;
    }

    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a, Math.min(r,255), Math.min(g,255), Math.min(b,255));
    }

    public static void confirmDeletionDialog(Context context, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
        builder.setTitle("Confirm deletion")
                .setMessage("The item will be permanently deleted. Confirm ?")
                .setPositiveButton("Yes", onClickListener)
                .setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
