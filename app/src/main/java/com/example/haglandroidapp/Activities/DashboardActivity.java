package com.example.haglandroidapp.Activities;

import android.Manifest;
import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.android.volley.VolleyError;
import com.clover.sdk.util.AuthTask;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.util.CloverAuth;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.Intents;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v1.app.AppNotification;
import com.clover.sdk.v1.app.AppNotificationReceiver;
import com.clover.sdk.v1.printer.job.PrintJob;
import com.clover.sdk.v1.printer.job.StaticOrderPrintJob;
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.inventory.InventoryContract;
import com.clover.sdk.v3.inventory.InventoryIntent;
import com.clover.sdk.v3.inventory.Item;
import com.clover.sdk.v3.inventory.Modifier;
import com.clover.sdk.v3.inventory.ModifierGroup;
import com.clover.sdk.v3.inventory.PriceType;
import com.clover.sdk.v3.order.Discount;
import com.clover.sdk.v3.order.LineItem;
import com.clover.sdk.v3.order.Order;
import com.clover.sdk.v3.order.OrderConnector;
import com.example.haglandroidapp.Activities.HagalItemActivity.HagalListActivity;
import com.example.haglandroidapp.Activities.TotalInventeryActivity.TotalInventoryActivity;
import com.example.haglandroidapp.Handler.GetHagalListHandler;
import com.example.haglandroidapp.Handler.GetMerchnatResult;
import com.example.haglandroidapp.Handler.GetProductDiscountHandler;
import com.example.haglandroidapp.Handler.GetTotalInventeryItemsHandler;
import com.example.haglandroidapp.Interface.GetAgeVerifiactionLineItemName;
import com.example.haglandroidapp.Interface.IGetDistributorsListView;
import com.example.haglandroidapp.Interface.JsonResultInterface;
import com.example.haglandroidapp.Interface.PrintInterface;
import com.example.haglandroidapp.Models.CloverDiscountModelLocal.CloverDiscountModelLocal;
import com.example.haglandroidapp.Models.DiscountPrice;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductExample;
import com.example.haglandroidapp.Models.GetProductListModel.GetProductListExample;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsExample;
import com.example.haglandroidapp.Models.GlobalFunctions;
import com.example.haglandroidapp.Models.PassThresoldPriceModelList;
import com.example.haglandroidapp.Models.itemsPojo;
import com.example.haglandroidapp.R;
import com.example.haglandroidapp.Utils.CSPreferences;
import com.example.haglandroidapp.Utils.Commons;
import com.example.haglandroidapp.Utils.LineItemsDeleteAsync;
import com.example.haglandroidapp.Utils.MyApplication;
import com.example.haglandroidapp.Utils.SharedPref;
import com.example.haglandroidapp.Utils.Utils;
import com.example.haglandroidapp.Utils.WebServices;
import com.example.haglandroidapp.Utils.WebServices2;
import com.example.haglandroidapp.orders.AppliedBottleReturnAsync;
import com.example.haglandroidapp.orders.DatabaseOpenHelper;
import com.example.haglandroidapp.orders.GetMerchantDataAsync;
import com.example.haglandroidapp.orders.OrderConnectorService;
import com.example.haglandroidapp.orders.OrderTaskAsync;
import com.example.haglandroidapp.serverhandler.RequestCode;
import com.example.haglandroidapp.signup.signupallsteps.model.SignupModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.os.Build.VERSION.SDK_INT;


