package com.example.haglandroidapp.Activities;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.clover.remote.client.CloverConnector;
import com.clover.remote.client.USBCloverDeviceConfiguration;
import com.clover.sdk.util.AuthTask;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.util.CloverAuth;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.Intents;
import com.clover.sdk.v1.ResultStatus;
import com.clover.sdk.v1.ServiceConnector;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v1.merchant.Merchant;
import com.clover.sdk.v1.merchant.MerchantConnector;
import com.clover.sdk.v3.inventory.InventoryConnector;
import com.clover.sdk.v3.inventory.InventoryContract;
import com.clover.sdk.v3.inventory.Item;
import com.clover.sdk.v3.order.Order;
import com.example.haglandroidapp.Activities.HagalItemActivity.HagalListActivity;
import com.example.haglandroidapp.Activities.TotalInventeryActivity.TotalInventoryActivity;
import com.example.haglandroidapp.Handler.GetHagalListHandler;
import com.example.haglandroidapp.Handler.GetMerchnatResult;
import com.example.haglandroidapp.Handler.GetProductDiscountHandler;
import com.example.haglandroidapp.Handler.GetTotalInventeryItemsHandler;
import com.example.haglandroidapp.Interface.GetAgeVerifiactionLineItemName;
import com.example.haglandroidapp.Interface.GetOrderDetailsListener;
import com.example.haglandroidapp.Models.DiscountPrice;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductExample;
import com.example.haglandroidapp.Models.GetProductListModel.GetProductListExample;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsExample;
import com.example.haglandroidapp.Models.GlobalFunctions;
import com.example.haglandroidapp.Models.ThreeData;
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
import com.example.haglandroidapp.orders.OrderTaskAsync;
import com.example.haglandroidapp.signup.signupallsteps.model.SignupModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//public class DashboardActivity2 extends AppCompatActivity implements GetOrderDetailsListener, GetMerchnatResult {
public class DashboardActivity2 extends AppCompatActivity {

    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 123;
    private String str_haglcode;
    private String itemsSize;
    private TextView txt_totalInventryItems;
    private TextView txt_totalHagalItems;

    private Account mAccount;
    private InventoryConnector mInventoryConnector;
    public static final String TAG = DashboardActivity2.class.getSimpleName();
//    private TextView mMerchantTextView;
//    private TextView mInventoryTextView;

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1002;

    private Activity activity;
    DatabaseOpenHelper databaseOpenHelper;
    long HOD = 100;
    public static long finalselecteditems;
    List<DiscountPrice> findtotal = new ArrayList<>();
    String bottleProductName = null;
    double bottleReturnProductPrice = 0;
    long bottleReturnPrice = 0;
    public static boolean isVerify = false;
    public static GetAgeVerifiactionLineItemName getAgeVerifiactionLineItemName;
    double lineitems_price = 0;
    private Account account;
    private AuthTask authTask;
    private CloverAuth.AuthResult authResult;
    private MerchantConnector mMerchantConnector;
    SignupModel signupModel;

    public static final String EXTRA_DATA = "data";
    private CloverConnector cloverConnector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_dashboard2);
        activity = this;

        Log.e("123457","2345678");

//        OrderTaskAsync.setChangeListener(this::callback);
//        GetMerchantDataAsync.setMerchantDataChangeListener(this::getMerchantResult);

        if (account == null) {
            account = CloverAccount.getAccount(this);

            if (account == null) {
                Toast.makeText(this, "Clover Account is null", Toast.LENGTH_LONG).show();
                return;
            } else {
//                Toast.makeText(this, "Clover Account is not null", Toast.LENGTH_LONG).show();
            }
        }

        new USBCloverDeviceConfiguration(activity, "07VM2P120K2WT");


        databaseOpenHelper = new DatabaseOpenHelper(MyApplication.context());
//        OrderTaskAsync.setChangeListener(this::callback);
        new GetMerchantDataAsync().execute();
        final Account account = CloverAccount.getAccount(activity);
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

        init();

