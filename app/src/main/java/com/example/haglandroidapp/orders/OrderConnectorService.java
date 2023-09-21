package com.example.haglandroidapp.orders;

import android.accounts.Account;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.order.Discount;
import com.clover.sdk.v3.order.Order;
import com.clover.sdk.v3.order.OrderConnector;
import com.clover.sdk.v3.order.OrderV31Connector;
import com.example.haglandroidapp.Interface.GetOrderDetailsListener;
import com.example.haglandroidapp.Models.DiscountPrice;
import com.example.haglandroidapp.Utils.CSPreferences;
import com.example.haglandroidapp.Utils.Commons;
import com.example.haglandroidapp.Utils.MyApplication;

import java.util.List;

public class OrderConnectorService extends Service {

    private Account mAccount;
    private OrderConnector orderConnector;
    private OrderChangeListener orderChangeListener;
    private BroadcastReceiver updateUIReciver;

    private class OrderChangeListener implements OrderV31Connector.OnOrderUpdateListener2, GetOrderDetailsListener {
        @Override
        public void onOrderUpdated(String orderId, boolean selfChange) {
            Log.d("OrderChangeListener", orderId);
            // I am able to get orderId and selfChange boolean flag<br>            // Trying to call orderConnector.getOrder here but failed
        }

        @Override
        public void onOrderCreated(String orderId) {
            Log.d("onOrderCreated", orderId);

// Storing data into SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
            myEdit.putString("createdorderid", orderId);

            // Once the changes have been made,
            // we need to commit to apply those changes made,
            // otherwise, it will throw an error
            myEdit.commit();
            Log.e("createdorderid", orderId);

        }

        @Override
        public void onOrderDeleted(String orderId) {
            AppliedBottleReturnAsync.selectedArraylistDeletePending.clear();

            Log.e("DeleteItems", "Multiple");
            CSPreferences.putString(MyApplication.context(), "addDiscountType", "addDiscountTypeNO");
            CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", "");
            CSPreferences.putString(MyApplication.context(), "DeleteDiscount", "Multiple");

           /* CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", "");
            CSPreferences.putString(MyApplication.context(), "DeleteDiscount", "Multiple");
            CSPreferences.putString(MyApplication.context(), "SingleStatusChange", "");
            CSPreferences.putString(MyApplication.context(), "pendingListArraylist", "");
            CSPreferences.putString(MyApplication.context(), "Status", "");
            CSPreferences.putString(MyApplication.context(), "addDiscountType", "");
            CSPreferences.putString(MyApplication.context(), "SingleDiscountValue", "");
            CSPreferences.putString(MyApplication.context(), "haglStatus", "");
            CSPreferences.putString(MyApplication.context(), "sum1", "");*/
            CSPreferences.clearAll(MyApplication.context());

            Log.d("onOrderDeleted", orderId);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            prefs.edit().remove("cashclick").commit();
            DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
            databaseOpenHelper.removeLastBackupTableData();

        }

        @Override
        public void onOrderDiscountAdded(String orderId, String discountId) {
            Log.e("onOrderDiscountAdded", orderId + "..." + discountId);
            // new OrderTaskAsync().execute(orderId, lv_lineitems, lv_discounts, "onOrderDiscountAdded");
        }

        @Override
        public void onOrderDiscountsDeleted(String orderId, List<String> discountIds) {
            Log.e("onOrderDiscountsDeleted", orderId);
        }

        @Override
        public void onLineItemsAdded(String orderId, List<String> lineItemIds) {
            Log.e("onLineItemsAdded", lineItemIds + "");
            new OrderTaskAsync().execute(orderId, lineItemIds, lineItemIds, "Add");
        }

        @Override
        public void onLineItemsUpdated(String orderId, List<String> lineItemIds) {
            Log.e("onLineItemsUpdated", orderId);
        }

        @Override
        public void onLineItemsDeleted(String orderId, List<String> lineItemIds) {
            Log.e("onLineItemsDeleted", lineItemIds.toString());

            String arraylist = CSPreferences.readString(MyApplication.context(), "pendingListArraylist");

            new OrderTaskAsync().
                    execute(orderId, lineItemIds, lineItemIds, "Remove");
            Log.e("DeleteItems", "Single");

            CSPreferences.putString(MyApplication.context(), "addDiscountType", "addDiscountTypeNO");
            CSPreferences.putString(MyApplication.context(), "SingleDiscountValue", "");
            CSPreferences.putString(MyApplication.context(), "DeleteDiscount", "Single");
        }

        @Override
        public void onLineItemModificationsAdded(String orderId, List<String> lineItemIds, List<String> modificationIds) {
            Log.e("onLineItemModification", orderId);
            Log.e("onLineItemModiLIST", lineItemIds +"");
            Log.e("onLineItemModiLIST22", modificationIds +"");
        }

        @Override
        public void onLineItemDiscountsAdded(String orderId, List<String> lineItemIds, List<String> discountIds) {
            Log.e("onLineItemDiscountsAdd", orderId);
            Log.e("olineItemIds", lineItemIds + "");
            new OrderTaskAsync().execute(orderId, lineItemIds, discountIds, Commons.DELETE_DISCOUNT);
        }

        @Override
        public void onLineItemExchanged(String orderId, String oldLineItemId, String newLineItemId) {
            Log.e("onLineItemExchanged", orderId);
        }

        @Override
        public void onPaymentProcessed(String orderId, String paymentId) {
        }

        @Override
        public void onRefundProcessed(String orderId, String refundId) {
            Log.e("onRefundProcessed", orderId + "refund Id>>> " + refundId + "");
        }

        @Override
        public void onCreditProcessed(String orderId, String creditId) {
        }


        @Override
        public void callback(Order result, List<String> lv_data, List<String> lv_discountsa, String orderid, String whichtask) {
            Log.e("AllDt", lv_data + "");
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mAccount = CloverAccount.getAccount(this);
        Log.d("OrderChangeListener", mAccount + "");
        if (mAccount != null && orderConnector == null) {
            orderConnector = new OrderConnector(this, mAccount, null);
            orderChangeListener = new OrderChangeListener();
            orderConnector.addOnOrderChangedListener(orderChangeListener);
            orderConnector.connect();
        }
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        orderConnector.disconnect();
        if (orderChangeListener != null) {
            orderConnector.removeOnOrderChangedListener(orderChangeListener);
        }
        orderConnector = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
