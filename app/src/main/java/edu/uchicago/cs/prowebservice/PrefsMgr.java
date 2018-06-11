package edu.uchicago.cs.prowebservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsMgr {

    private static SharedPreferences sSharedPreferences;

    public static String getSearch(Context context, String key) {
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sSharedPreferences.getString(key,null);
    }

    public static void setBoolean(Context context, String key, boolean bVal) {
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putBoolean(key, bVal);
        editor.commit();

    }

    public static void setString(Context context, String key, String value) {
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public static boolean getBoolean(Context context, String key, boolean bDefault) {
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sSharedPreferences.getBoolean(key, bDefault);
    }

}
