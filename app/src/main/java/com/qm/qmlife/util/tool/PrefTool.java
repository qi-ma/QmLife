package com.qm.qmlife.util.tool;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PrefTool {

    // 配置文件名字
    private static final String FILE_NAME = "file_name";

    // 初始化升级地址
    public static void initUpdateAdr(Context context) {
        //PrefTool.setStringsave(context, Prefs.PRE_SERVER_UPGRADE_ADDRESS, Common.URL5);
    }

    /**
     * 获取配置参数
     * String
     *
     * @return
     */
    public static String getStringPerferences(Context context, String name,
                                              String defValues) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        synchronized (preferences) {

            return preferences.getString(name, defValues);
        }
    }

    /*
     * String
     * 设置配置参数
     */
    public static void setStringsave(Context context, String name, String values) {
        SharedPreferences preferences = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(name, values);
        editor.commit();
    }

    /*
     * int,获取配置参数
     */
    public static int getIntPreferences(Context context, String name, int defValues) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        synchronized (preferences) {
            return preferences.getInt(name, defValues);
        }
    }

    /*
     * int,设置参数
     */
    public static void setIntSave(Context context, String name, int value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        synchronized (preferences) {
            Editor editor = preferences.edit();
            editor.putInt(name, value);
            editor.commit();
        }
    }

    /*
     * boolean类型,获取配置参数
     */
    public static boolean getBooleanPreferences(Context context, String name, boolean defValues) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        synchronized (preferences) {
            return preferences.getBoolean(name, defValues);
        }
    }

    /*
     * boolean类型,设置参数
     */
    public static void setBooleanSave(Context context, String name, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        synchronized (preferences) {
            Editor editor = preferences.edit();
            editor.putBoolean(name, value);
            editor.commit();
        }
    }

    /*---------------------Boolean-----------------------*/
    public static final boolean getBoolean(Context context, String name, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(name, defaultValue);
    }

    public static final boolean setBoolean(Context context, String name, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return preferences.edit().putBoolean(name, value).commit();
    }


    /*-----------------String-----------------------*/
    public static final String getString(Context context, String name, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(name, defaultValue);
    }

    public static final boolean setString(Context context, String name, String value) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return preferences.edit().putString(name, value).commit();
    }

    /*-----------------Long-----------------------*/
    public static final long getLong(Context context, String name, long defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(name, defaultValue);
    }

    public static final boolean setLong(Context context, String name, long value) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return preferences.edit().putLong(name, value).commit();
    }
}
