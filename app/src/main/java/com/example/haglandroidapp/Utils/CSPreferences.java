package com.example.haglandroidapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/*
 * Created by rishav on 18/1/17.
 */

public class CSPreferences {

    public static final String PREF = "pref";

    public static SharedPreferences getPref(Context activity) {
        return activity.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public static void putString(Context activity, String key, String value) {
        SharedPreferences.Editor editor = getPref(activity).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readString(Context activity, String key) {
        return getPref(activity).getString(key, "");
    }

    public static void clearAll(Context activity) {
        getPref(activity).edit().clear().commit();
    }

}
