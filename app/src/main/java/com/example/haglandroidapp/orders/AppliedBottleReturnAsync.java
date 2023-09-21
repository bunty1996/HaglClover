/**
 * Created by ${Raman} on ${28/08/2020}.
 */

package com.example.haglandroidapp.orders;

import android.accounts.Account;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.order.Discount;
import com.clover.sdk.v3.order.Order;
import com.clover.sdk.v3.order.OrderConnector;
import com.example.haglandroidapp.Activities.DashboardActivity;
import com.example.haglandroidapp.Activities.DashboardActivity2;
import com.example.haglandroidapp.Activities.MyReceiver;
import com.example.haglandroidapp.Models.CloverDiscountModelLocal.CloverDiscountModelLocal;
import com.example.haglandroidapp.Models.DiscountPrice;
import com.example.haglandroidapp.Utils.CSPreferences;
import com.example.haglandroidapp.Utils.MyApplication;
import com.example.haglandroidapp.Utils.SharedPref;
import com.google.gson.internal.$Gson$Preconditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppliedBottleReturnAsync extends AsyncTask<Object, Void, String> {
    private static final String TAG = AppliedBottleReturnAsync.class.getSimpleName();
    public static OrderConnector orderConnector;
    private InventoryConnector inventoryConnector;
    Account mAccount;
    public static ArrayList<CloverDiscountModelLocal> selectedArraylist = new ArrayList<>();
    public static ArrayList<CloverDiscountModelLocal> selectedArraylistDeletePending;
    double cumulativeDiscount = 0.0;
    private String cumulativeDiscountText;
    private int totalDiscount;
    private int productIDDD;
    private MyReceiver receiver;
    private IntentFilter intentFilter;
    private Discount discount = new Discount();
    private int position;


    public AppliedBottleReturnAsync() {

    }


    @Override
    protected String doInBackground(Object... params) {

        receiver = new MyReceiver();
        intentFilter = new IntentFilter("com.journaldev.broadcastreceiver.SOME_ACTION");

        String order_id = (String) params[0];
        long amount;
        if (params[1] == "") {
            amount = 0;
        } else {
            amount = ((Number) params[1]).longValue();
        }
//        long amount = (Long) params[1];
//        long tax=(Long) params[2];

        Order order = null;
        String productId = null;
        try {
            order = orderConnector.getOrder(String.valueOf(params[0]));
            if (order.getLineItems() != null) {
                for (int i = 0; i < order.getLineItems().size(); i++) {
//                    System.out.println(i);
                    Log.e("23456789", String.valueOf(i));
                    productId = order.getLineItems().get(i).getItem().getId();
                }

                Log.e("Loaded order", order + "");
            }
        } catch (RemoteException | ClientException | BindingException | ServiceException e) {
            e.printStackTrace();
        }
        try {
            if (amount == 0) {

//                Log.e("zxcvbnm", "zxcvbnm111111");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.context());
                String name = preferences.getString("cashclick", "");
                String productIdApp = preferences.getString("productId", "");
//                String addDiscountType = preferences.getString("addDiscountType", "");
                String addDiscountType = CSPreferences.readString(MyApplication.context(), "addDiscountType");
                Log.e("come1", "come1");
                Log.e("addDiscountType", addDiscountType);

                String disvalue = CSPreferences.readString(MyApplication.context(), "DeleteDiscount");

                if (disvalue.equals("Single") || disvalue == "Single") {

                } else {

                }


                if (addDiscountType.equals("addDiscountTypeYES") || addDiscountType == "addDiscountTypeYES") {
                    Log.e("1234", addDiscountType);
                    CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", "");

                    for (int i = 0; i < DashboardActivity.cloverDiscountModelLocalArrayList.size(); i++) {

                        if (DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getProductId().equals(productId)) {
//                        selectedArraylist = new ArrayList<CloverDiscountModelLocal>();

                            CloverDiscountModelLocal discountModelLocal = new CloverDiscountModelLocal();
                            discountModelLocal.setFinalPrice(Float.parseFloat(String.valueOf(DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getFinalPrice())));
                            discountModelLocal.setListPrice(DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getListPrice());
                            discountModelLocal.setDiscount(DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getDiscount());
                            discountModelLocal.setProductId(String.valueOf(DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getProductId()));
                            discountModelLocal.setCashclick("CloverADD");
                            discountModelLocal.setTotalDiscountDone("");
                            discountModelLocal.setProductName(DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getProductName());
                            selectedArraylist.add(discountModelLocal);

                            if (!name.equalsIgnoreCase("")) {
                                Log.e("come2", "come2");
                                SharedPreferences preferencessd = PreferenceManager.getDefaultSharedPreferences(MyApplication.context());
                                String finaldiscountadded = String.valueOf(preferencessd.getFloat("finaldiscountadded", 0));
//                    String totalamou = preferences.getString("sum1", "");
                                String totalamou = preferences.getString("listPrice", "");
                                double discountFlat = Double.parseDouble(preferences.getString("discountFlat", ""));
                                Log.e("listPrice", DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getDiscount());

                                if (!DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getListPrice().equalsIgnoreCase("")) {
                                    DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getListPrice().equals(DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getListPrice());  /* Edit the value here*/
                                }
                                SharedPreferences we = PreferenceManager.getDefaultSharedPreferences(MyApplication.context());
                                String df = String.valueOf(preferencessd.getFloat("finaldiscountadded", 0));


                                Double disfinal = Double.valueOf(DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getFinalPrice());
                                double dtot = Double.parseDouble(DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getListPrice());
                                double findsub = disfinal / dtot;
                                Log.e("DISCOUNTMULTIPLY", String.valueOf(findsub));

                                double total_discont = findsub * 100;
                                Log.e("DIVISION", String.valueOf(total_discont));

                                long appliedDiscount = (long) Double.parseDouble(DashboardActivity.cloverDiscountModelLocalArrayList.get(i).getDiscount());
                                Log.d("FINAL CASH PAY DIS", String.valueOf(appliedDiscount));
//                                final Discount discount = new Discount();
                                String str = String.valueOf(MyApplication.finalselecteditems);
                                String selectedData = CSPreferences.readString(MyApplication.context(), "TotalDiscountValue");


                                Log.e("1234selectedArraylist", selectedArraylist.size() + "");

                                for (int j = 0; j < selectedArraylist.size(); j++) {

                                    if (!(selectedData.equals("") || selectedData == null)) {
                                        int newDiscount = Integer.parseInt(selectedArraylist.get(j).getDiscount()); // Example: new discount value
                                        //convert value into int
                                        int selectedint = Integer.parseInt(selectedData);
                                        //sum these two numbers
                                        totalDiscount = selectedint + newDiscount;

                                        cumulativeDiscount = totalDiscount;
                                        cumulativeDiscountText = String.valueOf(cumulativeDiscount); // Example: format the discount text
                                        discount.setName("Hagl Discount : ");
                                        discount.setAmount(Long.valueOf(-Math.round(Float.parseFloat(cumulativeDiscountText)) * 100));

                                        Log.e("1234", "qwer");

                                    } else {
                                      /*  CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", "");
                                        double newDiscount = Double.parseDouble(selectedArraylist.get(j).getDiscount()); // Example: new discount value
                                        cumulativeDiscount += newDiscount;
                                        cumulativeDiscountText = String.valueOf(cumulativeDiscount); // Example: format the discount text
                                        discount.setName("Hagl Discount : ");
                                        discount.setAmount(Long.valueOf(-Math.round(Float.parseFloat(cumulativeDiscountText)) * 100));
                                        totalDiscount = Integer.parseInt(selectedArraylist.get(j).getDiscount());*/
//                                    productIDDD = Integer.parseInt(selectedArraylist.get(j).getProductId());


                                        double newDiscount = Double.parseDouble(selectedArraylist.get(j).getDiscount()); // Example: new discount value
                                        cumulativeDiscount += newDiscount;
                                        cumulativeDiscountText = String.valueOf(cumulativeDiscount); // Example: format the discount text
                                        discount.setName("Hagl Discount : ");
                                        discount.setAmount(Long.valueOf(-Math.round(Float.parseFloat(cumulativeDiscountText)) * 100));
                                        totalDiscount = Integer.parseInt(selectedArraylist.get(j).getDiscount());

                                        Log.e("1234", "1234");

                                    }

                                    CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", String.valueOf(totalDiscount));
//                                CSPreferences.putString(MyApplication.context(), "productIDValue", String.valueOf(productIDDD));
                                }
                                Log.e("selectedArraylist22", selectedArraylist.size() + "");


//                                Log.e("strstrstr", str + "  123");
                                Log.e("strstrstr", str);

                                orderConnector.addDiscount(order_id, discount);
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.context());
                                prefs.edit().remove("sum1").commit();

                            } else {
                                Log.e("come3", "come3");

//                                Discount discount = new Discount();
                                DiscountPrice price = new DiscountPrice();
                                String str = String.valueOf(MyApplication.finalselecteditems);
//                    discount.setName("Total Items  in Cart :  " + str);
                                discount.setName("Hagl Discount :  ");
                                orderConnector.addDiscount(order_id, discount);
                            }
                        } else {
//                        Log.e("come4", "come4");
//
//                        Discount discount = new Discount();
//                        DiscountPrice price = new DiscountPrice();
//                        String str = String.valueOf(MyApplication.finalselecteditems);
////                    discount.setName("Total Items  in Cart :  " + str);
//                        discount.setName("Hagl Discount :  ");
//                        orderConnector.addDiscount(order_id, discount);

                            ///////////////////////////////////

                        }
                    }

                } else {
                    ///////////////Deleteing product from selection
                    Log.e("1234", addDiscountType);
                    Log.e("1234selectedArraylist11", selectedArraylist.size() + "");
                    Log.e("1234pendingList", MyApplication.pendingList.size() + "");
                    selectedArraylistDeletePending = new ArrayList<CloverDiscountModelLocal>();
                    selectedArraylistDeletePending.clear();


                    for (String id : MyApplication.pendingList) {
                        for (CloverDiscountModelLocal product : selectedArraylist) {
                            if (product.getProductId().equals(id)) {
                                CloverDiscountModelLocal discountModelLocal = new CloverDiscountModelLocal();
                                discountModelLocal.setFinalPrice(Float.parseFloat(String.valueOf(product.getFinalPrice())));
                                discountModelLocal.setListPrice(product.getListPrice());
                                discountModelLocal.setDiscount(product.getDiscount());
                                discountModelLocal.setProductId(product.getProductId());
                                discountModelLocal.setCashclick("CloverADD");
                                discountModelLocal.setTotalDiscountDone("");
                                discountModelLocal.setProductName(product.getProductName());
                                selectedArraylistDeletePending.add(discountModelLocal);
                                break;  // No need to continue searching once found
                            }
                        }
                    }

                    CSPreferences.putString(MyApplication.context(), "pendingListArraylist", String.valueOf(selectedArraylistDeletePending));
                    Log.e("pendingList2", selectedArraylistDeletePending.size() + "");


             /*       for (int n = 0; n < selectedArraylist.size(); n++) {
                        CloverDiscountModelLocal object1 = selectedArraylist.get(n);

//                        if (n == 0) {
//                            position = 0;
//                        } else {
//                            position = n;
//                        }

                        for (int position = 0; position < MyApplication.pendingList.size(); position++) {
                            if (MyApplication.pendingList.get(position).equals(object1)) {
                                CloverDiscountModelLocal discountModelLocal = new CloverDiscountModelLocal();
                                discountModelLocal.setFinalPrice(Float.parseFloat(String.valueOf(selectedArraylist.get(n).getFinalPrice())));
                                discountModelLocal.setListPrice(selectedArraylist.get(n).getListPrice());
                                discountModelLocal.setDiscount(selectedArraylist.get(n).getDiscount());
                                discountModelLocal.setProductId(selectedArraylist.get(n).getProductId());
                                discountModelLocal.setCashclick("CloverADD");
                                discountModelLocal.setTotalDiscountDone("");
                                discountModelLocal.setProductName(selectedArraylist.get(n).getProductName());
                                selectedArraylistDeletePending.add(discountModelLocal);
                                Log.e("pendingList2", "pendingList2");
                            }
                            break;
                        }
                        CSPreferences.putString(MyApplication.context(), "pendingListArraylist", String.valueOf(selectedArraylistDeletePending));
                        Log.e("pendingList2", selectedArraylistDeletePending.size() + "");
                    }*/
                    Log.e("12345pendingList", MyApplication.pendingList.size() + "");

                   /* for (int m = 0; m < selectedArraylist.size(); m++) {
                        String object1 = selectedArraylist.get(m).getProductId();
//                            for (String object2 : MyApplication.pendingList) {
                        for (int n = 0; n < MyApplication.pendingList.size(); n++) {
                            if (object1.equals(MyApplication.pendingList.get(n))) {
                                CloverDiscountModelLocal discountModelLocal = new CloverDiscountModelLocal();
                                discountModelLocal.setFinalPrice(Float.parseFloat(String.valueOf(selectedArraylist.get(m).getFinalPrice())));
                                discountModelLocal.setListPrice(selectedArraylist.get(m).getListPrice());
                                discountModelLocal.setDiscount(selectedArraylist.get(m).getDiscount());
                                discountModelLocal.setProductId(selectedArraylist.get(m).getProductId());
                                discountModelLocal.setCashclick("CloverADD");
                                discountModelLocal.setTotalDiscountDone("");
                                discountModelLocal.setProductName(selectedArraylist.get(m).getProductName());
                                selectedArraylistDeletePending.add(discountModelLocal);
                            }
                            break;
                        }
                        CSPreferences.putString(MyApplication.context(), "pendingListArraylist", String.valueOf(selectedArraylistDeletePending));
                        Log.e("pendingList2", selectedArraylistDeletePending.size() + "");
                    }*/

//                        Log.e("listDeletePending:",selectedArraylistDeletePending+"");


                    if (!name.equalsIgnoreCase("")) {
                        Log.e("come2", "come2");


                        Log.e("12345selectedArraylist", selectedArraylistDeletePending.size() + "");
                        for (int a = 0; a < selectedArraylistDeletePending.size(); a++) {
                            SharedPreferences preferencessd = PreferenceManager.getDefaultSharedPreferences(MyApplication.context());
                            String finaldiscountadded = String.valueOf(preferencessd.getFloat("finaldiscountadded", 0));
//                    String totalamou = preferences.getString("sum1", "");
                            String totalamou = preferences.getString("listPrice", "");
                            double discountFlat = Double.parseDouble(preferences.getString("discountFlat", ""));
                            Log.e("listPrice", selectedArraylistDeletePending.get(a).getDiscount());

                            if (!selectedArraylistDeletePending.get(a).getListPrice().equalsIgnoreCase("")) {
                                selectedArraylistDeletePending.get(a).getListPrice().equals(selectedArraylistDeletePending.get(a).getListPrice());  /* Edit the value here*/
                            }
                            SharedPreferences we = PreferenceManager.getDefaultSharedPreferences(MyApplication.context());
                            String df = String.valueOf(preferencessd.getFloat("finaldiscountadded", 0));

                            Double disfinal = Double.valueOf(selectedArraylistDeletePending.get(a).getFinalPrice());
                            double dtot = Double.parseDouble(selectedArraylistDeletePending.get(a).getListPrice());
                            double findsub = disfinal / dtot;
                            Log.e("DISCOUNTMULTIPLY", String.valueOf(findsub));

                            double total_discont = findsub * 100;
                            Log.e("DIVISION", String.valueOf(total_discont));

                            long appliedDiscount = (long) Double.parseDouble(selectedArraylistDeletePending.get(a).getDiscount());
                            Log.d("FINAL CASH PAY DIS", String.valueOf(appliedDiscount));
//                            final Discount discount = new Discount();
                            String str = String.valueOf(MyApplication.finalselecteditems);
                            String selectedData = CSPreferences.readString(MyApplication.context(), "SingleDiscountValue");
                            discount.clearDiscount();


                            for (int j = 0; j < selectedArraylistDeletePending.size(); j++) {

                                if (!(selectedData.equals("") || selectedData == null)) {

                                   /* int totalPrice = 0;

//                                    for (CloverDiscountModelLocal price : selectedArraylistDeletePending) {
//                                        totalPrice +=Integer.parseInt(price.getDiscount());
//                                    }

                                    int selectedint = Integer.parseInt(selectedData);

                                    totalDiscount = Integer.parseInt(1 + selectedArraylistDeletePending.get(j).getDiscount());

                                    cumulativeDiscount = totalDiscount;
                                    cumulativeDiscountText = String.valueOf(cumulativeDiscount); // Example: format the discount text
                                    discount.setName("Hagl Discount : ");
                                    discount.setAmount(Long.valueOf(-Math.round(Float.parseFloat(cumulativeDiscountText)) * 100));*/


                                    double newDiscount = Double.parseDouble(selectedArraylistDeletePending.get(j).getDiscount()); // Example: new discount value
                                    cumulativeDiscount += newDiscount;
                                    cumulativeDiscountText = String.valueOf(cumulativeDiscount); // Example: format the discount text
                                    discount.setName("Hagl Discount : ");
                                    discount.setAmount(Long.valueOf(-Math.round(Float.parseFloat(cumulativeDiscountText)) * 100));


//                                    Log.e("123456", cumulativeDiscountText * 100);
                                   /* int newDiscount = Integer.parseInt(selectedArraylistDeletePending.get(j).getDiscount()); // Example: new discount value
                                    //convert value into int
                                    int selectedint = Integer.parseInt(selectedData);
                                    //sum these two numbers

                                    totalDiscount = selectedint + newDiscount;

                                    cumulativeDiscount = totalDiscount;
                                    cumulativeDiscountText = String.valueOf(cumulativeDiscount); // Example: format the discount text
                                    discount.setName("Hagl Discount : ");
                                    discount.setAmount(Long.valueOf(-Math.round(Float.parseFloat(cumulativeDiscountText)) * 100));


*/
                                    Log.e("12345", "qwer");

                                } else {

                                    ////////////////
                                    CSPreferences.putString(MyApplication.context(), "SingleDiscountValue", "");
                                    double newDiscount = Double.parseDouble(selectedArraylistDeletePending.get(j).getDiscount()); // Example: new discount value
                                    cumulativeDiscount += newDiscount;
                                    cumulativeDiscountText = String.valueOf(cumulativeDiscount); // Example: format the discount text
                                    discount.setName("Hagl Discount : ");
                                    discount.setAmount(Long.valueOf(-Math.round(Float.parseFloat(cumulativeDiscountText)) * 100));
                                    totalDiscount = Integer.parseInt(selectedArraylistDeletePending.get(j).getDiscount());
//                                    productIDDD = Integer.parseInt(selectedArraylist.get(j).getProductId());

                                    Log.e("12345", "12345");

                                }
                                Log.e("selectedArraylist2222", selectedArraylistDeletePending.size() + "");

                                CSPreferences.putString(MyApplication.context(), "SingleDiscountValue", String.valueOf(totalDiscount));
//                                CSPreferences.putString(MyApplication.context(), "productIDValue", String.valueOf(productIDDD));
                            }




                            Log.e("strstrstr", str + "  123");
                            Log.e("strstrstr", str);

                            orderConnector.addDiscount(order_id, discount);
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.context());
                            prefs.edit().remove("sum1").commit();
                            break;
                        }

                    } else {
                        Log.e("come33", "come33");

                        AppliedBottleReturnAsync.selectedArraylistDeletePending.clear();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.context());
                        prefs.edit().remove("cashclick").commit();
                        preferences.edit().clear().commit();

                        Log.e("DeleteItems", "Multiple");
                        CSPreferences.putString(MyApplication.context(), "addDiscountType", "addDiscountTypeNO");
                        CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", "");
                        CSPreferences.putString(MyApplication.context(), "DeleteDiscount", "Multiple");
                        CSPreferences.clearAll(MyApplication.context());

                        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
                        databaseOpenHelper.removeLastBackupTableData();

//                        orderConnector.addDiscount(order_id, discount);
                    }
                }

            } else {
                Log.e("come5", "come5");

//                Discount discount = new Discount();
                DiscountPrice price = new DiscountPrice();
                String str = String.valueOf(MyApplication.finalselecteditems);
//                discount.setName("Total Items  in Cart :  " + str);
                discount.setName("Hagl Discount :  ");

                orderConnector.addDiscount(order_id, discount);
            }
        } catch (
                RemoteException e) {
            e.printStackTrace();
        } catch (
                ClientException e) {
            e.printStackTrace();
        } catch (
                ServiceException e) {
            e.printStackTrace();
        } catch (
                BindingException e) {
            e.printStackTrace();
        } catch (NumberFormatException
                e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mAccount = CloverAccount.getAccount(MyApplication.context());
        orderConnector = new OrderConnector(MyApplication.context(), mAccount, null);
        inventoryConnector = new InventoryConnector(MyApplication.context(), mAccount, null);
        inventoryConnector.connect();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("getReturnResult>>>>>>", s + "");

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getAction();
            Toast.makeText(MyApplication.context(), message, Toast.LENGTH_LONG).show();
        }
    };


}

