package com.example.haglandroidapp.background;


import android.content.Context;
import android.content.Intent;

import com.clover.sdk.v1.app.AppNotification;
import com.clover.sdk.v1.app.AppNotificationReceiver;
import com.example.haglandroidapp.Activities.DashboardActivity2;

/**
 * Created by jiongshen on 12/9/14.
 */
public class NotificationReceiver extends AppNotificationReceiver {
    public final static String TEST_NOTIFICATION_ACTION = "test_notification";

    public NotificationReceiver() {

    }

    @Override
    public void onReceive(Context context, AppNotification notification) {
        if (notification.appEvent.equals(TEST_NOTIFICATION_ACTION)) {
            Intent intent = new Intent(context, DashboardActivity2.class);
            intent.putExtra(DashboardActivity2.EXTRA_DATA, notification.payload);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}