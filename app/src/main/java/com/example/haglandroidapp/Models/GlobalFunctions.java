/**
 * Created by ${Raman} on ${12/08/2020}.
 */

package com.example.haglandroidapp.Models;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haglandroidapp.Utils.MyApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class GlobalFunctions {

    public static void closeKeyboardOnStartActivity(Activity activity, View view) {
        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String abc(String textView) {
        if(textView==null){
            textView= "Enter value";

        }else
       if(textView.equals("")){
           textView= "Enter value";
       }
        return textView;
    }

    public static String chkIsFieldNull(String textView, Activity mActivity) {
        if (textView == "") {
            textView = "-";
        } else if (textView == "null")
            textView = "-";
        else if (textView == null)
            textView = "_";
        else if (textView == "_")
            textView = "-";
        return textView;
    }



    public static String chkSqlFieldEWntryNull(String fieldValue, String TAG) {
        if (fieldValue == null || fieldValue == "null" || fieldValue == "") {
            fieldValue = "_";
        }
        Log.i(TAG, "field," + fieldValue);
        return fieldValue;

    }

    public static String chkEditextUnderSCrore(String fieldValue) {
        if (fieldValue.equals("_")) {
            fieldValue = "";
        }
        return fieldValue;

    }



    public static double removeSpecialChars(double neg_value) {
        double value = Math.abs(neg_value);
        return value;
    }

    public static String chkJsonObjectIsNulll(String jsonObj, String TAG) {
        if (jsonObj == null || jsonObj == "null" || jsonObj == "") {
            jsonObj = "";
        }else
        Log.d("Percentage>>>", jsonObj + "");
        return jsonObj;
    }

    public static String chkJsonObjectIsNullll(String jsonObj, String TAG) {
        if ( jsonObj == "" ) {
            jsonObj = "_";
        }else
            Log.d("Percentage>>>", jsonObj + "");
     return jsonObj;
    }

    public static String getDateFromTimestamp(long time) {
        Log.d("ChkDate", time + "");
        String date = DateFormat.format("MM/dd/yyyy", time).toString();
        if (date.equals("") || date == null) {
            date = "_";
        }
        Log.d("ChkDate", date + "");
        return date;
    }

    public static long getTimeStamp(String expiray) {
        long timestamp;
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = format.parse(expiray);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            timestamp = 0;
        } else {
            timestamp = date.getTime();
        }
        return timestamp;
    }

    public static void hideAndroidKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            //ShowMessage.message(MyApplication.context()," showing");
        } else {
            // ShowMessage.message(MyApplication.context(),"not showing");
        }


    }

    public static long isRetailPriceBelowStateMinForPer(double retail_price, double state_min, double discountapplied, long count) {

        double total_statemin = state_min * count;
        double total_retail = retail_price * count;
        double total_amount = (total_retail - discountapplied);
        Log.d("total_amount", total_amount + "total_statemin" + total_statemin + "gghfgasg" + discountapplied);
        if (total_amount > total_statemin) {
            return 1; // yes
        } else {
            return 0; // yes
        }
    }

    public static long isRetailPriceBelowStateMinForFlat(double retail_price, double state_min, double discountapplied) {
        double total_amount = (retail_price - discountapplied);
        Log.d("total_amount", total_amount + "total_statemin" + state_min);
        if (total_amount > state_min) {
            return 1; // yes // discount will be applicable
        } else {
            return 0; // discount not appliacble//show message
        }
    }

    // Get Full name and email from auth data (from authResult)
    public static String getSplittedEmail(String json_string) {
        String[] parts = json_string.split("\\|");
        String email = parts[1];
        return email;
    }

    public static String getSplittedFullName(String json_string) {
        String[] parts = json_string.split("\\|");
        String full_name = parts[0];
        return full_name;
    }

    // For check edittext fileds validations
    public static boolean chkIsValidateNeedAssistance(EditText editText, String message) {
        if (editText.getText().toString().trim().length() == 0) {
            Toast.makeText(MyApplication.context(), message, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static int getCurrentAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        //Integer ageInt = new Integer(age);
        //String ageS = ageInt.toString();

        return age;
    }

}

