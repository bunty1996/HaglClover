/**
 * Created by ${Raman} on ${28/08/2020}.
 */

package com.example.haglandroidapp.orders;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.order.Order;
import com.clover.sdk.v3.order.OrderConnector;
import com.example.haglandroidapp.Interface.GetOrderDetailsListener;
import com.example.haglandroidapp.Utils.CSPreferences;
import com.example.haglandroidapp.Utils.MyApplication;


import java.util.ArrayList;
import java.util.List;

public class OrderTaskAsync extends AsyncTask<Object, Void, Order> {
    Account mAccount;
    OrderConnector orderConnector;
    DatabaseOpenHelper databaseOpenHelper;
    static GetOrderDetailsListener getOrderDetailsListener;
    List<String> lv_lineitems = new ArrayList<>();
    List<String> lv_discounts = new ArrayList<>();
    String order_id;
    String whatTask;

    // constructor
    public OrderTaskAsync() {

    }

    public static void setChangeListener(GetOrderDetailsListener listener) {
        getOrderDetailsListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
        mAccount = CloverAccount.getAccount(MyApplication.context());
        orderConnector = new OrderConnector(MyApplication.context(), mAccount, null);

        // Connect to OrderConnector
        orderConnector.connect();


    }

    @Override
    protected Order doInBackground(Object... params) {
        lv_lineitems = (List<String>) params[1];
        lv_discounts = (List<String>) params[2];
        whatTask = (String) params[3];
        order_id = (String) params[0];
        Order order = null;
        try {
            order = orderConnector.getOrder(String.valueOf(params[0]));
            if (order != null) {
                //  Log.d("Loaded order", order + "");
            }
        } catch (RemoteException | ClientException | BindingException | ServiceException e) {
            e.printStackTrace();
        }
//        Log.d("OrderTaskAsync>>>>>", order.toString());
        return order;

    }

    @Override
    protected void onPostExecute(Order order) {
        //databaseOpenHelper.removeLastBackupTableData();

        if (order.getLineItems() == null) {

            new AppliedBottleReturnAsync().execute(order.getId(), "");

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.context());
            prefs.edit().remove("cashclick").commit();

            Log.e("DeleteItems", "Multiple");
            CSPreferences.putString(MyApplication.context(), "addDiscountType", "addDiscountTypeNO");
            CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", "");
            CSPreferences.putString(MyApplication.context(), "DeleteDiscount", "Multiple");
            CSPreferences.clearAll(MyApplication.context());

            DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
            databaseOpenHelper.removeLastBackupTableData();
            databaseOpenHelper.removeDiscountDataAll();


        } else {

            this.getOrderDetailsListener.callback(order, lv_lineitems, lv_discounts, order_id, whatTask);

        }

    }


}
