/**
 * Created by ${Raman} on ${02/08/2020}.
 */

package com.example.haglandroidapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.haglandroidapp.Models.DeleteLineItemPojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPref {
    private static SharedPreferences mSharedPref;
    public static final String NAME = "NAME";
    public static final String AGE = "AGE";
    public static final String IS_SELECT = "IS_SELECT";

    private SharedPref() {
    }

    public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String getMerchantID(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void saveMerchantId(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static void saveDiscountPrice(String key, float value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putFloat(key, value);
        prefsEditor.commit();
    }

    public static float getDiscountPrice(String key, float defValue) {
        return mSharedPref.getFloat(key, defValue);
    }

    public static void isUserAlreadyRegistered(String key, String merchant_id) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, merchant_id);
        prefsEditor.commit();
    }

    public static String getIsUserAlreadyRegistered(String key, String value) {
        return mSharedPref.getString(key, value);
    }


    public static String getLastOrderResult(String key, String value) {
        return mSharedPref.getString(key, value);
    }

    public static void saveLastOrderResult(String key, String merchant_id) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, merchant_id);
        prefsEditor.commit();
    }

    public static void saveArrayLIstData(String key, String strObject) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, strObject);
        prefsEditor.commit();

    }

    public static List<DeleteLineItemPojo> getSavedList(String key, String value) {
        Gson gson = new Gson();
        List<DeleteLineItemPojo>lvmExampleList = new ArrayList<>();
        String json = mSharedPref.getString(key, "");
        Type type = new TypeToken<ArrayList<DeleteLineItemPojo>>() {}.getType();
        lvmExampleList = gson.fromJson(json, type);
        return lvmExampleList;


    }


}

