package com.example.lifemanager.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.lifemanager.R;
import com.example.lifemanager.async_tasks.AsyncTask;
import com.example.lifemanager.dao.RoomSettingDAO;
import com.example.lifemanager.fragments.DatePickerFragment;
import com.example.lifemanager.model.Setting;
import com.example.lifemanager.roomConfig.LifeManagerDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Util {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastIfEnabled(Context context, String text){

        new AsyncTask(new AsyncTask.AsyncTaskInterface() {
            @Override
            public List<Object> doInBackground() {
                RoomSettingDAO roomSettingDAO = LifeManagerDatabase.getInstance(context).getRoomSettingDAO();
                List<Object> objects = new ArrayList<>();
                objects.add(roomSettingDAO.getEnableToastsSetting());
                return objects;
            }

            @Override
            public void onPostExecute(List<Object> objects) {
                Setting setting = (Setting) objects.get(0);
                boolean showToast = setting.getValue().equals("true");
                if (showToast){
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
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

    public static AlertDialog yesOrNoDialog(Context context, String title, String message, String yesOption, String noOption,
                                            DialogInterface.OnClickListener onClickListenerYes, DialogInterface.OnClickListener onClickListenerNo){
        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message).setPositiveButton(yesOption, onClickListenerYes)
                .setNegativeButton(noOption, onClickListenerNo);
        return builder.create();
    }

    public static AlertDialog deletionDialog(Context context, DialogInterface.OnClickListener onClickListenerYes){
        return yesOrNoDialog(context, context.getResources().getString(R.string.deletion_dialog_title),
                context.getResources().getString(R.string.deletion_dialog_message),
                context.getResources().getString(R.string.deletion_dialog_yes),
                context.getResources().getString(R.string.deletion_dialog_no),
                onClickListenerYes,null);
    }

    public static void configureDatePicker(FragmentManager fragmentManager, TextView datePickerInput, String label, String tagName) {
        datePickerInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setOnDateSetListener(new DatePickerFragment.OnDateSetListener() {
                    @Override
                    public void OnDateSetListener(int year, int month, int day) {
                        String monthString = month+"";
                        String dayString = day+"";
                        if (month<10){
                            monthString = "0" + monthString;
                        }
                        if (day<10){
                            dayString = "0" + dayString;
                        }
                        datePickerInput.setText(label+" "+year+"-"+monthString+"-"+dayString);
                    }
                });
                datePickerFragment.show(fragmentManager,tagName);
            }
        });
    }

    @NonNull
    public static String getDateFromPicker(TextView datePickerInput, String label) {
        String dateString = datePickerInput.getText().toString();
        dateString = dateString.replace(label,"");
        dateString = dateString.replace("-","");
        dateString = dateString.replace(" ","");
        return dateString;
    }

}
