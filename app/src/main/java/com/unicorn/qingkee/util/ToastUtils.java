package com.unicorn.qingkee.util;

import android.view.Gravity;
import android.widget.Toast;

import com.unicorn.qingkee.MyApplication;


public class ToastUtils {

    public static void show(String message){

        Toast toast = Toast.makeText(MyApplication.getInstance().getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void show(Object message){

        show(message.toString());
    }

}
