package com.unicorn.qingkee.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.unicorn.qingkee.MyApplication;


public class SharedPreferencesUtils {

    private static final String SHARE_PREFERENCES_NAME = "MyApplication";

    private static Context getApplicationContext() {

        return MyApplication.getInstance();
    }

    private static SharedPreferences getSharedPreferences() {

        return getApplicationContext().getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static String NO_SUCH_STRING_VALUE = "NO_SUCH_STRING_VALUE";

    public static int NO_SUCH_INT_VALUE = -10101;

    public static float NO_SUCH_FLOAT_VALUE = -1.0101F;

    // ==================== string =======================

    public static boolean putString(String key, String value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(String key, String defaultValue) {

        return getSharedPreferences().getString(key, defaultValue);
    }

    public static String getString(String key) {

        return getString(key, NO_SUCH_STRING_VALUE);
    }

    // ==================== int =======================

    public static boolean putInt(String key, int value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(String key, int defaultValue) {

        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static int getInt(String key) {

        return getInt(key, NO_SUCH_INT_VALUE);
    }

    // ==================== float =======================

    public static boolean putFloat(String key, float value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static float getFloat(String key, float defaultValue) {

        return getSharedPreferences().getFloat(key, defaultValue);
    }

    public static float getFloat(String key) {

        return getFloat(key, NO_SUCH_FLOAT_VALUE);
    }

    // ==================== boolean =======================

    public static boolean putBoolean(String key, boolean value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {

        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String key) {

        return getBoolean(key, false);
    }

    // ==================== long =======================

}