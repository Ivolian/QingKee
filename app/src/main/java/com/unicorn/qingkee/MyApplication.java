package com.unicorn.qingkee;

import android.app.Application;

import com.unicorn.qingkee.bean.UserInfo;
import com.unicorn.qingkee.volley.MyVolley;


public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {

        return instance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        init();
    }

    private void init() {

        instance = this;
        MyVolley.init(this);
    }

    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