public class DashboardActivity extends AppCompatActivity implements PrintInterface,
        AdapterView.OnItemSelectedListener,
        GetMerchnatResult, JsonResultInterface, IGetDistributorsListView, GetAgeVerifiactionLineItemName {

    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 123;
    private String str_haglcode;
    private static String itemsSize;

    private static TextView txt_totalInventryItems;
    private TextView txt_totalHagalItems;

    //    private CloverAuth.AuthResult authResult;
//    private AuthTask authTask;
    private Account account;
    private InventoryConnector mInventoryConnector;
    private AuthTask authTask;
    private CloverAuth.AuthResult authResult;

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1_002;

    private static Activity activity;
    private static Context context;
    String st2 = "";
    String finalstock, finalseparation2, finalseparation1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private OrderConnector orderConnector;
    public static InventoryConnector inventoryConnector;
    DatabaseOpenHelper databaseOpenHelper;
    private List<Item> merchantItems;
    private Item mItem;
    private int itemId;
    private LineItem mLineItem;

    public static ArrayList<CloverDiscountModelLocal> cloverDiscountModelLocalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_dashboard2);
        activity = this;

        SharedPref.init(activity);
        databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
        account = CloverAccount.getAccount(this);
//        OrderTaskAsync.setChangeListener(this::callback);
//        Crashlytics.getInstance().crash();

        if (account != null) {
            orderConnector = new OrderConnector(this, account, null);
            orderConnector.connect();
            inventoryConnector = new InventoryConnector(this, account, null);
            inventoryConnector.connect();
        }
        intialialiseView();
        init();

//        getInventeryItems();


        if (account == null) {
            account = CloverAccount.getAccount(this);

            if (account == null) {
                Toast.makeText(this, "Clover Account is null", Toast.LENGTH_LONG).show();
                return;
            } else {
//                Toast.makeText(this, "Clover Account is not null", Toast.LENGTH_LONG).show();
            }
        }

//        receiver.register(this);
        final Account account = CloverAccount.getAccount(this);
        if (account == null) {
            return;
        }

        authTask = new AuthTask(this) {
            @Override
            protected void onAuthComplete(boolean success, CloverAuth.AuthResult result) {
                if (success) {
                    authResult = result;
                } else {

                }
            }
        };

        authTask.execute(account);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        if (sh.getString("createdorderid", "").equals(null)) {
            String s1 = sh.getString("createdorderid", "");
            Log.e("createdorderidinstring", s1);
        } else {
            String s1 = sh.getString("createdorderid", "");
            Log.e("createdorderidinstring", s1);
        }

        intialialiseView();
        getInventeryItems();
//        getHagalListApi();

        findViewById(R.id.totalHaglInventory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TotalInventoryActivity.class);
                startActivity(i);
//                finish();
            }
        });

        findViewById(R.id.openHaglInventory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HagalListActivity.class);
                startActivity(i);
