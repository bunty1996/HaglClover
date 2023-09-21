package com.example.haglandroidapp.Utils;


import android.accounts.Account;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.clover.sdk.util.AuthTask;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.util.CloverAuth;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v1.app.AppNotification;
import com.clover.sdk.v3.order.Discount;
import com.clover.sdk.v3.order.Order;
import com.example.haglandroidapp.Activities.DashboardActivity;
import com.example.haglandroidapp.Interface.GetAgeVerifiactionLineItemName;
import com.example.haglandroidapp.Interface.GetOrderDetailsListener;
import com.example.haglandroidapp.Models.CloverDiscountModelLocal.CloverDiscountModelLocal;
import com.example.haglandroidapp.Models.DeleteLineItemPojo;
import com.example.haglandroidapp.Models.DiscountPrice;
import com.example.haglandroidapp.Models.GlobalFunctions;
import com.example.haglandroidapp.Models.ThreeData;
import com.example.haglandroidapp.Models.itemsPojo;
import com.example.haglandroidapp.orders.AppliedBottleReturnAsync;
import com.example.haglandroidapp.orders.DatabaseOpenHelper;
import com.example.haglandroidapp.orders.DeleteAppliedDiscount;
import com.example.haglandroidapp.orders.OrderTaskAsync;
import com.example.haglandroidapp.serverhandler.JsonRequestHandler;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyApplication extends MultiDexApplication implements GetOrderDetailsListener {

    private static Context context;
    private static MyApplication mInstance;
    public static long finalselecteditems;
    private DatabaseOpenHelper databaseOpenHelper;
    long HOD = 100;
    public static final String TAG = MyApplication.class.getSimpleName();
    double lineitems_price = 0;
    double bottleReturnProductPrice = 0;
    String bottleProductName = null;
    List<DiscountPrice> findtotal = new ArrayList<>();
    long bottleReturnPrice = 0;
    public static boolean isVerify = false;

    public static GetAgeVerifiactionLineItemName getAgeVerifiactionLineItemName;
    private RequestQueue mRequestQueue;
    Gson gson;

    public static ArrayList<String> pendingList = new ArrayList<>();

    public void onCreate() {
        super.onCreate();

        MultiDex.install(getApplicationContext());
        mInstance = this;

        new WebServices();
        new WebServices2();

        SharedPref.init(mInstance);
        MyApplication.context = getApplicationContext();
        databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
        OrderTaskAsync.setChangeListener(this::callback);
        gson = new Gson();
    }

    public static Context context() {
        return MyApplication.context;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    static JsonRequestHandler jsonRequestHandler;

    public static synchronized JsonRequestHandler getJsonRequest() {
        if (jsonRequestHandler == null) {
            jsonRequestHandler = new JsonRequestHandler();
        }
        return jsonRequestHandler;
    }

//    public JsonRequestHandler getServiceRequestInstance() {
//        if (requestHandler == null) {
//            requestHandler = new JsonRequestHandler();
//        }
//        return requestHandler;
//    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    // Before caculations remove all prevoius discount
    private void deleteAllDiscount(Order result, String orderid, String whicTask, List<String> lv_lineitems) {
        List<String> lv_discounts = new ArrayList<>();
        if (result.getLineItems() != null) {
            if (whicTask.equals("Remove")) {
                List<DeleteLineItemPojo> lv_delete = SharedPref.getSavedList(Commons.ORDER_RESULT, "");
                List<itemsPojo> lv_getSqlProducts = getCloverOrderRemoveRepetationOfProducts(result);
                Log.e("getDiscounts", lv_getSqlProducts + "");
                List<DeleteLineItemPojo> lv_list = new ArrayList<>();
                for (int i = 0; i < result.getLineItems().size(); i++) {
                    DeleteLineItemPojo lineItemPojo = new DeleteLineItemPojo();
                    if (result.getLineItems().get(i).getId().equals(lv_lineitems.get(0))) {
//                    lineItemPojo.setClover_id(result.getLineItems().get(i).getItem().getId());
                        lineItemPojo.setLine_id(result.getLineItems().get(i).getId());
                        lineItemPojo.setItem_name(result.getLineItems().get(i).getName());
                        lv_list.add(lineItemPojo);

                    } else {
                        /*lineItemPojo.setClover_id(result.getLineItems().get(i).getItem().getId());*/
                        lineItemPojo.setLine_id(result.getLineItems().get(i).getId());
                        lineItemPojo.setItem_name(result.getLineItems().get(i).getName());
                        lv_list.add(lineItemPojo);

                    }

                }
            }

            if (result.getDiscounts() != null) {
                //  Log.d("getDiscounts", result.getDiscounts() + "");
                for (int i = 0; i < result.getDiscounts().size(); i++) {
                    lv_discounts.add(result.getDiscounts().get(i).getId());
                    lv_discounts.add(String.valueOf(result.getDiscounts().get(i).getDiscount() + "HDBF"));
                }
                new DeleteAppliedDiscount().execute(orderid, lv_discounts);

            }


            if (result.getLineItems() != null) {
                List<itemsPojo> lv_getSqlProducts = getCloverOrderRemoveRepetationOfProducts(result);
//          Log.d("getDiscounts", lv_getSqlProducts + "");
                List<DeleteLineItemPojo> lv_list = new ArrayList<>();
                for (int i = 0; i < result.getLineItems().size(); i++) {
                    DeleteLineItemPojo lineItemPojo = new DeleteLineItemPojo();
                    if (result.getLineItems().get(i).getId().equals(lv_lineitems.get(0))) {
//                    lineItemPojo.setClover_id(result.getLineItems().get(i).getItem().getId());
                        lineItemPojo.setLine_id(result.getLineItems().get(i).getId());
                        lineItemPojo.setItem_name(result.getLineItems().get(i).getName());
                        lv_list.add(lineItemPojo);

                    } else {
                        /*lineItemPojo.setClover_id(result.getLineItems().get(i).getItem().getId());*/
                        lineItemPojo.setLine_id(result.getLineItems().get(i).getId());
                        lineItemPojo.setItem_name(result.getLineItems().get(i).getName());
                        lv_list.add(lineItemPojo);

                    }

                }


                for (int i = 0; i < lv_getSqlProducts.size(); i++) {

                    lv_list.get(i).setCount(Integer.parseInt(lv_getSqlProducts.get(i).getClover_quantity()));
                    Log.d("getDiscounts", lv_list.get(i).getCount() + "");
                }

                Set<DeleteLineItemPojo> set = new HashSet<DeleteLineItemPojo>(lv_list);
                lv_list.clear();
                lv_list.addAll(set);
                String strObject = gson.toJson(lv_list);
                SharedPref.saveArrayLIstData(Commons.ORDER_RESULT, strObject);
            }

        }
    }

    @Override
    public void callback(Order result, List<String> lv_lineitems, List<String> lv_discounts, String order_id, String whichTask) {
        double discount = 0;
        //Log.d("result>>>>", String.valueOf(result));
        if (result != null) {
            deleteAllDiscount(result, order_id, whichTask, lv_lineitems);
            if (whichTask.equals(Commons.DELETE_DISCOUNTAPPLICATION)) {
                if (result.getLineItems() != null) {
                    for (int i = 0; i < result.getLineItems().size(); i++) {
                        Log.d("result.getLineItems()", result.getLineItems().get(i).getId());
                        if (result.getLineItems().get(i).getId().equals(lv_lineitems.get(0))) {
                            if (result.getLineItems().get(i).getDiscounts() != null) {
                                for (int j = 0; j < result.getLineItems().get(i).getDiscounts().size(); j++) {
                                    if (result.getLineItems().get(i).getDiscounts().get(j).getPercentage() == null) {
                                        discount = discount + GlobalFunctions.removeSpecialChars(result.getLineItems().get(i).getDiscounts().get(j).getAmount());
                                    } else {
                                        discount = discount + result.getLineItems().get(i).getDiscounts().get(j).getPercentage();
                                    }
                                }
                                if (result.getLineItems().get(i).getDiscounts().get(0).getPercentage() == null) {
                                    getCloverOrderProductPrice(discount, result.getLineItems().get(i).getPrice(),
                                            result.getLineItems().get(i).getItem().getId(), Commons.DOLLAR_AMOUNT, lv_lineitems, lv_discounts, order_id, i);
                                } else {
                                    getCloverOrderProductPrice(discount,
                                            result.getLineItems().get(i).getPrice(), result.getLineItems().get(i).getItem().getId(),
                                            Commons.PER, lv_lineitems, lv_discounts, order_id, i);
                                }

                            }
                        }
                    }
                }
            } else {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.context);
                SharedPreferences.Editor editor = preferences.edit();
                try {
                    editor.putString("result12", result.getJSONObject().get("total").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.apply();
                itemsPojo itemsPojo = new itemsPojo();
                Log.d("result12", String.valueOf(itemsPojo.getTotal_price()));

                if (result.getTotal() == 0) {

                    new OrderTaskAsync().
                            execute(order_id, lv_lineitems, lv_lineitems, "Remove");

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    prefs.edit().remove("cashclick").commit();

                    DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
                    databaseOpenHelper.removeLastBackupTableData();
                    // Storing data into SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
// Creating an Editor object to edit(write to the file)
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
// Storing the key and its value as the data fetched from edittext
                    myEdit.putString("createdorderid", order_id);
                    myEdit.commit();

                    CSPreferences.putString(MyApplication.context(), "addDiscountType", "addDiscountTypeNO");
                    CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", "");
                    CSPreferences.putString(MyApplication.context(), "DeleteDiscount", "Multiple");
                    CSPreferences.putString(MyApplication.context(), "SingleDiscountValue", "");

                    CSPreferences.clearAll(MyApplication.context());

//                    final Discount discount2 = new Discount();
//                    CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", "");
//                    discount2.setName("Hagl Discount : ");
//                    discount2.setAmount(Long.valueOf(0) * 100);
////                    totalDiscount = Integer.parseInt(selectedArraylist.get(j).getDiscount());
//                    Log.e("DeleteItems", "12345678trewqwertyu");


                } else {

                    int totalAmount = 0;
                    for (int i = 0; i < result.getLineItems().size(); i++) {
                        String txN_AMT = null;
                        try {
                            JSONObject jsonRootObject = new JSONObject(String.valueOf(result));

                            //Get the instance of JSONArray that contains JSONObjects
                            JSONArray jsonArray = jsonRootObject.optJSONArray("lineItems");
                            //  JSONArray jsonArray1 = jsonArray.optJSONArray("lineItems");

                            txN_AMT = result.getJSONObject().get("lineItems").toString();
                            totalAmount = Integer.parseInt(totalAmount + txN_AMT);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //outside of the loop, you now have the total amount
                //   Toast.makeText(MyApplication.this, "" + totalAmount, Toast.LENGTH_SHORT).show();
                if (whichTask.toLowerCase().equals(Commons.ADD) || whichTask.toLowerCase().equals(Commons.REMOVE)) {
                    if (result.getLineItems() != null) {
                        boolean isDiscountExist = false;
                        List<DiscountPrice> discount_list = new ArrayList<>();
                        double total_discount = 0;
                        int sql_qunatity = 0;
                        String dis_type = null;
                        // Implementing bottle return functionality acc to product name here
                        Map<String, Integer> lv_productsNameRepetation = cloverOrderRemoveRepetationOfProductsNameForReturn(result);
                        // bottle Return product name and age verification product name
                        getLineItemForBottleReturnsAndAgeVerification(lv_productsNameRepetation, order_id, result);
                        List<itemsPojo> lv_checkDiscountApplicable = new ArrayList<>();
                        List<itemsPojo> lv_getSqlProducts = getCloverOrderRemoveRepetationOfProducts(result);
                        for (int i = 0; i < lv_getSqlProducts.size(); i++) {
                            if (lv_getSqlProducts.get(i).getRetail_price() != null) {
                                double retail_price = Double.parseDouble(lv_getSqlProducts.get(i).getRetail_price());
                                double state_min = Double.parseDouble(lv_getSqlProducts.get(i).getState_min());
                                double clover_qunatity = Double.parseDouble(lv_getSqlProducts.get(i).getClover_quantity());
                                double dis_price = Double.parseDouble(lv_getSqlProducts.get(i).getDiscount_price());
                                if (lv_getSqlProducts.get(i).getPer_type().equals(Commons.PERCENTAGE)) {
                                    double total_retail = retail_price * clover_qunatity;
                                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                                    SharedPreferences.Editor edi = preferences.edit();
                                    edi.putString("result12", String.valueOf(total_retail));
                                    edi.apply();
                                    double total_statemin = state_min * clover_qunatity;
                                    double dis_subtractamount = total_retail * dis_price / 100;
                                    double total_price = total_retail - dis_subtractamount;
                                    itemsPojo itemsPojos = lv_getSqlProducts.get(i);
                                    if (total_price > total_statemin) {
                                        lv_checkDiscountApplicable.add(itemsPojos);
                                    }
                                } else { // Check state min for flat discount here
                                    double total_price = retail_price - dis_price;
                                    itemsPojo itemsPojos = lv_getSqlProducts.get(i);
                                    if (total_price > state_min) {
                                        lv_checkDiscountApplicable.add(itemsPojos);
                                    }
                                }
                            }
                        }
                        // if we have more than products in a discount

                        List<itemsPojo> lv_finalproducts = new ArrayList<>();
                        for (int i = 0; i < lv_checkDiscountApplicable.size(); i++) {
                            if (lv_finalproducts.size() > 0) {
                                itemsPojo items = lv_checkDiscountApplicable.get(i);
                                for (int j = 0; j < lv_finalproducts.size(); j++) {
                                    Log.d("lv_raman>>>>", lv_finalproducts + "");
                                    // Check for we have alraedy added discount for that product by using primary id
                                    // If already added
                                    if (items.getPrimary_id().equals(lv_finalproducts.get(j).getPrimary_id())) {
                                        if (items.getClover_id().equals(lv_finalproducts.get(j).getClover_id())) {
                                        } else {
                                            // firstly get older values and add-up new
                                            long lat_qunatity = Long.parseLong(items.getClover_quantity());
                                            long exist_quantity = Long.parseLong(lv_finalproducts.get(j).getClover_quantity());
                                            double lat_price = items.getTotal_price() * lat_qunatity;
                                            double last_price = lv_finalproducts.get(j).getTotal_price();
                                            double total_price = lat_price + last_price;
                                            long qunatity = exist_quantity + lat_qunatity;
                                            boolean isDiscountApply = chkDiscountApplicableOnSameParentIdProducts(items);
                                            if (isDiscountApply) {
                                                items.setClover_quantity(qunatity + "");
                                                items.setTotal_price(total_price);
                                                lv_finalproducts.set(j, items);
                                            } else {
                                                lv_finalproducts.get(j).setClover_quantity(qunatity + "");
                                            }
                                        }
                                    } else {
                                        // else products will be added in lv_finalproducts
                                        boolean isgotSameId = false;
                                        for (int k = 0; k < lv_finalproducts.size(); k++) {
                                            if (items.getPrimary_id().equals(lv_finalproducts.get(k).getPrimary_id())) {
                                                isgotSameId = true;
                                                break;
                                            } else {
                                            }
                                        }
                                        if (isgotSameId) {
                                        } else {
                                            int qunt = Integer.parseInt(items.getClover_quantity());
                                            double total = items.getTotal_price() * qunt;
                                            itemsPojo items2 = lv_checkDiscountApplicable.get(i);
                                            items2.setTotal_price(total);
                                            if (j == lv_finalproducts.size() - 1) {
                                                lv_finalproducts.add(items2);
                                                break;
                                            } else {
                                                lv_finalproducts.add(items2);
                                            }
                                        }
                                    }
                                }
                            } else {
                                itemsPojo items = lv_checkDiscountApplicable.get(i);
                                double total = items.getTotal_price() * Integer.parseInt(items.getClover_quantity());
                                items.setTotal_price(total);
                                boolean isDiscountApply = chkDiscountApplicableOnSameParentIdProducts(items);
                                if (isDiscountApply)
                                    lv_finalproducts.add(items);
                                Log.d("start>>", lv_finalproducts + "");
                            }
                        }

                        if (lv_finalproducts.size() != 0) {
                            for (int i = 0; i < lv_finalproducts.size(); i++) {
                                itemsPojo item = lv_finalproducts.get(i);
                                Log.d("item.getClover_id()>>", item + "");
                                lv_discounts = getLineItemPriceAndDiscountIdClover(item.getClover_id(), result);
                                if (item.getDiscount_quantity() != null) {
                                    sql_qunatity = Integer.parseInt(item.getDiscount_quantity());
                                    if (lv_discounts == null) {
                                        isDiscountExist = false;
                                        // Discount should be applicable
                                    } else {
                                        isDiscountExist = true;
                                    }
                                    long limit = 0;
                                    if (item.getLimit() == "_") {
                                    } else if (item.getLimit().equals("")) {
                                    } else {
                                        limit = Long.parseLong(item.getLimit());
                                    }
                                    Log.d("sql_qunatity", sql_qunatity + "");
                                    long clover_qunatity = Long.parseLong(item.getClover_quantity());
                                    if (item.getPer_type().equals(Commons.PERCENTAGE)) {
                                        if (limit == 0) {
                                            Log.d("sql_qunatity11", sql_qunatity + "");
                                            if (clover_qunatity % sql_qunatity == 0) {
                                                Log.d("sql_qunatity111", sql_qunatity + "");
                                                double new_price = item.getTotal_price() / HOD;
                                                total_discount = new_price * Double.parseDouble(item.getDiscount_price()) / (double) 100;
                                                // total_discount = total_discount / sql_qunatity;
                                                DiscountPrice discountPrice = new DiscountPrice();
                                                discountPrice.setAppleddiscount(total_discount);
                                                discountPrice.setDiscount_price(item.getDiscount_price());
                                                discountPrice.setQunatity(sql_qunatity);
                                                discountPrice.setDiscount_name(item.getDiscount_name());
                                                discountPrice.setCloverItemsCount(clover_qunatity);
                                                discountPrice.setDiscount_type(item.getPer_type());
                                                discountPrice.setAction_type(Commons.ADD);
                                                discount_list.add(discountPrice);
                                                item.setDiscount_quantity(sql_qunatity + "");
                                                total_discount = 0;
                                                dis_type = Commons.PER;
                                            } else {
                                                if (clover_qunatity >= sql_qunatity) {
                                                    Log.d("sql_qunatity3333", sql_qunatity + "");
                                                    double new_price = item.getTotal_price() / HOD;
                                                    total_discount = new_price * Double.parseDouble(item.getDiscount_price()) / (double) 100;
                                                    //start
                                                    DiscountPrice discountPrice = new DiscountPrice();
                                                    discountPrice.setAppleddiscount(total_discount);
                                                    discountPrice.setDiscount_price(item.getDiscount_price());
                                                    discountPrice.setQunatity(sql_qunatity);
                                                    discountPrice.setDiscount_name(item.getDiscount_name());
                                                    discountPrice.setCloverItemsCount(clover_qunatity);
                                                    discountPrice.setDiscount_type(item.getPer_type());
                                                    discountPrice.setAction_type(Commons.ADD);
                                                    discount_list.add(discountPrice);
                                                    item.setDiscount_quantity(sql_qunatity + "");
                                                    total_discount = 0;
                                                    dis_type = Commons.PER;
                                                   /* Log.d("sql_qunatity3333", sql_qunatity + "");
                                                    DiscountPrice discountPrice = new DiscountPrice();
                                                    discountPrice.setDiscount_name(item.getDiscount_name());
                                                    discountPrice.setDiscount_price(item.getDiscount_price());
                                                    discountPrice.setAction_type(Commons.LOCAL_DISCOUNT);
                                                    //discount_list = new ArrayList<>();
                                                    discount_list.add(discountPrice);
                                                    new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
                                                            lv_discounts, Commons.LOCAL_DISCOUNT, discount_list,
                                                            Commons.PERCENTAGE, 2, isDiscountExist, total_discount);*/
                                                }
                                            }
                                        } else {
                                            Log.d("sql_qunatity22", sql_qunatity + "");
                                            if (clover_qunatity % sql_qunatity == 0 && clover_qunatity <= limit) {
                                                double new_price = item.getTotal_price() / HOD;
                                                total_discount = new_price * Double.parseDouble(item.getDiscount_price()) / (double) 100;

                                                // total_discount = total_discount / sql_qunatity;
                                                DiscountPrice discountPrice = new DiscountPrice();
                                                Log.d("total_discount>", total_discount + "");
                                                discountPrice.setAppleddiscount(total_discount);
                                                discountPrice.setDiscount_price(item.getDiscount_price());
                                                discountPrice.setQunatity(sql_qunatity);
                                                sql_qunatity = sql_qunatity + sql_qunatity;
                                                item.setDiscount_quantity(sql_qunatity + "");
                                                discountPrice.setDiscount_name(item.getDiscount_name());
                                                discountPrice.setCloverItemsCount(clover_qunatity);
                                                discountPrice.setDiscount_type(item.getPer_type());
                                                discountPrice.setAction_type(Commons.ADD);
                                                discount_list.add(discountPrice);
                                                total_discount = 0;
                                                dis_type = Commons.PER;
                                            } else { // foe showing last account
                                                if (clover_qunatity >= sql_qunatity) {
                                                    Log.d("sql_qunatity3333", sql_qunatity + "");
                                                    DiscountPrice discountPrice = new DiscountPrice();
                                                    discountPrice.setDiscount_name(item.getDiscount_name());
                                                    discountPrice.setDiscount_price(item.getDiscount_price());
                                                    discountPrice.setAction_type(Commons.LOCAL_DISCOUNT);
                                                    //discount_list = new ArrayList<>();
                                                    discount_list.add(discountPrice);
                                                    new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
                                                            lv_discounts, Commons.LOCAL_DISCOUNT, discount_list,
                                                            Commons.PERCENTAGE, 2, isDiscountExist, total_discount);
                                                }
                                            }

                                        }
                                    } else {
                                        dis_type = Commons.DOLLAR_AMOUNT;
                                        Log.d("limuit1", limit + "");
                                        if (limit == 0) {
                                            Log.d("limuit11", limit + "");
                                            if (clover_qunatity % sql_qunatity == 0) {
                                                double flat_discount = 0;
                                                Log.d("limuit111", limit + "");
                                                // Get how much discount u want to apply,divide by 100, multiiply with quantity
                                                // then send discount price to clover
                                                flat_discount = Double.parseDouble(item.getDiscount_price());
                                                total_discount = flat_discount * clover_qunatity / sql_qunatity;
                                                // total_discount = lastPrice;
                                                DiscountPrice discountPrice = new DiscountPrice();
                                                discountPrice.setAppleddiscount(total_discount);
                                                discountPrice.setDiscount_name(item.getDiscount_name());
                                                discountPrice.setAction_type(Commons.ADD);
                                                discountPrice.setDiscount_price(item.getDiscount_price());
                                                discountPrice.setQunatity(sql_qunatity);
                                                discountPrice.setCloverItemsCount(clover_qunatity);
                                                discountPrice.setDiscount_type(item.getPer_type());
                                                discount_list.add(discountPrice);
                                                Log.d("discount_list", flat_discount + "");
                                            } else {
                                                DiscountPrice discountPrice = new DiscountPrice();
                                                discountPrice.setDiscount_name(item.getDiscount_name());
                                                discountPrice.setDiscount_price(item.getDiscount_price());
                                                discountPrice.setAction_type(Commons.LOCAL_DISCOUNT);
                                                //discount_list = new ArrayList<>();
                                                discount_list.add(discountPrice);
                                                if (clover_qunatity >= sql_qunatity) {
                                                    total_discount = SharedPref.getDiscountPrice(Commons.DISCOUNT_PRICE, 0);
                                                    new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
                                                            lv_discounts, Commons.LOCAL_DISCOUNT, discount_list,
                                                            Commons.DOLLAR_AMOUNT, 2, isDiscountExist, total_discount);
                                                }
                                            }
                                        } else {
                                            Log.d("limuit1122", limit + "");
                                            if (clover_qunatity % sql_qunatity == 0 && clover_qunatity <= limit) {
                                                double flat_discount = 0;
                                                Log.d("limuit33", limit + "");
                                                // Get how much discount u want to apply,divide by 100, multiiply with quantity
                                                // then send discount price to clover
                                                flat_discount = Double.parseDouble(item.getDiscount_price());
                                                total_discount = flat_discount * clover_qunatity / sql_qunatity;
                                                DiscountPrice discountPrice = new DiscountPrice();
                                                discountPrice.setAppleddiscount(total_discount);
                                                discountPrice.setDiscount_name(item.getDiscount_name());
                                                discountPrice.setAction_type(Commons.ADD);
                                                discountPrice.setDiscount_price(item.getDiscount_price());
                                                discountPrice.setQunatity(sql_qunatity);
                                                discountPrice.setCloverItemsCount(clover_qunatity);
                                                discountPrice.setDiscount_type(item.getPer_type());
                                                discount_list.add(discountPrice);
                                                Log.d("discount_list2", discount_list + "");

                                            } else {
                                                Log.d("discount_list3", "flat_discount" + "");
                                                DiscountPrice discountPrice = new DiscountPrice();
                                                discountPrice.setDiscount_name(item.getDiscount_name());
                                                discountPrice.setDiscount_price(item.getDiscount_price());
                                                discountPrice.setAction_type(Commons.LOCAL_DISCOUNT);
                                                // discount_list = new ArrayList<>();
                                                discount_list.add(discountPrice);
                                                if (clover_qunatity >= sql_qunatity) {
                                                    total_discount = SharedPref.getDiscountPrice(Commons.DISCOUNT_PRICE, 0);
                                                    new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
                                                            lv_discounts, Commons.LOCAL_DISCOUNT, discount_list,
                                                            Commons.DOLLAR_AMOUNT, 2, isDiscountExist, total_discount);
                                                }

                                            }

                                        }

                                    }

                                }
                            }
                            if (whichTask.equals(Commons.REMOVE)) {
                                if (dis_type != null) {
                                    if (dis_type.equals(Commons.PER)) {
                                        Log.e("perType", dis_type);
                                        new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
                                                lv_discounts, Commons.REMOVE, discount_list,
                                                Commons.PERCENTAGE, 2, isDiscountExist, 0.0);
                                    } else {
                                        Log.e("perTyp22Remove", dis_type);
                                        new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
                                                lv_discounts, Commons.REMOVE, discount_list,
                                                Commons.DOLLAR_AMOUNT, 2, isDiscountExist, 0.0);
                                    }
                                }
                            }
                            if (whichTask.equals(Commons.ADD)) {
                                if (dis_type != null) {
                                    if (dis_type.equals(Commons.PER)) {
                                        Log.e("perType", dis_type);
                                        new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
                                                lv_discounts, Commons.ADD, discount_list,
                                                Commons.PERCENTAGE, 2, isDiscountExist, 0.0);
                                    } else {
                                        Log.e("perTyp22Add", dis_type);
                                        new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
                                                lv_discounts, Commons.ADD, discount_list,
                                                Commons.DOLLAR_AMOUNT, 2, isDiscountExist, 0.0);
                                    }
                                }
                            } else {
                                new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
                                        lv_discounts, Commons.REMOVE, discount_list,
                                        Commons.PERCENTAGE, 2, isDiscountExist, 0.0);
                            }

                        }
                    }

                }
            }
        } else {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.context);
            SharedPreferences.Editor editor = preferences.edit();
            try {
                editor.putString("result12", result.getJSONObject().get("total").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            editor.apply();
            itemsPojo itemsPojo = new itemsPojo();
            Log.d("result12", String.valueOf(itemsPojo.getTotal_price()));

            if (result.getTotal() == 0) {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                prefs.edit().remove("cashclick").commit();

                DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
                databaseOpenHelper.removeLastBackupTableData();

                // Storing data into SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                myEdit.putString("createdorderid", order_id);
                myEdit.commit();

                Log.e("DeleteItems", "Multiple");
                CSPreferences.putString(MyApplication.context(), "addDiscountType", "addDiscountTypeNO");
                CSPreferences.putString(MyApplication.context(), "TotalDiscountValue", "");
                CSPreferences.putString(MyApplication.context(), "DeleteDiscount", "Multiple");
                CSPreferences.putString(MyApplication.context(), "SingleDiscountValue", "");

                CSPreferences.clearAll(MyApplication.context());


            }
        }

    }

    private boolean chkDiscountApplicableOnSameParentIdProducts(itemsPojo items) {
        boolean isDiscount;
        double discount_price = Double.parseDouble(items.getDiscount_price());
        double state_min = Double.parseDouble(items.getState_min());
        double gettotql = Double.parseDouble(String.valueOf(items.getTotal_price()));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("result12", String.valueOf(gettotql));
        editor.apply();
        double retail_price = Double.parseDouble(items.getRetail_price());
        double getdiscount = retail_price * discount_price / 100;
        double total_discount = retail_price - getdiscount;
        if (total_discount > state_min) {
            Log.e("chkDiscount", "discountApplicable" + total_discount + "........" + state_min);
            isDiscount = true;
        } else {
            Log.e("chkDiscount", "NotdiscountApplicable" + total_discount + "........" + state_min);
            isDiscount = false;
        }
        return isDiscount;
    }

    // Apply bottle return functionality
    private void getLineItemForBottleReturnsAndAgeVerification(Map<String, Integer> repetitionsItemsList, String order_id, Order result) {
        int temp = 0;
        for (Map.Entry<String, Integer> entry : repetitionsItemsList.entrySet()) {
            bottleProductName = entry.getKey();
            int lastValue = entry.getValue();
            DiscountPrice disprice = new DiscountPrice();
            temp = temp + lastValue;
            Log.e("finalstock", String.valueOf(temp));
            finalselecteditems = temp;
            disprice.setDiscount_name("Surcharge");
            disprice.settotalstock(finalselecteditems);
            entry.getValue();

            findtotal.add(disprice);
            if (bottleProductName.equals(Commons.BOTTLERETURN_PRODUCT)) { //Bottle Return - Single
                // Get clover price
                getLineItemNameWithPriceDiscountListFromClover(bottleProductName, result);
                Log.d("price", bottleReturnProductPrice + "");
                long price = (long) bottleReturnProductPrice;
                long getPrice = price * lastValue;
                bottleReturnPrice = bottleReturnPrice + getPrice;
                // Log.d("finalPrice", bottleReturnPrice + ">>>>>>" + bottleProductName);
                isVerify = false;
                // on triggering on lineiTemAdded we get only orderid and lineitem id so for name we are using this method
            } else if (bottleProductName.equals("IsVerify")) {
                // to register local receiver
                isVerify = true;
                Log.e("isVerifyChcek", isVerify + "");
                getAgeVerifiactionLineItemName.setAgeVerificationFired();
            } else {
                Log.e("isVerifyChcek", isVerify + "");

                isVerify = false;
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new AppliedBottleReturnAsync().execute(order_id, bottleReturnPrice);

                //Do something after 100ms
            }
        }, 100);
    }

    //From Clover Order products remove repetitions for bottle return accoarding to product name
    private Map<String, Integer> cloverOrderRemoveRepetationOfProductsNameForReturn(Order result) {
        Map<String, Integer> productNameRepetitionList = new LinkedHashMap<>();
        for (int i = 0; i < result.getLineItems().size(); i++) {
            String itemname = result.getLineItems().get(i).getName();
            if (productNameRepetitionList.containsKey(itemname)) {
                productNameRepetitionList.put(itemname, productNameRepetitionList.get(itemname) + 1);
            } else {
                productNameRepetitionList.put(itemname, 1);
            }
        }
        return productNameRepetitionList;
    }

    // Get repetitions free list from clover orders
    private List<itemsPojo> getCloverOrderRemoveRepetationOfProducts(Order result) {
        List<itemsPojo> lv_sql_products = new ArrayList<>();
        Map<String, ThreeData> repetitionsItemsList = new LinkedHashMap<>();
        Map<String, Integer> productNameRepetitionList = new LinkedHashMap<>();

        pendingList.clear();
        for (int i = 0; i < result.getLineItems().size(); i++) {
//                        selectedArraylist = new ArrayList<CloverDiscountModelLocal>();

//            CloverDiscountModelLocal discountModelLocal = new CloverDiscountModelLocal();
//            discountModelLocal.setFinalPrice(Float.parseFloat(result.getLineItems()));
//            discountModelLocal.setListPrice(selectedArraylist.get(m).getListPrice());
//            discountModelLocal.setDiscount(selectedArraylist.get(m).getDiscount());
//            discountModelLocal.setProductId(selectedArraylist.get(m).getProductId());
//            discountModelLocal.setCashclick("CloverADD");
//            discountModelLocal.setTotalDiscountDone("");
//            discountModelLocal.setProductName(selectedArraylist.get(m).getProductName());
//            pendingList.add(discountModelLocal);

            pendingList.add(result.getLineItems().get(i).getItem().getId());

        }

        CSPreferences.putString(MyApplication.context(), "pendingListArraylist", String.valueOf(pendingList));
        Log.e("pendingList1", pendingList + "");


        int sum1 = 0;

        for (int i = 0; i < result.getLineItems().size(); i++) {
            if (result.getLineItems().get(i).getItem() != null) {
                String lineitems = result.getLineItems().get(i).getItem().getId();
                double price = result.getLineItems().get(i).getPrice();
                String itemname = result.getLineItems().get(i).getName();
                sum1 += price;
                Log.e("ADDITIONTOTAL", String.valueOf(price));
                Log.e("ADDITIONTOTAL", String.valueOf(result));

                if (itemname.equals("CASH PAY")) {


                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("cashclick","Harneet");
                    editor.putString("sum1", String.valueOf(sum1));

                    editor.apply();

                    CSPreferences.putString(MyApplication.context(),"Status","CASHPAY");

                    CSPreferences.putString(MyApplication.context(),"sum1", String.valueOf(sum1));
                    Log.e("sum11234", sum1 + "");

                }

                if (repetitionsItemsList.containsKey(lineitems)) {
                    repetitionsItemsList.put(lineitems, new ThreeData(lineitems, price, productNameRepetitionList.get(itemname) + 1));
                    productNameRepetitionList.put(itemname, productNameRepetitionList.get(itemname) + 1);
                } else {
                    repetitionsItemsList.put(lineitems, new ThreeData(lineitems, price, 1));
                    productNameRepetitionList.put(itemname, 1);
                }
            }
        }

        // After removing repetitions get data from map in popjos list //lv_sql_products
        for (Map.Entry<String, ThreeData> entry : repetitionsItemsList.entrySet()) {
            ThreeData threeData = entry.getValue();
            // Removing and adding discount info in list
            Log.d("getIdsdsdssdsd>>", threeData + "");


            itemsPojo item = databaseOpenHelper.getOneProductItemAccToProductId(threeData.getLineItems(), TAG);
            Log.e("threedata", String.valueOf(threeData.getPrice()));
            Log.e("threedataItem", item + "");
            if (item.getPrimary_id() != null) {
                item.setClover_quantity(threeData.getHow_many() + "");
                item.setTotal_price(threeData.getPrice());
                lv_sql_products.add(item);
            }
        }
        return lv_sql_products;
    }


    // Get applied discounts list with price and discount id
    private List<String> getLineItemPriceAndDiscountIdClover(String lineItems, Order result) {
        for (int i = 0; i < result.getLineItems().size(); i++) {
            if (lineItems != null) {
                if (result.getLineItems().get(i).getItem() != null) {
                    if (lineItems.equals(result.getLineItems().get(i).getItem().getId())) {
                        if (lineItems.equals(result.getLineItems().get(i).getItem().getId())) {
                            lineitems_price = result.getLineItems().get(i).getPrice();
                            Log.d("itemsPojo>>>>1", lineitems_price + "");
                        }
                    }
                }
            }
        }
        List<String> lv_discounts = new ArrayList<>();
        if (result.getDiscounts() != null) {
            for (int i = 0; i < result.getDiscounts().size(); i++) {
                lv_discounts.add(result.getDiscounts().get(i).getId());
            }
        }
        return lv_discounts;
    }

    // get bottle return product clover price(retail price)
    private List<String> getLineItemNameWithPriceDiscountListFromClover(String lineItems, Order result) {
        for (int i = 0; i < result.getLineItems().size(); i++) {
            if (lineItems.equals(result.getLineItems().get(i).getName())) {
                bottleReturnProductPrice = 0.05 * 100;
                Log.d("lineItemPrice", bottleReturnProductPrice + "");
            }
        }
        List<String> lv_discounts = new ArrayList<>();
        if (result.getDiscounts() != null) {
            Log.d("getDiscounts", result.getDiscounts() + "");
            for (int i = 0; i < result.getDiscounts().size(); i++) {
                lv_discounts.add(result.getDiscounts().get(i).getId());
            }
        }
        return lv_discounts;
    }

    // For removing discount from order products if retail price is below state min
    private void getCloverOrderProductPrice(double discount, double price, String product_id, String which_dis, List<String> getlineitem, List<String> lv_discount, String orderid, int pos) {
        double realPrice = price / 100;
        double discount_amount = 0;
        if (which_dis.equals(Commons.PER)) {
            discount_amount = realPrice * discount / 100;
        } else {
            discount_amount = discount / 100;
        }
        double total_price = realPrice - discount_amount;
        itemsPojo item = databaseOpenHelper.getOnerecord(product_id);
        if (product_id.equals(item.getClover_id())) {
            double retail_price = Double.parseDouble(item.getState_min());
            if (total_price < retail_price) {
                List<DiscountPrice> lv_discount1 = new ArrayList<>();
                DiscountPrice discountPrice = new DiscountPrice();
                discountPrice.setDiscount_price(34 + "");
                discountPrice.setAppleddiscount(34);
                lv_discount1.add(discountPrice);
                new LineItemsDeleteAsync().execute(orderid, getlineitem, lv_discount, Commons.REMOVE_DISCOUNTBELOWSTATE, lv_discount1,
                        Commons.DELETE_DISCOUNT, -1, false);
                //tv_showData.setText(Commons.BELOW_STATE + "\n" + "State Min : " + retail_price + "\n" + "product price : " + total_price);
                //    Toast.makeText(MyApplication.getAppContext(), Commons.BELOW_STATE, Toast.LENGTH_LONG).show();
            } else {
                Log.d("All Good", retail_price + "price" + total_price);
            }
        } else {
            Log.d("total_price>>", total_price + "");
        }
    }


}
