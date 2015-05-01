package com.unicorn.qingkee;

import android.app.Application;



public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {

        super.onCreate();
        init();
    }

    private void init() {

        instance = this;
    }


    public static MyApplication getInstance() {

        return instance;
    }



}