//                finish();
            }
        });

        findViewById(R.id.openHagl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcustomdialog();
            }
        });
    }

    private void callback(Order result, List<String> lv_lineitems, List<String> lv_discounts, String order_id, String whichTask) {

        double discount = 0;
        //Log.d("result>>>>", String.valueOf(result));
        if (result != null) {
//            deleteAllDiscount(result, order_id, whichTask, lv_lineitems);
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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor editor = preferences.edit();
                try {
                    editor.putString("result12", result.getJSONObject().get("total").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.apply();
                itemsPojo itemsPojo = new itemsPojo();
                Log.d("result12", String.valueOf(itemsPojo.getTotal_price()));
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

        }
    }


    private void init() {
        txt_totalInventryItems = findViewById(R.id.txt_totalInventryItems);
        txt_totalHagalItems = findViewById(R.id.txt_totalHagalItems);
//        getInventeryItems();
    }

    private void intialialiseView() {
        SharedPref.init(MyApplication.context());
        GetMerchantDataAsync.setMerchantDataChangeListener(this::getMerchantResult);
        new GetMerchantDataAsync().execute();
    }

    public void getMerchantResult(SignupModel signupModel) {

        Log.d("merchantData", signupModel + "");
        if (signupModel != null) {
//            this.signupModel = signupModel;
//            getMerchantData();
        } else {
            Toast.makeText(activity, "Invalid Merchant Id!", Toast.LENGTH_SHORT).show();
//            ShowMessage.message(MyApplication.context(), "Invalid Merchant Id!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        getInventeryItems();
//        getHagalListApi();

        // Retrieve the Clover account
        if (account == null) {
            account = CloverAccount.getAccount(this);

            if (account == null) {
                return;
            }
        }

        // Connect InventoryConnector
        connect();

        startTheService();
        // Get Item
//        new InventoryAsyncTask().execute();

    }


    public void startTheService() {
        Intent intent = new Intent(activity, OrderConnectorService.class);
        MyApplication.context().startService(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
    }


    private void connect() {
        disconnect();
        if (account != null) {
            mInventoryConnector = new InventoryConnector(this, account, null);
            mInventoryConnector.connect();
        }
    }

    private void disconnect() {
        if (mInventoryConnector != null) {
            mInventoryConnector.disconnect();
            mInventoryConnector = null;
        }
    }

    public void showcustomdialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.discount_dialog);

        final AppCompatEditText enter_hagl = dialog.findViewById(R.id.enter_hagl);
        final ImageView img_cancel = dialog.findViewById(R.id.img_cancel);
        TextView td_applycode = dialog.findViewById(R.id.td_applycode);

        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        td_applycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hagalCode = enter_hagl.getText().toString();
//                Toast.makeText(getApplicationContext(), hagalCode, Toast.LENGTH_SHORT).show();

                getProductPurchageOrderApi(hagalCode);
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    ////////////Get Inventery Api
    public static void getInventeryItems() {
        if (Utils.isNetworkConnected(activity)) {
            String authToken = "Bearer " + CSPreferences.readString(activity, "authToken");
            String merchantID = CSPreferences.readString(activity, "merchantID");
            String tokenn;
            if (merchantID.equals("41HJH17GJD4H1")) {
                CSPreferences.putString(activity, "merchantID", "41HJH17GJD4H1");
                SharedPref.saveMerchantId("merchantID", "A3KC1QFWWNX71");
                tokenn = Utils.TOKEN22;
            } else if (merchantID.equals("A3KC1QFWWNX71")) {
                CSPreferences.putString(activity, "merchantID", "A3KC1QFWWNX71");
                SharedPref.saveMerchantId("merchantID", "A3KC1QFWWNX71");
                tokenn = Utils.TOKEN11;
            } else {
                tokenn = authToken;
                CSPreferences.putString(activity, "merchantID", merchantID);
                SharedPref.saveMerchantId("merchantID", merchantID);
            }

            String merchantIDD = SharedPref.getMerchantID(Commons.MERCHANT_ID, "0");
            Log.e("MERCHANTIDD", merchantIDD);

            WebServices.getmInstance().getItemsApi(merchantID, tokenn, new GetTotalInventeryItemsHandler() {
                @Override
                public void onSuccess(GetTotalInventeryItemsExample getItemsExample) {
                    if (getItemsExample != null) {

                        itemsSize = String.valueOf(getItemsExample.getElements().size());
                        Log.e("SIZEEEE", itemsSize);

                        txt_totalInventryItems.setText(itemsSize);

                        //                    }else {
//                        Toast.makeText(activity, "Null Data", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(activity, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//                    login_registerView.showMessage(activity, "Please try again");
                }
            });
        } else {
            Toast.makeText(activity, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    //////////getProductList
    public void getHagalListApi() {

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity, "merchantID");

        String merchantIDD = SharedPref.getMerchantID(Commons.MERCHANT_ID, "0");
        Log.e("MERCHANTIDD", merchantIDD);

        if (Utils.isNetworkConnected(this)) {
            WebServices2.getmInstance().getHagalListApi(merchntId, new GetHagalListHandler() {
                @Override
                public void onSuccess(HagalProductExample getItemsExample) {
//                    hagalListView.hideDialog();
                    if (getItemsExample != null) {
                        txt_totalHagalItems.setText(getItemsExample.getProducts().size() + "");

//                        productName = getItemsExample.getProducts();

                    } else {
//                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
//                    hagalListView.hideDialog();
//                    login_registerView.showMessage(activity, "Please try again");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }


    //////////////getProductPageOrder
    public void getProductPurchageOrderApi(String random_number) {
//        String merchntId = Utils.MERCHANTSID;

        SharedPref.init(MyApplication.context());
        GetMerchantDataAsync.setMerchantDataChangeListener(this::getMerchantResult);
        new GetMerchantDataAsync().execute();
        String merchntId = CSPreferences.readString(activity, "merchantID");

        String merchantIDD = SharedPref.getMerchantID(Commons.MERCHANT_ID, "0");
        Log.e("MERCHANTIDD", merchantIDD);

        if (Utils.isNetworkConnected(this)) {
            WebServices2.getmInstance().getProductPurchageDiscountApi(merchntId, random_number, new GetProductDiscountHandler() {
                @Override
                public void onSuccess(GetProductListExample getProductListExample) {
                    cloverDiscountModelLocalArrayList = new ArrayList<CloverDiscountModelLocal>();
                    cloverDiscountModelLocalArrayList.clear();
//                    hagalListView.hideDialog();
                    if (getProductListExample.getSuccess() == true) {
                        if (!(getProductListExample.getOrder() == null)) {
                            for (int i = 0; i < getProductListExample.getOrder().size(); i++) {
                                for (int j = 0; j < getProductListExample.getOrder().get(i).getOrderItems().size(); j++) {

                                    Log.e("DiscountResponse", getProductListExample.getOrder() + "");


                                    String finalPrice = String.valueOf(getProductListExample.getOrder().get(i).getOrderItems().get(j).getFinalPrice());
                                    String listPrice = String.valueOf(getProductListExample.getOrder().get(i).getOrderItems().get(j).getListPrice());
//                            String discount = String.valueOf(getProductListExample.getOrder().get(0).getDiscount());

                                    String discount = String.valueOf(getProductListExample.getOrder().get(i).getOrderItems().get(j).getAmountSaved());

                                    CSPreferences.putString(activity, "addDiscountType", "addDiscountTypeYES");

                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("kl", discount.trim());

                                    editor.putFloat("finaldiscountadded", Float.parseFloat(finalPrice));
//                            editor.putString("sum1", listPrice);
                                    editor.putString("listPrice", listPrice);
                                    editor.putString("discountFlat", discount);
                                    editor.putString("productId", String.valueOf(getProductListExample.getOrder()
                                            .get(i).getOrderItems().get(j).getProduct().getCloverProductId()));
                                    editor.putString("cashclick", "CloverADD");
//                                    editor.putString("addDiscountType", "addDiscountTypeYES");
                                    editor.apply();


                                    Toast.makeText(getApplicationContext(),
//                                    "Discount " + getProductListExample.getOrder().get(0).getDiscount() + " on "
                                            "Discount Applied = $" + getProductListExample.getOrder().get(i).getOrderItems().get(j).getAmountSaved() /*+ " on "
                                            + getProductListExample.getOrder().get(0).getOrderItems().get(0).getProduct().getName() + " Product"*/,
                                            Toast.LENGTH_SHORT).show();

                                    CloverDiscountModelLocal discountModelLocal = new CloverDiscountModelLocal();
                                    discountModelLocal.setFinalPrice(Float.parseFloat(finalPrice));
                                    discountModelLocal.setListPrice(listPrice);
                                    discountModelLocal.setDiscount(discount);
                                    discountModelLocal.setProductId(String.valueOf(getProductListExample.getOrder()
                                            .get(i).getOrderItems().get(j).getProduct().getCloverProductId()));
                                    discountModelLocal.setCashclick("CloverADD");
                                    discountModelLocal.setTotalDiscountDone("");
                                    discountModelLocal.setProductName(String.valueOf(getProductListExample.getOrder().get(i).getOrderItems().get(j).getProduct().getName()));
                                    cloverDiscountModelLocalArrayList.add(discountModelLocal);

                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Coupon Code Expired", Toast.LENGTH_SHORT).show();
                        }
//                        Your Coupon Code Is Wrong
                    } else {
                        Toast.makeText(getApplicationContext(),
                                getProductListExample.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getApplicationContext(),
                            "Invalid Coupon Code", Toast.LENGTH_SHORT).show();
//                    hagalListView.hideDialog();
//                    login_registerView.showMessage(activity, "Please try again");
//                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void setAgeVerificationFired() {
        if (MyApplication.isVerify) {
            checkDrawOverlayPermission();
        }
    }

    @Override
    public void controlGetDistributorsProgressBar(boolean isShowProgressBar) {

    }

    @Override
    public void onGetDistributorsSuccess(JSONObject jsonObject, RequestCode requestCode, String isSearched) {

    }

    @Override
    public void onGetDistributorsFaillure(VolleyError error) {

    }

    @Override
    public void showGetDistributorsErrorMessage(String errorMessage) {

    }

    @Override
    public void JsonResultSuccess(JSONObject result, RequestCode requestCode) throws Exception {

    }

    @Override
    public void JsonError(JSONObject result, RequestCode requestCode) throws Exception {

    }

    @Override
    public void getResult(Order order) {
        Log.d("getResult", order + "");
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {
        if (!Settings.canDrawOverlays(MyApplication.context())) {
            Intent localIntent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            localIntent.setData(Uri.parse("package:" + getPackageName()));
            this.startActivityForResult(localIntent, 1);
        } else {
//            showWindowPopup();

        }
    }

    private class InventoryAsyncTask extends AsyncTask<Void, Void, Item> {
        @Override
        protected final Item doInBackground(Void... unused) {
            String itemId = null;
            try (Cursor cursor = getContentResolver().query(InventoryContract.Item.contentUriWithAccount(account),
                    new String[]{InventoryContract.Item.UUID}, null, null, null)) {
                //Get inventory item
                if (cursor != null && cursor.moveToFirst()) {
                    itemId = cursor.getString(0);
                }
                return itemId != null ? mInventoryConnector.getItem(itemId) : null;
            } catch (RemoteException | ClientException | ServiceException | BindingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected final void onPostExecute(Item item) {
            if (item != null) {
                Toast.makeText(activity, item.getName(), Toast.LENGTH_SHORT).show();
                Log.e("ITEMSMSSS", item.getName());
            }
        }

    }


    /**
     * Receives an app notification and logs it.
     */
    private AppNotificationReceiver receiver = new AppNotificationReceiver() {
        @Override
        public void onReceive(Context context, AppNotification notification) {
            Log.e("ReceivedINMAIN ", String.valueOf(notification));
            String st = String.valueOf(notification);
            String[] separated = st.split("=");
            String st1 = separated[0];
            st2 = separated[1];
            String[] separated1 = st.split("payload=");
            String st11 = separated1[0];
            finalstock = separated1[1];
            String strepl = ",}";
            //String[] finalseparation=finalstock.replaceAll(strepl,"");
            finalseparation2 = finalstock.replace('}', ' ');

            //    finalseparation1= finalseparation[0];
            //   finalseparation2= finalseparation[1];

            Log.e("ReceivedINMAINfirst2 ", finalseparation2);
//            card.setVisibility(View.GONE);
            if (finalstock == null) {
            } else {
                SharedPreferences settings = getSharedPreferences("YOUR_PREF_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("SNOW_DENSITY", finalstock);
                editor.commit();
//                fetchproduct();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    card.setVisibility(View.GONE);
                }
            }, 2000);

            Log.e("ReceivedINMAINfirst ", st1);
            JSONObject myJsonObj = null;
            try {
                myJsonObj = new JSONObject(st);
                String busName = myJsonObj.getString("appEvent");
                Log.e("ReceivedINMAIbusNameN ", busName);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("ReceivedINMAIN2 ", st2);

            try {
                JSONObject jObject = new JSONObject(String.valueOf(st));
                String aJsonString = jObject.getString("appEvent");
                String bJsonString = jObject.getString("payload");
                Log.e("json ", String.valueOf(aJsonString + bJsonString));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

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
                //    Toast.makeText(MyApplication.context(), Commons.BELOW_STATE, Toast.LENGTH_LONG).show();
            } else {
                Log.d("All Good", retail_price + "price" + total_price);
            }
        } else {
            Log.d("total_price>>", total_price + "");
        }
    }


}
