package com.example.lifemanager.tools;

import android.content.Context;
import android.widget.Toast;

public class Util {

    public static void showToast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }

}