//        checkAndRequestPermissions();
//        checkPermission();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

        getInventeryItems();
        getHagalListApi();


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

    private void init() {
        txt_totalInventryItems = findViewById(R.id.txt_totalInventryItems);
        txt_totalHagalItems = findViewById(R.id.txt_totalHagalItems);
//        GetMerchantDataAsync.setMerchantDataChangeListener(this::getMerchantResult);
        new GetMerchantDataAsync().execute();
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
    public void getInventeryItems() {
        if (Utils.isNetworkConnected(this)) {
            String authToken = "Bearer "+ CSPreferences.readString(activity,"authToken");
            String merchantID = CSPreferences.readString(activity, "merchantID");
            String tokenn;
            if (merchantID.equals("41HJH17GJD4H1")) {
                tokenn = Utils.TOKEN22;
            } else if (merchantID.equals("A3KC1QFWWNX71")) {
                tokenn = Utils.TOKEN11;
            } else {
                tokenn = authToken;
            }

            String id =SharedPref.getMerchantID(Commons.MERCHANT_ID, "merchantID");

            WebServices.getmInstance().getItemsApi(merchantID,tokenn, new GetTotalInventeryItemsHandler() {
                @Override
                public void onSuccess(GetTotalInventeryItemsExample getItemsExample) {
                    if (getItemsExample != null) {

                        itemsSize = String.valueOf(getItemsExample.getElements().size());
                        Log.e("SIZEEEE", itemsSize);

                        txt_totalInventryItems.setText(itemsSize);

                            /*if (getItemsExample.getIsSuccess() == true) {
//                                getInventeryItemsView.showMessage(activity, getItemsExample.getMessage());
//                            Toast.makeText(activity, getItemsExample.getMessage(), Toast.LENGTH_SHORT).show();
//                                CSPreferences.putString(activity, Utils.TOKEN, getItemsExample.getData().getToken());


                            } else {
//                                forgotPasswordView.showMessage(activity, getItemsExample.getMessage());
                            }*/
//                    }else {
//                        Toast.makeText(activity, "Null Data", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }

                @Override
                public void onError(String message) {
//                    login_registerView.showMessage(activity, "Please try again");
                }
            });
        } else {
            Toast.makeText(this, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    //////////getProductList
    public void getHagalListApi() {

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity,"merchantID");
        if (Utils.isNetworkConnected(this)) {
            WebServices2.getmInstance().getHagalListApi(merchntId, new GetHagalListHandler() {
                @Override
                public void onSuccess(HagalProductExample getItemsExample) {
//                    hagalListView.hideDialog();
                    if (getItemsExample != null) {

                        txt_totalHagalItems.setText(getItemsExample.getProducts().size() + "");

//                        productName = getItemsExample.getProducts();
//
//                        hagalListAdapter = new HagalListAdapter(activity, productName);
//                        recyclerView.setHasFixedSize(true);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
//                        recyclerView.setAdapter(hagalListAdapter);
                            /*if (getItemsExample.getIsSuccess() == true) {
//                                getInventeryItemsView.showMessage(activity, getItemsExample.getMessage());
//                            Toast.makeText(activity, getItemsExample.getMessage(), Toast.LENGTH_SHORT).show();
//                                CSPreferences.putString(activity, Utils.TOKEN, getItemsExample.getData().getToken());

                            } else {
//                                forgotPasswordView.showMessage(activity, getItemsExample.getMessage());
                            }*/
//                    }else {
//                        Toast.makeText(activity, "Null Data", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
//                    hagalListView.hideDialog();
//                    login_registerView.showMessage(activity, "Please try again");
//                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    //////////////getProductPageOrder
    public void getProductPurchageOrderApi(String random_number) {

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity,"merchantID");
        if (Utils.isNetworkConnected(this)) {
            WebServices2.getmInstance().getProductPurchageDiscountApi(merchntId, random_number, new GetProductDiscountHandler() {
                @Override
                public void onSuccess(GetProductListExample getProductListExample) {
//                    hagalListView.hideDialog();
                    if (getProductListExample.getSuccess() == true) {
                        if (getProductListExample.getOrder().size() != 0) {

                            cloverConnector.initializeConnection();
                            cloverConnector.showMessage("Welcome to Clover Connector!");




//                            Intent intent = new Intent(Intents.ACTION_START_REGISTER);
//                            intent.putExtra(Intents.EXTRA_DISABLE_CASHBACK, "10.00");
//                            startActivity(intent);

//                            Intent intent = new Intent(Intents.ACTION_CLOVER_PAY);
//                            intent.putExtra(Intents.EXTRA_CLOVER_ORDER_ID, "order.getId()");
//                            intent.putExtra(Intents.EXTRA_OBEY_AUTO_LOGOUT, "autoLogout");
//                            startActivity(intent);

                            String discount = String.valueOf(getProductListExample.getOrder().get(0).getTotalAmountSaved());

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("kl", discount.trim());

                            editor.putFloat("finaldiscountadded", Float.parseFloat(discount));

                            editor.putString("cashclick", "CloverADD");
                            editor.putString("sum1", String.valueOf(discount));
                            editor.apply();

                            Toast.makeText(getApplicationContext(),
                                    "Discount " + getProductListExample.getOrder().get(0).getTotalAmountSaved() + " on "
                                            + getProductListExample.getOrder().get(0).getOrderItems().get(0).getProduct().getName() + " Product",
                                    Toast.LENGTH_SHORT).show();

                            /////////////////


                            /////////////////

//                        txt_totalHagalItems.setText(getProductListExample.getProducts().size() + "");
                            new AppliedBottleReturnAsync().execute("", bottleReturnPrice);

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Your Coupon Code Is Wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(),
                                getProductListExample.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
//                    hagalListView.hideDialog();
//                    login_registerView.showMessage(activity, "Please try again");
//                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkAndRequestPermissions() {
        int camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_CODE_ASK_PERMISSIONS);
            return false;
        }
        return true;
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "Permission error. You have permission", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(activity, "Permission error. You have asked for permission", Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            // for a stuff below api level 23
            Toast.makeText(activity, "Permission error. You already have the permission", Toast.LENGTH_SHORT).show();

            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Long amount = getIntent().hasExtra(Intents.EXTRA_AMOUNT) ? getIntent().getLongExtra(Intents.EXTRA_AMOUNT, 0l) : null;
        if (amount != null) {

//            TextView amountTextView = (TextView) findViewById(R.id.amount);

            NumberFormat currencyFormat = DecimalFormat.getCurrencyInstance(Locale.US);
            String formattedAmount = currencyFormat.format(amount / 100.0);
//            amountTextView.setText(formattedAmount);

//            mAddButton = (Button) findViewById(R.id.add_discount_button);
//            mAddButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
            Intent data = new Intent();
//            double discountedAmount = (longToDouble(amount)) * 0.9;
            data.putExtra(Intents.EXTRA_AMOUNT, "10.00");
            setResult(RESULT_OK, data);
            finish();
//                }
//            });
        }

//        mMerchantTextView = (TextView) findViewById(R.id.merchantName);
//        mInventoryTextView = (TextView) findViewById(R.id.inventoryItems);

        //Retrieve Clover account
//        if (mAccount == null) {
//            mAccount = CloverAccount.getAccount(this);
//
//            if (mAccount == null) {
//                return;
//            }
//        }

        String orderId = getIntent().getStringExtra(Intents.EXTRA_ORDER_ID);

        connectInventory();
        connectMerchant();

//        new MerchantAsyncTask().execute();
//        new InventoryAsyncTask().execute();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        authTask.cancel(true);
        disconnectInventory();
        disconnectMerchant();
    }

    private void connectMerchant() {
        disconnectMerchant();

        if (mAccount != null) {
            mMerchantConnector = new MerchantConnector(this, mAccount, null);
            mMerchantConnector.connect();
        }
    }

    private void disconnectMerchant() {
        if (mMerchantConnector != null) {
            mMerchantConnector.disconnect();
            mMerchantConnector = null;
        }
    }

    private void connectInventory() {
        disconnectInventory();

        if (mAccount != null) {
            mInventoryConnector = new InventoryConnector(this, mAccount, null);
            mInventoryConnector.connect();
        }
    }

    private void disconnectInventory() {
        if (mInventoryConnector != null) {
            mInventoryConnector.disconnect();
            mInventoryConnector = null;
        }
    }

//    @Override
//    public void callback(Order result, List<String> lv_lineitems, List<String> lv_discounts, String order_id, String whichTask) {
//        double discount = 0;
//        //Log.d("result>>>>", String.valueOf(result));
//        if (result != null) {
////            deleteAllDiscount(result, order_id, whichTask, lv_lineitems);
//            if (whichTask.equals(Commons.DELETE_DISCOUNTAPPLICATION)) {
//                if (result.getLineItems() != null) {
//                    for (int i = 0; i < result.getLineItems().size(); i++) {
//                        Log.d("result.getLineItems()", result.getLineItems().get(i).getId());
//                        if (result.getLineItems().get(i).getId().equals(lv_lineitems.get(0))) {
//                            if (result.getLineItems().get(i).getDiscounts() != null) {
//                                for (int j = 0; j < result.getLineItems().get(i).getDiscounts().size(); j++) {
//                                    if (result.getLineItems().get(i).getDiscounts().get(j).getPercentage() == null) {
//                                        discount = discount + GlobalFunctions.removeSpecialChars(result.getLineItems().get(i).getDiscounts().get(j).getAmount());
//                                    } else {
//                                        discount = discount + result.getLineItems().get(i).getDiscounts().get(j).getPercentage();
//                                    }
//                                }
//                                if (result.getLineItems().get(i).getDiscounts().get(0).getPercentage() == null) {
//                                    getCloverOrderProductPrice(discount, result.getLineItems().get(i).getPrice(),
//
//                                            result.getLineItems().get(i).getItem().getId(), Commons.DOLLAR_AMOUNT, lv_lineitems, lv_discounts, order_id, i);
//                                } else {
//                                    getCloverOrderProductPrice(discount,
//                                            result.getLineItems().get(i).getPrice(), result.getLineItems().get(i).getItem().getId(),
//                                            Commons.PER, lv_lineitems, lv_discounts, order_id, i);
//                                }
//
//                            }
//                        }
//                    }
//                }
//            } else {
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.context();
//                SharedPreferences.Editor editor = preferences.edit();
//                try {
//                    editor.putString("result12", result.getJSONObject().get("total").toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                editor.apply();
//                itemsPojo itemsPojo = new itemsPojo();
//                Log.d("result12", String.valueOf(itemsPojo.getTotal_price()));
//                int totalAmount = 0;
//                for (int i = 0; i < result.getLineItems().size(); i++) {
//                    String txN_AMT = null;
//                    try {
//                        JSONObject jsonRootObject = new JSONObject(String.valueOf(result));
//
//                        //Get the instance of JSONArray that contains JSONObjects
//                        JSONArray jsonArray = jsonRootObject.optJSONArray("lineItems");
//                        //  JSONArray jsonArray1 = jsonArray.optJSONArray("lineItems");
//
//                        txN_AMT = result.getJSONObject().get("lineItems").toString();
//                        totalAmount = Integer.parseInt(totalAmount + txN_AMT);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                //outside of the loop, you now have the total amount
//                //   Toast.makeText(MyApplication.this, "" + totalAmount, Toast.LENGTH_SHORT).show();
//                if (whichTask.toLowerCase().equals(Commons.ADD) || whichTask.toLowerCase().equals(Commons.REMOVE)) {
//                    if (result.getLineItems() != null) {
//                        boolean isDiscountExist = false;
//                        List<DiscountPrice> discount_list = new ArrayList<>();
//                        double total_discount = 0;
//                        int sql_qunatity = 0;
//                        String dis_type = null;
//
//
//                        // Implementing bottle return functionality acc to product name here
//                        Map<String, Integer> lv_productsNameRepetation = cloverOrderRemoveRepetationOfProductsNameForReturn(result);
//                        // bottle Return product name and age verification product name
//                        getLineItemForBottleReturnsAndAgeVerification(lv_productsNameRepetation, order_id, result);
//                        List<itemsPojo> lv_checkDiscountApplicable = new ArrayList<>();
//                        List<itemsPojo> lv_getSqlProducts = getCloverOrderRemoveRepetationOfProducts(result);
//                        for (int i = 0; i < lv_getSqlProducts.size(); i++) {
//                            if (lv_getSqlProducts.get(i).getRetail_price() != null) {
//                                double retail_price = Double.parseDouble(lv_getSqlProducts.get(i).getRetail_price());
//                                double state_min = Double.parseDouble(lv_getSqlProducts.get(i).getState_min());
//                                double clover_qunatity = Double.parseDouble(lv_getSqlProducts.get(i).getClover_quantity());
//                                double dis_price = Double.parseDouble(lv_getSqlProducts.get(i).getDiscount_price());
//                                if (lv_getSqlProducts.get(i).getPer_type().equals(Commons.PERCENTAGE)) {
//                                    double total_retail = retail_price * clover_qunatity;
//                                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
//                                    SharedPreferences.Editor edi = preferences.edit();
//                                    edi.putString("result12", String.valueOf(total_retail));
//                                    edi.apply();
//                                    double total_statemin = state_min * clover_qunatity;
//                                    double dis_subtractamount = total_retail * dis_price / 100;
//                                    double total_price = total_retail - dis_subtractamount;
//                                    itemsPojo itemsPojos = lv_getSqlProducts.get(i);
//                                    if (total_price > total_statemin) {
//                                        lv_checkDiscountApplicable.add(itemsPojos);
//                                    }
//                                } else { // Check state min for flat discount here
//                                    double total_price = retail_price - dis_price;
//                                    itemsPojo itemsPojos = lv_getSqlProducts.get(i);
//                                    if (total_price > state_min) {
//                                        lv_checkDiscountApplicable.add(itemsPojos);
//                                    }
//                                }
//                            }
//                        }
//                        // if we have more than products in a discount
//
//                        List<itemsPojo> lv_finalproducts = new ArrayList<>();
//                        for (int i = 0; i < lv_checkDiscountApplicable.size(); i++) {
//                            if (lv_finalproducts.size() > 0) {
//                                itemsPojo items = lv_checkDiscountApplicable.get(i);
//                                for (int j = 0; j < lv_finalproducts.size(); j++) {
//                                    Log.d("lv_raman>>>>", lv_finalproducts + "");
//                                    // Check for we have alraedy added discount for that product by using primary id
//                                    // If already added
//                                    if (items.getPrimary_id().equals(lv_finalproducts.get(j).getPrimary_id())) {
//                                        if (items.getClover_id().equals(lv_finalproducts.get(j).getClover_id())) {
//                                        } else {
//                                            // firstly get older values and add-up new
//                                            long lat_qunatity = Long.parseLong(items.getClover_quantity());
//                                            long exist_quantity = Long.parseLong(lv_finalproducts.get(j).getClover_quantity());
//                                            double lat_price = items.getTotal_price() * lat_qunatity;
//                                            double last_price = lv_finalproducts.get(j).getTotal_price();
//                                            double total_price = lat_price + last_price;
//                                            long qunatity = exist_quantity + lat_qunatity;
//                                            boolean isDiscountApply = chkDiscountApplicableOnSameParentIdProducts(items);
//                                            if (isDiscountApply) {
//                                                items.setClover_quantity(qunatity + "");
//                                                items.setTotal_price(total_price);
//                                                lv_finalproducts.set(j, items);
//                                            } else {
//                                                lv_finalproducts.get(j).setClover_quantity(qunatity + "");
//                                            }
//                                        }
//                                    } else {
//                                        // else products will be added in lv_finalproducts
//                                        boolean isgotSameId = false;
//                                        for (int k = 0; k < lv_finalproducts.size(); k++) {
//                                            if (items.getPrimary_id().equals(lv_finalproducts.get(k).getPrimary_id())) {
//                                                isgotSameId = true;
//                                                break;
//                                            } else {
//                                            }
//                                        }
//                                        if (isgotSameId) {
//                                        } else {
//                                            int qunt = Integer.parseInt(items.getClover_quantity());
//                                            double total = items.getTotal_price() * qunt;
//                                            itemsPojo items2 = lv_checkDiscountApplicable.get(i);
//                                            items2.setTotal_price(total);
//                                            if (j == lv_finalproducts.size() - 1) {
//                                                lv_finalproducts.add(items2);
//                                                break;
//                                            } else {
//                                                lv_finalproducts.add(items2);
//                                            }
//                                        }
//                                    }
//                                }
//                            } else {
//                                itemsPojo items = lv_checkDiscountApplicable.get(i);
//                                double total = items.getTotal_price() * Integer.parseInt(items.getClover_quantity());
//                                items.setTotal_price(total);
//                                boolean isDiscountApply = chkDiscountApplicableOnSameParentIdProducts(items);
//                                if (isDiscountApply)
//                                    lv_finalproducts.add(items);
//                                Log.d("start>>", lv_finalproducts + "");
//                            }
//                        }
//
//                        if (lv_finalproducts.size() != 0) {
//                            for (int i = 0; i < lv_finalproducts.size(); i++) {
//                                itemsPojo item = lv_finalproducts.get(i);
//                                Log.d("item.getClover_id()>>", item + "");
//                                lv_discounts = getLineItemPriceAndDiscountIdClover(item.getClover_id(), result);
//                                if (item.getDiscount_quantity() != null) {
//                                    sql_qunatity = Integer.parseInt(item.getDiscount_quantity());
//                                    if (lv_discounts == null) {
//                                        isDiscountExist = false;
//                                        // Discount should be applicable
//                                    } else {
//                                        isDiscountExist = true;
//                                    }
//                                    long limit = 0;
//                                    if (item.getLimit() == "_") {
//                                    } else if (item.getLimit().equals("")) {
//                                    } else {
//                                        limit = Long.parseLong(item.getLimit());
//                                    }
//                                    Log.d("sql_qunatity", sql_qunatity + "");
//                                    long clover_qunatity = Long.parseLong(item.getClover_quantity());
//                                    if (item.getPer_type().equals(Commons.PERCENTAGE)) {
//                                        if (limit == 0) {
//                                            Log.d("sql_qunatity11", sql_qunatity + "");
//                                            if (clover_qunatity % sql_qunatity == 0) {
//                                                Log.d("sql_qunatity111", sql_qunatity + "");
//                                                double new_price = item.getTotal_price() / HOD;
//                                                total_discount = new_price * Double.parseDouble(item.getDiscount_price()) / (double) 100;
//                                                // total_discount = total_discount / sql_qunatity;
//                                                DiscountPrice discountPrice = new DiscountPrice();
//                                                discountPrice.setAppleddiscount(total_discount);
//                                                discountPrice.setDiscount_price(item.getDiscount_price());
//                                                discountPrice.setQunatity(sql_qunatity);
//                                                discountPrice.setDiscount_name(item.getDiscount_name());
//                                                discountPrice.setCloverItemsCount(clover_qunatity);
//                                                discountPrice.setDiscount_type(item.getPer_type());
//                                                discountPrice.setAction_type(Commons.ADD);
//                                                discount_list.add(discountPrice);
//                                                item.setDiscount_quantity(sql_qunatity + "");
//                                                total_discount = 0;
//                                                dis_type = Commons.PER;
//                                            } else {
//                                                if (clover_qunatity >= sql_qunatity) {
//                                                    Log.d("sql_qunatity3333", sql_qunatity + "");
//                                                    double new_price = item.getTotal_price() / HOD;
//                                                    total_discount = new_price * Double.parseDouble(item.getDiscount_price()) / (double) 100;
//                                                    //start
//                                                    DiscountPrice discountPrice = new DiscountPrice();
//                                                    discountPrice.setAppleddiscount(total_discount);
//                                                    discountPrice.setDiscount_price(item.getDiscount_price());
//                                                    discountPrice.setQunatity(sql_qunatity);
//                                                    discountPrice.setDiscount_name(item.getDiscount_name());
//                                                    discountPrice.setCloverItemsCount(clover_qunatity);
//                                                    discountPrice.setDiscount_type(item.getPer_type());
//                                                    discountPrice.setAction_type(Commons.ADD);
//                                                    discount_list.add(discountPrice);
//                                                    item.setDiscount_quantity(sql_qunatity + "");
//                                                    total_discount = 0;
//                                                    dis_type = Commons.PER;
//                                                   /* Log.d("sql_qunatity3333", sql_qunatity + "");
//                                                    DiscountPrice discountPrice = new DiscountPrice();
//                                                    discountPrice.setDiscount_name(item.getDiscount_name());
//                                                    discountPrice.setDiscount_price(item.getDiscount_price());
//                                                    discountPrice.setAction_type(Commons.LOCAL_DISCOUNT);
//                                                    //discount_list = new ArrayList<>();
//                                                    discount_list.add(discountPrice);
//                                                    new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
//                                                            lv_discounts, Commons.LOCAL_DISCOUNT, discount_list,
//                                                            Commons.PERCENTAGE, 2, isDiscountExist, total_discount);*/
//                                                }
//                                            }
//                                        } else {
//                                            Log.d("sql_qunatity22", sql_qunatity + "");
//                                            if (clover_qunatity % sql_qunatity == 0 && clover_qunatity <= limit) {
//                                                double new_price = item.getTotal_price() / HOD;
//                                                total_discount = new_price * Double.parseDouble(item.getDiscount_price()) / (double) 100;
//
//                                                // total_discount = total_discount / sql_qunatity;
//                                                DiscountPrice discountPrice = new DiscountPrice();
//                                                Log.d("total_discount>", total_discount + "");
//                                                discountPrice.setAppleddiscount(total_discount);
//                                                discountPrice.setDiscount_price(item.getDiscount_price());
//                                                discountPrice.setQunatity(sql_qunatity);
//                                                sql_qunatity = sql_qunatity + sql_qunatity;
//                                                item.setDiscount_quantity(sql_qunatity + "");
//                                                discountPrice.setDiscount_name(item.getDiscount_name());
//                                                discountPrice.setCloverItemsCount(clover_qunatity);
//                                                discountPrice.setDiscount_type(item.getPer_type());
//                                                discountPrice.setAction_type(Commons.ADD);
//                                                discount_list.add(discountPrice);
//                                                total_discount = 0;
//                                                dis_type = Commons.PER;
//                                            } else { // foe showing last account
//                                                if (clover_qunatity >= sql_qunatity) {
//                                                    Log.d("sql_qunatity3333", sql_qunatity + "");
//                                                    DiscountPrice discountPrice = new DiscountPrice();
//                                                    discountPrice.setDiscount_name(item.getDiscount_name());
//                                                    discountPrice.setDiscount_price(item.getDiscount_price());
//                                                    discountPrice.setAction_type(Commons.LOCAL_DISCOUNT);
//                                                    //discount_list = new ArrayList<>();
//                                                    discount_list.add(discountPrice);
//                                                    new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
//                                                            lv_discounts, Commons.LOCAL_DISCOUNT, discount_list,
//                                                            Commons.PERCENTAGE, 2, isDiscountExist, total_discount);
//                                                }
//                                            }
//
//                                        }
//                                    } else {
//                                        dis_type = Commons.DOLLAR_AMOUNT;
//                                        Log.d("limuit1", limit + "");
//                                        if (limit == 0) {
//                                            Log.d("limuit11", limit + "");
//                                            if (clover_qunatity % sql_qunatity == 0) {
//                                                double flat_discount = 0;
//                                                Log.d("limuit111", limit + "");
//                                                // Get how much discount u want to apply,divide by 100, multiiply with quantity
//                                                // then send discount price to clover
//                                                flat_discount = Double.parseDouble(item.getDiscount_price());
//                                                total_discount = flat_discount * clover_qunatity / sql_qunatity;
//                                                // total_discount = lastPrice;
//                                                DiscountPrice discountPrice = new DiscountPrice();
//                                                discountPrice.setAppleddiscount(total_discount);
//                                                discountPrice.setDiscount_name(item.getDiscount_name());
//                                                discountPrice.setAction_type(Commons.ADD);
//                                                discountPrice.setDiscount_price(item.getDiscount_price());
//                                                discountPrice.setQunatity(sql_qunatity);
//                                                discountPrice.setCloverItemsCount(clover_qunatity);
//                                                discountPrice.setDiscount_type(item.getPer_type());
//                                                discount_list.add(discountPrice);
//                                                Log.d("discount_list", flat_discount + "");
//                                            } else {
//                                                DiscountPrice discountPrice = new DiscountPrice();
//                                                discountPrice.setDiscount_name(item.getDiscount_name());
//                                                discountPrice.setDiscount_price(item.getDiscount_price());
//                                                discountPrice.setAction_type(Commons.LOCAL_DISCOUNT);
//                                                //discount_list = new ArrayList<>();
//                                                discount_list.add(discountPrice);
//                                                if (clover_qunatity >= sql_qunatity) {
//                                                    total_discount = SharedPref.getDiscountPrice(Commons.DISCOUNT_PRICE, 0);
//                                                    new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
//                                                            lv_discounts, Commons.LOCAL_DISCOUNT, discount_list,
//                                                            Commons.DOLLAR_AMOUNT, 2, isDiscountExist, total_discount);
//                                                }
//                                            }
//                                        } else {
//                                            Log.d("limuit1122", limit + "");
//                                            if (clover_qunatity % sql_qunatity == 0 && clover_qunatity <= limit) {
//                                                double flat_discount = 0;
//                                                Log.d("limuit33", limit + "");
//                                                // Get how much discount u want to apply,divide by 100, multiiply with quantity
//                                                // then send discount price to clover
//                                                flat_discount = Double.parseDouble(item.getDiscount_price());
//                                                total_discount = flat_discount * clover_qunatity / sql_qunatity;
//                                                DiscountPrice discountPrice = new DiscountPrice();
//                                                discountPrice.setAppleddiscount(total_discount);
//                                                discountPrice.setDiscount_name(item.getDiscount_name());
//                                                discountPrice.setAction_type(Commons.ADD);
//                                                discountPrice.setDiscount_price(item.getDiscount_price());
//                                                discountPrice.setQunatity(sql_qunatity);
//                                                discountPrice.setCloverItemsCount(clover_qunatity);
//                                                discountPrice.setDiscount_type(item.getPer_type());
//                                                discount_list.add(discountPrice);
//                                                Log.d("discount_list2", discount_list + "");
//
//                                            } else {
//                                                Log.d("discount_list3", "flat_discount" + "");
//                                                DiscountPrice discountPrice = new DiscountPrice();
//                                                discountPrice.setDiscount_name(item.getDiscount_name());
//                                                discountPrice.setDiscount_price(item.getDiscount_price());
//                                                discountPrice.setAction_type(Commons.LOCAL_DISCOUNT);
//                                                // discount_list = new ArrayList<>();
//                                                discount_list.add(discountPrice);
//                                                if (clover_qunatity >= sql_qunatity) {
//                                                    total_discount = SharedPref.getDiscountPrice(Commons.DISCOUNT_PRICE, 0);
//                                                    new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
//                                                            lv_discounts, Commons.LOCAL_DISCOUNT, discount_list,
//                                                            Commons.DOLLAR_AMOUNT, 2, isDiscountExist, total_discount);
//                                                }
//
//                                            }
//
//                                        }
//
//                                    }
//
//                                }
//                            }
//                            if (whichTask.equals(Commons.REMOVE)) {
//                                if (dis_type != null) {
//                                    if (dis_type.equals(Commons.PER)) {
//                                        Log.d("perType", dis_type);
//                                        new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
//                                                lv_discounts, Commons.REMOVE, discount_list,
//                                                Commons.PERCENTAGE, 2, isDiscountExist, 0.0);
//                                    } else {
//                                        Log.d("perTyp22e", dis_type);
//                                        new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
//                                                lv_discounts, Commons.REMOVE, discount_list,
//                                                Commons.DOLLAR_AMOUNT, 2, isDiscountExist, 0.0);
//                                    }
//                                }
//                            }
//                            if (whichTask.equals(Commons.ADD)) {
//                                if (dis_type != null) {
//                                    if (dis_type.equals(Commons.PER)) {
//                                        Log.d("perType", dis_type);
//                                        new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
//                                                lv_discounts, Commons.ADD, discount_list,
//                                                Commons.PERCENTAGE, 2, isDiscountExist, 0.0);
//                                    } else {
//                                        Log.d("perTyp22e", dis_type);
//                                        new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
//                                                lv_discounts, Commons.ADD, discount_list,
//                                                Commons.DOLLAR_AMOUNT, 2, isDiscountExist, 0.0);
//                                    }
//                                }
//                            } else {
//                                new LineItemsDeleteAsync().execute(order_id, lv_lineitems,
//                                        lv_discounts, Commons.REMOVE, discount_list,
//                                        Commons.PERCENTAGE, 2, isDiscountExist, 0.0);
//                            }
//
//                        }
//                    }
//
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public void getMerchantResult(SignupModel signupModel) {
//
//        Log.d("merchantData", signupModel + "");
//        if (signupModel != null) {
//            this.signupModel = signupModel;
//            getMerchantData();
//        } else {
//            Toast.makeText(activity, "Invalid Merchant Id!", Toast.LENGTH_SHORT).show();
////            ShowMessage.message(MyApplication.context(), "Invalid Merchant Id!");
////            isPortraitOrLandscape();
//        }
//
//    }

    private void getMerchantData() {

        if (mAccount != null) {
            mMerchantConnector.getMerchant(new ServiceConnector.Callback<Merchant>() {
                @Override
                public void onServiceSuccess(Merchant result, ResultStatus status) {
                    signupModel.setAddress(result.getAddress().getAddress1());
                    signupModel.setCity(result.getAddress().getCity());
                    signupModel.setState(result.getAddress().getState());
                    signupModel.setCountry(result.getAddress().getCountry());
                    signupModel.setZipcode(result.getAddress().getZip());
                    signupModel.setBusiness_phone(result.getPhoneNumber());
//                    chkIsUserAlreadyExists();
                    Log.d("merchantData", signupModel + "");
                }

                @Override
                public void onServiceFailure(ResultStatus status) {

                }

                @Override
                public void onServiceConnectionFailure() {
                }
            });


        }
    }

    private class InventoryAsyncTask extends AsyncTask<Void, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(Void... voids) {
            try {
                return mInventoryConnector.getItems();
            } catch (RemoteException | ClientException | ServiceException | BindingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected final void onPostExecute(List<Item> items) {
            super.onPostExecute(items);

            StringBuilder inventoryText = new StringBuilder("Items: ");
            inventoryText.append("\n");

//            for (int i = 0; i < items.size(); i++) {
//                inventoryText.append(items.get(i).getName() + "\n");
//            }

            Log.e("inventoryText", inventoryText.toString());
//            mInventoryTextView.setText(inventoryText.toString());
        }
    }

//    private class MerchantAsyncTask extends AsyncTask<Void, Void, Merchant> {
//
//        @Override
//        protected Merchant doInBackground(Void... voids) {
//            try {
//                return mMerchantConnector.getMerchant();
//            } catch (ServiceException | BindingException | ClientException | RemoteException e) {
//
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Merchant merchant) {
//            super.onPostExecute(merchant);
////            Log.e("inventoryText_Merchant Name:",merchant.getName() + "");
////            mMerchantTextView.setText("Merchant Name: " + merchant.getName());
//        }
//    }


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

    // Apply bottle return functionality
    private void getLineItemForBottleReturnsAndAgeVerification(Map<String, Integer> repetitionsItemsList, String order_id, Order result) {
        int temp = 0;
        for (Map.Entry<String, Integer> entry : repetitionsItemsList.entrySet()) {
//            bottleProductName = entry.getKey();
            int lastValue = entry.getValue();
            DiscountPrice disprice = new DiscountPrice();
            temp = temp + lastValue;
            Log.e("finalstock", String.valueOf(temp));
            finalselecteditems = temp;
            disprice.setDiscount_name("Surcharge");
            disprice.settotalstock(finalselecteditems);
            Log.e("finalselecteditems", String.valueOf(finalselecteditems));


            CSPreferences.putString(activity, "finalselecteditems", String.valueOf(finalselecteditems));

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("finalselecteditems", String.valueOf(finalselecteditems));
            editor.apply();
            entry.getValue();

            findtotal.add(disprice);
            if (bottleProductName.equals(Commons.BOTTLERETURN_PRODUCT)) { //Bottle Return - Single
                // Get clover price
                getLineItemNameWithPriceDiscountListFromClover(bottleProductName, result);
//                Log.d("price", bottleReturnProductPrice + "");
                long price = (long) bottleReturnProductPrice;
                long getPrice = price * lastValue;
                bottleReturnPrice = bottleReturnPrice + getPrice;
                // Log.d("finalPrice", bottleReturnPrice + ">>>>>>" + bottleProductName);
                isVerify = false;
                // on triggering on lineiTemAdded we get only orderid and lineitem id so for name we are using this method
            } else if (bottleProductName.equals("IsVerify")) {
                // to register local receiver
                isVerify = true;
                Log.d("isVerifyChcek", isVerify + "");
                getAgeVerifiactionLineItemName.setAgeVerificationFired();
            } else {
                Log.d("isVerifyChcek", isVerify + "");

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

//    public static void setAgeVerificationFiredSetListener(GetAgeVerifiactionLineItemName ageVerificationFiredSetListener) {
//        getAgeVerifiactionLineItemName = ageVerificationFiredSetListener;
//    }

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
            Log.d("chkDiscount", "discountApplicable" + total_discount + "........" + state_min);
            isDiscount = true;
        } else {
            Log.d("chkDiscount", "NotdiscountApplicable" + total_discount + "........" + state_min);
            isDiscount = false;
        }
        return isDiscount;
    }

    // Get repetitions free list from clover orders
    private List<itemsPojo> getCloverOrderRemoveRepetationOfProducts(Order result) {
        List<itemsPojo> lv_sql_products = new ArrayList<>();
        Map<String, ThreeData> repetitionsItemsList = new LinkedHashMap<>();
        Map<String, Integer> productNameRepetitionList = new LinkedHashMap<>();
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
                    editor.putString("cashclick", "Harneet");
                    editor.putString("sum1", String.valueOf(sum1));

                    editor.apply();

                    CSPreferences.putString(activity, "Status", "CASHPAY");

                    CSPreferences.putString(activity, "sum1", String.valueOf(sum1));
                    Log.e("sum11234", sum1 + "");

//                    SharedPreferences preferences2 = getSharedPreferences("temp", MODE_PRIVATE);
//                    SharedPreferences.Editor editor2 = preferences2.edit();
//                    editor2.putString("afterCasePayItems", lv_sql_products.get(0).getClover_quantity());
//                    editor2.commit();

//                    CSPrefrences.putString((Activity) getApplicationContext(),"afterCasePayItems",lv_sql_products.get(0).getClover_quantity());

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
            Log.e("threedataItem", item+"");
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

}
