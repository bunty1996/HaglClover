package com.example.haglandroidapp.Utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Utils {

    private static ProgressDialog progressDialog;
    // (AndrewMartin MerchantID)
    public static final String MERCHANTSID = "41HJH17GJD4H1";
    //    (Hagl MerchantID)
//    public static final String MERCHANTSID = "A3KC1QFWWNX71";

    // (AndrewMartin MerchantID)
    public static final String TOKEN22 = "Bearer dfafec9b-90c8-5a15-c825-ee7290ac1471";
//    public static final String Auth_TOKEN = "dfafec9b-90c8-5a15-c825-ee7290ac1471";

    //    (Hagl MerchantID)
    public static String TOKEN11 = "Bearer 10752486-6f09-41d2-b273-a8236258ca38";
    public static String TOKEN;
//    public static final String Auth_TOKEN = "10752486-6f09-41d2-b273-a8236258ca38";


    public static final String ImageBaseURL = "http://13.54.226.124/";


    public static final String SETNAME = "SETNAME";
    public static final String SETCOST = "SETCOST";
    public static final String SETPRICE = "SETPRICE";
    public static final String LOGINTYPE = "logintype";
    public static final String USERID = "userid";


    public static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    // for keyboard hide
    public static void hideKeyboardFrom(Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showDialogMethod(Activity activity, FragmentManager supportFragmentManager) {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    public static void hideDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();

            }
        } catch (Exception e) {
        }
    }

    public static void showMessage(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    //camra
    public static boolean hasFeatureCamera(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


//    public static void changeFragment(Context context, androidx.fragment.app.Fragment fragment) {
//
//        androidx.fragment.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commitAllowingStateLoss();
//
//    }
//
//    public static void changeTabFragment(Context context, androidx.fragment.app.Fragment fragment) {
//
//        androidx.fragment.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).addToBackStack(null).commitAllowingStateLoss();
//
//    }

}
