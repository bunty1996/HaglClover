package com.example.haglandroidapp.Utils;

import android.accounts.Account;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.order.Discount;
import com.clover.sdk.v3.order.OrderConnector;
import com.example.haglandroidapp.Models.DiscountPrice;
import com.example.haglandroidapp.Models.LastDiscountBackup;
import com.example.haglandroidapp.orders.DatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LineItemsDeleteAsync extends AsyncTask<Object, Void, String> {
    private static final String TAG = LineItemsDeleteAsync.class.getSimpleName();
    OrderConnector orderConnector;
    DatabaseOpenHelper databaseOpenHelper;
    private Account mAccount;

    public LineItemsDeleteAsync() {
    }

    @Override
    protected String doInBackground(Object... params) {
        String order_id = (String) params[0];
        List<String> lv_discounts = (List<String>) params[2];
        List<String> lv_lineitems = (List<String>) params[1];
        String action_type = (String) params[3];
        List<DiscountPrice> dis_price = new ArrayList<>();
        dis_price  =  (List<DiscountPrice>) params[4];
        Log.d("action_type>>>", action_type + ">>>" + dis_price);
        try {
            Log.d("called",  "yes");
            if (action_type.equals(Commons.ADD) || action_type.equals(Commons.REMOVE)) {
                Log.d("action1",  dis_price.toString());
                for (int i = 0; i < dis_price.size(); i++) {
                    Log.d("action2", action_type);
                    double total_discont = Double.parseDouble(dis_price.get(i).getAppleddiscount() + "") * 100;
                    Log.d("actionapplied1", String.valueOf(dis_price.get(i).getAppleddiscount()));
                    Log.d("total_discont56", String.valueOf(total_discont));

                    long appliedDiscount = (long) total_discont;
                    if (dis_price.get(i).getDiscount_type() != null) {

                        String price_typee = dis_price.get(i).getDiscount_type();
                        if (price_typee.equals(Commons.PERCENTAGE)) {
                            appliedDiscount = appliedDiscount * -1;
                            Log.d("appliedDiscount12", String.valueOf(appliedDiscount));

                            Discount discount = new Discount();
                            discount.setName(dis_price.get(i).getDiscount_price() + "% off");
                            discount.setAmount(appliedDiscount);
                            Log.d("actionapplied5", String.valueOf(appliedDiscount));

                            //if(total_statemin)
                            if (dis_price.get(i).getCloverItemsCount() >= dis_price.get(i).getQunatity()) {
                                LastDiscountBackup lastDiscountBackup = new LastDiscountBackup();
                                lastDiscountBackup.setBkup_discountname(dis_price.get(i).getDiscount_name());
                                lastDiscountBackup.setBkup_discount(appliedDiscount + "");
                                //lastDiscountBackup.setBkup_discountid();
                                lastDiscountBackup.setBkup_discounttype(dis_price.get(i).getDiscount_type());
                                List<LastDiscountBackup> lv_backup = new ArrayList<>();

                                lv_backup = databaseOpenHelper.getLastDiscountBackup();
                                Log.d("hiii1", lv_backup.toString());
                                if (lv_backup.toString().equals("[]")) {
                                    Log.d("hiii2", lv_backup.toString());
                                    databaseOpenHelper.saveBackupOfDiscount(lastDiscountBackup, TAG);
                                } else {
                                    int count = 0;
                                    Log.d("hiiielse", lv_backup.toString());
                                    for (LastDiscountBackup lastDiscountBackup1 : lv_backup) {
                                        Log.d("conatins>>>1", lv_backup.size() + "");
                                        if (lastDiscountBackup1.getBkup_discountname().equals(lastDiscountBackup.getBkup_discountname())) {
                                            //update
                                            Log.d("hiii", lv_backup.toString());
                                            lastDiscountBackup.setBkup_discountid(lastDiscountBackup1.getBkup_discountid());
                                            databaseOpenHelper.updateDiscount(lastDiscountBackup, TAG);
                                        } else {
                                            List<LastDiscountBackup> lv_data = new ArrayList<>();
                                            lv_data = databaseOpenHelper.getLastDiscountBackup();
                                            for (int h = 0; h < lv_data.size(); h++) {
                                                Log.d("conatins1>>>", count + "");
                                                if (lv_data.get(h).getBkup_discountname().equals(lastDiscountBackup.getBkup_discountname())) {
                                                    Log.d("conatins2>>>", lv_data.size() + "count " + count);
                                                    count++;
                                                } else {
                                                    Log.d("conatins3>>>", count + "");
                                                }
                                            }
                                            if (count == 0) {
                                                Log.d("notconatins>>>", lastDiscountBackup.getBkup_discountname() + "");
                                                databaseOpenHelper.saveBackupOfDiscount(lastDiscountBackup, TAG);
                                            }
                                        }
                                    }
                                }
                                orderConnector.addDiscount(order_id, discount);
                            }
                        } else if (price_typee.equals(Commons.DOLLAR_AMOUNT)) {
                            appliedDiscount = appliedDiscount * -1;
                            Discount discount = new Discount();
                            discount.setName(dis_price.get(i).getDiscount_price() + " off");
                            discount.setAmount(appliedDiscount);
                            if (dis_price.get(i).getCloverItemsCount() >= dis_price.get(i).getQunatity()) {
                                LastDiscountBackup lastDiscountBackup = new LastDiscountBackup();
                                lastDiscountBackup.setBkup_discountname(dis_price.get(i).getDiscount_name());
                                lastDiscountBackup.setBkup_discount(appliedDiscount + "");
                                lastDiscountBackup.setBkup_discounttype(dis_price.get(i).getDiscount_type());
                                List<LastDiscountBackup> lv_backup = new ArrayList<>();
                                lv_backup = databaseOpenHelper.getLastDiscountBackup();
                                Log.d("hiii1d", lv_backup.toString());
                                if (lv_backup.toString().equals("[]")) {
                                    Log.d("hiii2d", lv_backup.toString());
                                    databaseOpenHelper.saveBackupOfDiscount(lastDiscountBackup, TAG);
                                } else {
                                    int count = 0;
                                    Log.d("hiiielsed", lv_backup.toString());
                                    for (LastDiscountBackup lastDiscountBackup1 : lv_backup) {
                                        Log.d("conatins>>>1d", lv_backup.size() + "");
                                        Log.d("lastDiscountBackup1", lastDiscountBackup1.toString());
                                        if (lastDiscountBackup1.getBkup_discountname().equals(lastDiscountBackup.getBkup_discountname())) {
                                            //update

                                            lastDiscountBackup.setBkup_discountid(lastDiscountBackup1.getBkup_discountid());
                                            databaseOpenHelper.updateDiscount(lastDiscountBackup, TAG);
                                        } else {
                                            List<LastDiscountBackup> lv_data = new ArrayList<>();
                                            lv_data = databaseOpenHelper.getLastDiscountBackup();
                                            for (int h = 0; h < lv_data.size(); h++) {
                                                Log.d("conatins1>>>d", count + "");
                                                if (lv_data.get(h).getBkup_discountname().equals(lastDiscountBackup.getBkup_discountname())) {
                                                    Log.d("conatins2>>>d", lv_data.size() + "count " + count);
                                                    count++;
                                                } else {
                                                    Log.d("conatins3>>>d", count + "");
                                                }
                                            }
                                            if (count == 0) {
                                                Log.d("notconatins>>>d", lastDiscountBackup.getBkup_discountname() + "");
                                                databaseOpenHelper.saveBackupOfDiscount(lastDiscountBackup, TAG);
                                            }
                                        }
                                    }
                                }
                                //databaseOpenHelper.saveBackupOfDiscount(lastDiscountBackup, TAG);
                                orderConnector.addDiscount(order_id, discount);
                            }
                        }
                    }
                }

            } else if (action_type.equals(Commons.LOCAL_DISCOUNT)) {
                String disType_prefix ="";
                List<LastDiscountBackup> lv_backup = new ArrayList<>();
                lv_backup = databaseOpenHelper.getLastDiscountBackup();
                for (int k = 0; k < dis_price.size(); k++) {
                    for (int j = 0; j < lv_backup.size(); j++) {
                        if (lv_backup.get(j).getBkup_discountname().equalsIgnoreCase(dis_price.get(k).getDiscount_name())) {
                            if (dis_price.get(k).getAction_type().equals(Commons.LOCAL_DISCOUNT)) {
                                Discount discount = new Discount();
                                if(lv_backup.get(j).getBkup_discounttype().equals(Commons.DOLLAR_AMOUNT))
                                {
                                    discount.setName(dis_price.get(k).getDiscount_price() + " off");
                                }else {
                                    discount.setName(dis_price.get(k).getDiscount_price() + "% off");
                                }

                                long price = Long.parseLong(lv_backup.get(j).getBkup_discount());
                                discount.setAmount(price);
                                orderConnector.addDiscount(order_id, discount);
                            }
                        } else {
                            Log.d("discountElse", lv_backup.toString());
                        }
                    }
                }

            } else if (action_type.equals(Commons.REMOVE_DISCOUNTBELOWSTATE)) {
                Log.d("DISCOUNTBELOWSTATE", "REMOVE_DISCOUNTBELOWSTATE");
                for (int j = 0; j < lv_lineitems.size(); j++) {
                    orderConnector.deleteLineItemDiscounts(order_id, lv_lineitems.get(j), lv_discounts);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (BindingException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        int count = 0;
        super.onPreExecute();
        SharedPref.init(MyApplication.context());
        databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
        mAccount = CloverAccount.getAccount(MyApplication.context());
        orderConnector = new OrderConnector(MyApplication.context(), mAccount, null);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("StringLineItem>>>>>>", s + "");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }
}
