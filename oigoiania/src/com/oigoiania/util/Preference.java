package com.oigoiania.util;


import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private static final String PREF_KEY = "RememberMe";

    public static void saveText(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void saveNumber(Context context, String key, int value) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(key, value);

        edit.commit();
    }

    public static int restoreNumber(Context context, String key) {
        SharedPreferences savedSession =
                context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return savedSession.getInt(key, 0);
    }

    public static String restoreText(Context context, String key) {
        SharedPreferences savedSession =
                context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return savedSession.getString(key, "");
    }

    public static void clearKeys(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }
}


