/**
 * Created by ${Raman} on ${01/09/2020}.
 */

package com.example.haglandroidapp.orders;


import android.accounts.Account;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.order.OrderConnector;
import com.example.haglandroidapp.Utils.MyApplication;


import java.util.List;

public class DeleteAppliedDiscount extends AsyncTask<Object, Void, String> {
    private static final String TAG = DeleteAppliedDiscount.class.getSimpleName();
    OrderConnector orderConnector;
    Account mAccount;

    public DeleteAppliedDiscount() {

    }

    @Override
    protected String doInBackground(Object... params) {
        Log.d("DeleteAppliedDiscount", "Started");
        String order_id = (String) params[0];
        List<String> lv_dis = (List<String>) params[1];

        try {

            orderConnector.deleteDiscounts(order_id, lv_dis);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        } catch (ClientException e1) {
            e1.printStackTrace();
        } catch (ServiceException e1) {
            e1.printStackTrace();
        } catch (BindingException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mAccount = CloverAccount.getAccount(MyApplication.context());
        orderConnector = new OrderConnector(MyApplication.context(), mAccount, null);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("DeletedResult>>>>>>", s + "");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

