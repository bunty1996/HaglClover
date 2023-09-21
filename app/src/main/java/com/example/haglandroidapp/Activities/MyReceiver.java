package com.example.haglandroidapp.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.clover.sdk.v1.Intents;
import com.clover.sdk.v1.app.AppNotification;

public class MyReceiver extends BroadcastReceiver {
   /* @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(Intents.ACTION_ORDER_CREATED)) {
            String orderId = intent.getStringExtra(Intents.EXTRA_CLOVER_ORDER_ID);
            String productId = intent.getStringExtra(Intents.EXTRA_CLOVER_ITEM_ID);
            Log.e("ORDERRRID", orderId);
            Log.e("PRODUCTID", productId);
        }
    }*/

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Broadcast Message Recieved", Toast.LENGTH_SHORT).show();
        Log.e(String.valueOf(context), "Broadcast Message Recieved");
//        try {
//            System.out.println("Receiver start");
//            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//                Intent local = new Intent();
//                local.setAction("service.to.activity.transfer");
//                local.putExtra("number", incomingNumber);
//                context.sendBroadcast(local);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
