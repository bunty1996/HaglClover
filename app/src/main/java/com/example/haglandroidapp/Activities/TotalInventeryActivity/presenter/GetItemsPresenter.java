package com.example.haglandroidapp.Activities.TotalInventeryActivity.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haglandroidapp.Activities.TotalInventeryActivity.TotalInventoryActivity;
import com.example.haglandroidapp.Activities.TotalInventeryActivity.view.GetInventeryItemsView;
import com.example.haglandroidapp.Adapters.TotalInventoryAdapter;
import com.example.haglandroidapp.Handler.DeleteHagalProductHandler;
import com.example.haglandroidapp.Handler.GetHagalListHandler;
import com.example.haglandroidapp.Handler.GetPostThresoldPriceHandler;
import com.example.haglandroidapp.Handler.GetTotalInventeryItemsHandler;
import com.example.haglandroidapp.Handler.UpdateHagalProductHandler;
import com.example.haglandroidapp.Models.DeleteProduct.DeleteProductExample;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductExample;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductProduct;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsElement;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsExample;
import com.example.haglandroidapp.Models.PassThresoldPriceModelList;
import com.example.haglandroidapp.Models.PostThresoldPriceModel.PostProductExample;
import com.example.haglandroidapp.Models.UpdateHagalProductModel.UpdateHagalProductExample;
import com.example.haglandroidapp.Models.itemsPojo;
import com.example.haglandroidapp.R;
import com.example.haglandroidapp.Utils.CSPreferences;
import com.example.haglandroidapp.Utils.Commons;
import com.example.haglandroidapp.Utils.SharedPref;
import com.example.haglandroidapp.Utils.Utils;
import com.example.haglandroidapp.Utils.WebServices;
import com.example.haglandroidapp.Utils.WebServices2;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GetItemsPresenter {

    private static Activity activity = null;
    private final RecyclerView recyclerView;
    private final GetInventeryItemsView getInventeryItemsView;
    private final GetItemsPresenter getItemsPresenter;
    //    private final EditTextListener editTextListener;
    //    private final ValueChangeListener valueChangeListener;
    public TotalInventoryAdapter inventoryAdapter;
    public ArrayList<GetTotalInventeryItemsElement> productName;
    private List<itemsPojo> sql_arrayList;
    public ArrayList<HagalProductProduct> hagalProductList;

    public ArrayList<String> mainLocalList = new ArrayList<>();
    private ArrayList<HagalProductProduct> selectedDataArrayList;
    //    private ArrayList<HagalProductProduct> selectedCloverStatusTrue;
    private ArrayList<PassThresoldPriceModelList> selectedDataArrayListONSELECTED;
    private ProgressDialog mProgressDialog;


    public GetItemsPresenter(Activity activity, RecyclerView recyclerView,
                             GetInventeryItemsView getInventeryItemsView,
                             TotalInventoryAdapter totalInventoryAdapter, GetItemsPresenter getItemsPresenter) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.getInventeryItemsView = getInventeryItemsView;
        this.inventoryAdapter = totalInventoryAdapter;
        this.getItemsPresenter = getItemsPresenter;
//        this.valueChangeListener = valueChangeListener;


    }


    ///////////getInventeryApi
    public void getInventeryItems() {
        if (Utils.isNetworkConnected(activity)) {
//            getInventeryItemsView.showDialog(activity);
            String authToken = "Bearer " + CSPreferences.readString(activity, "authToken");
            String merchantIDD = CSPreferences.readString(activity, "merchantID");

            String merchantID = SharedPref.getMerchantID(Commons.MERCHANT_ID,"0");
            Log.e("MERCHANTIDD",merchantID);

            String tokenn;
            if (merchantID.equals("41HJH17GJD4H1")) {
                tokenn = Utils.TOKEN22;
            } else if (merchantID.equals("A3KC1QFWWNX71")) {
                tokenn = Utils.TOKEN11;
            } else {
                tokenn = authToken;
            }
            WebServices.getmInstance().getItemsApi(merchantIDD, tokenn, new GetTotalInventeryItemsHandler() {
                @Override
                public void onSuccess(GetTotalInventeryItemsExample getItemsExample) {
                    getInventeryItemsView.hideDialog();
                    if (getItemsExample != null) {
                        productName = getItemsExample.getElements();
                        getHagalListApi(productName);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                mProgressDialog.dismiss();

                                Collections.sort(productName, new Comparator<GetTotalInventeryItemsElement>() {
                                    @Override
                                    public int compare(GetTotalInventeryItemsElement text1, GetTotalInventeryItemsElement text2) {
                                        return text1.getName().compareToIgnoreCase(text2.getName());
                                    }
                                });
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                    productName.sort(Comparator.comparing(GetTotalInventeryItemsElement::getName));
//                                    Log.e("1234567890", String.valueOf(productName));
//                                }
                            }
                        }, 3000);


                    } else {
//                        mProgressDialog.dismiss();
//                        Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
//                    mProgressDialog.dismiss();
//                    getInventeryItemsView.hideDialog();
//                    login_registerView.showMessage(activity, "Please try again");
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    public void filter(String text) {


        // creating a new array list to filter our data.
        ArrayList<HagalProductProduct> filteredlist = new ArrayList<HagalProductProduct>();

        // running a for loop to compare elements.
        for (HagalProductProduct item : selectedDataArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            inventoryAdapter.setFilter(filteredlist);
        }

      /*  final ArrayList<GetTotalInventeryItemsElement> filteredList = new ArrayList<>();

        for (int i = 0; i < productName.size(); i++) {

            final String textselect = productName.get(i).toString();
            if (textselect.contains(text)) {

                GetTotalInventeryItemsElement passInventeryModelList = new GetTotalInventeryItemsElement();
                passInventeryModelList.setName(productName.get(i).getName());
                passInventeryModelList.setCost(productName.get(i).getCost());
                passInventeryModelList.setPrice(productName.get(i).getPrice());
                filteredList.add(passInventeryModelList);
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        inventoryAdapter = new TotalInventoryAdapter(activity, selectedDataArrayList, mainLocalList,
                hagalProductList, selectedDataArrayListONSELECTED, getItemsPresenter);
//        inventoryAdapter.setEditTextChangeListener((TotalInventoryAdapter.EditTextChangeListener) this);
        recyclerView.setAdapter(inventoryAdapter);
        inventoryAdapter.notifyDataSetChanged();*/

        //////////

//        ArrayList<GetTotalInventeryItemsElement> newList = new ArrayList<>();
//        for (GetTotalInventeryItemsElement item : inventoryAdapter.productName) {
//            if (item.getName().toLowerCase().contains(text.toLowerCase()) || (item.getName().toUpperCase().contains(text.toUpperCase()))) {
//                newList.add(item);
//            }
//        }
//        inventoryAdapter.setFilter(newList);
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /////////////upload Hagl Api
    public void updateHagalProductApi(String cloverProductId, String name, String sku, String listPrice, String cloverId,
                                      String unitName, String thresoldPrice) {

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity, "merchantID");
//        String auth_token = Utils.Auth_TOKEN;

        if (sku.equalsIgnoreCase("")) {
            sku = "123456789";
        }

        String searchString = listPrice;

        String merchantIDD = SharedPref.getMerchantID(Commons.MERCHANT_ID,"0");
        Log.e("MERCHANTIDD",merchantIDD);

        if (Utils.isNetworkConnected(activity)) {
            getInventeryItemsView.showDialog(activity);
            WebServices2.getmInstance().updateHagalProductApi(cloverProductId, name, sku, listPrice, cloverId, unitName, merchntId,
                    thresoldPrice, new UpdateHagalProductHandler() {
                        @Override
                        public void onSuccess(UpdateHagalProductExample updateHagalProductExample) {
                            getInventeryItemsView.hideDialog();
                            if (updateHagalProductExample.getProduct() != null) {
                                if (updateHagalProductExample.getSuccess() == true) {

                                    Toast.makeText(activity, "Floor Price Changed Successfully", Toast.LENGTH_SHORT).show();
//                                    activity.finish();

                                } else {
//                            Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
//                        Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(activity, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    /////////////getHagalListApi
    public void getHagalListApi(ArrayList<GetTotalInventeryItemsElement> productName) {


        selectedDataArrayListONSELECTED = new ArrayList<PassThresoldPriceModelList>();
        selectedDataArrayListONSELECTED.clear();

        for (int i = 0; i < productName.size(); i++) {

            PassThresoldPriceModelList passThresoldPriceModelList = new PassThresoldPriceModelList();
            passThresoldPriceModelList.setProduct_id(productName.get(i).getId());
            passThresoldPriceModelList.setSku("");
            passThresoldPriceModelList.setName(String.valueOf(productName.get(i).getName()));
            passThresoldPriceModelList.setRetail_price(String.valueOf(productName.get(i).getPrice()));
            passThresoldPriceModelList.setCloverid(String.valueOf(productName.get(i).getId()));
            passThresoldPriceModelList.setStock(String.valueOf(productName.get(i).getUnitName()));
            passThresoldPriceModelList.setThreshold_price("0");
            passThresoldPriceModelList.setChecked(false);
            selectedDataArrayListONSELECTED.add(passThresoldPriceModelList);

        }

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity, "merchantID");
        String merchantIDD = SharedPref.getMerchantID(Commons.MERCHANT_ID,"0");
        Log.e("MERCHANTIDD",merchantIDD);

        if (Utils.isNetworkConnected(activity)) {
            WebServices2.getmInstance().getHagalListApi(merchntId, new GetHagalListHandler() {
                @Override
                public void onSuccess(HagalProductExample getItemsExample) {
//                    hagalListView.hideDialog();
                    if (getItemsExample != null) {
                        hagalProductList = getItemsExample.getProducts();

                        selectedDataArrayList = new ArrayList<HagalProductProduct>();
                        selectedDataArrayList.clear();

                        ArrayList<String> listLocal1 = new ArrayList<String>();
                        listLocal1.clear();

                        ArrayList<String> list1 = new ArrayList<String>();
                        list1.clear();
                        for (int i = 0; i < productName.size(); i++) {

                            for (int j = 0; j < hagalProductList.size(); j++) {
                                if (productName.get(i).getName().equalsIgnoreCase(hagalProductList.get(j).getName())) {
                                    list1.add(productName.get(i).getName());

                                    HagalProductProduct passThresoldPriceModelList = new HagalProductProduct();
                                    passThresoldPriceModelList.setCloverProductId(String.valueOf(hagalProductList.get(j).getCloverProductId()));
                                    passThresoldPriceModelList.setSku("");
                                    passThresoldPriceModelList.setName(String.valueOf(hagalProductList.get(j).getName()));
                                    passThresoldPriceModelList.setListPrice(productName.get(i).getPrice());
                                    passThresoldPriceModelList.setCloverId(String.valueOf(hagalProductList.get(j).getId()));
                                    passThresoldPriceModelList.setUnitName(String.valueOf(hagalProductList.get(j).getUnitName()));
                                    passThresoldPriceModelList.setFloorPrice(hagalProductList.get(j).getFloorPrice());
                                    passThresoldPriceModelList.setChecked(hagalProductList.get(j).getCloverStatus());
                                    passThresoldPriceModelList.setCloverProductStatus(hagalProductList.get(j).getCloverProductStatus());
                                    if (hagalProductList.get(j).getCloverProductStatus() == true) {
//                                    if (hagalProductList.get(j).getCloverStatus() == true) {
                                        passThresoldPriceModelList.setStatus(1);
                                    } else {
                                        passThresoldPriceModelList.setStatus(2);
                                    }

                                    selectedDataArrayList.add(passThresoldPriceModelList);
                                    break;
                                }
                            }


                        }

                        Log.e("helllooPrint", selectedDataArrayList.size() + "");
                        Log.e("helllooPrintproductName", productName.size() + "");

                        for (int j = 0; j < productName.size(); j++) {
                            if (!(list1.contains(productName.get(j).getName()))) {
                                HagalProductProduct passThresoldPriceModelList = new HagalProductProduct();
                                passThresoldPriceModelList.setCloverProductId(String.valueOf(productName.get(j).getId()));
                                passThresoldPriceModelList.setSku("");
                                passThresoldPriceModelList.setName(String.valueOf(productName.get(j).getName()));
                                passThresoldPriceModelList.setListPrice(productName.get(j).getPrice());
                                passThresoldPriceModelList.setCloverId(String.valueOf(productName.get(j).getId()));
                                passThresoldPriceModelList.setUnitName(String.valueOf(productName.get(j).getUnitName()));

                                if (productName.get(j).getFloorPrice() == null || (productName.get(j).getFloorPrice().equals(""))) {
                                    passThresoldPriceModelList.setFloorPrice(0);
                                    Log.e("floorPrice111", "0");
                                } else {
                                    passThresoldPriceModelList.setFloorPrice(productName.get(j).getFloorPrice());
                                    Log.e("floorPrice111", String.valueOf(productName.get(j).getFloorPrice()));
                                }
                                passThresoldPriceModelList.setChecked(false);
                                passThresoldPriceModelList.setStatus(0);
                                selectedDataArrayList.add(passThresoldPriceModelList);
                            }
                        }

                        for (int i = 0; i < selectedDataArrayList.size(); i++) {
                            listLocal1.add(selectedDataArrayList.get(i).getName());

                        }


                        Collections.sort(listLocal1, new Comparator<String>() {
                            @Override
                            public int compare(String text1, String text2) {
                                return text1.compareToIgnoreCase(text2);
                            }
                        });


                        Collections.sort(selectedDataArrayList, new Comparator<HagalProductProduct>() {
                            @Override
                            public int compare(HagalProductProduct text1, HagalProductProduct text2) {
                                return text1.getName().compareToIgnoreCase(text2.getName());
                            }
                        });

                        //////////////////add Data in SelectedDataArraylistONSELECETED
                        Collections.sort(selectedDataArrayListONSELECTED, new Comparator<PassThresoldPriceModelList>() {
                            @Override
                            public int compare(PassThresoldPriceModelList text1, PassThresoldPriceModelList text2) {
                                return text1.getName().compareToIgnoreCase(text2.getName());
                            }
                        });

//                        selectedDataArrayListONSELECTED.clear();
                        for (int j = 0; j < selectedDataArrayList.size(); j++) {
                            if (listLocal1.contains(selectedDataArrayList.get(j).getName())) {

//                                if (listLocal1.equals(selectedDataArrayList.get(j).getName())) {
                                PassThresoldPriceModelList item = selectedDataArrayListONSELECTED.get(j);
                                item.setThreshold_price(String.valueOf(selectedDataArrayList.get(j).getFloorPrice()));
//                                }
                                /*PassThresoldPriceModelList passThresoldPriceModelList = new PassThresoldPriceModelList();
                                passThresoldPriceModelList.setProduct_id(String.valueOf(selectedDataArrayList.get(j).getId()));
                                passThresoldPriceModelList.setSku("");
                                passThresoldPriceModelList.setName(String.valueOf(selectedDataArrayList.get(j).getName()));
                                passThresoldPriceModelList.setRetail_price(selectedDataArrayList.get(j).getListPrice());
                                passThresoldPriceModelList.setCloverid(String.valueOf(selectedDataArrayList.get(j).getId()));
                                passThresoldPriceModelList.setStock(String.valueOf(selectedDataArrayList.get(j).getUnitName()));

                                if (selectedDataArrayList.get(j).getFloorPrice() == null || (selectedDataArrayList.get(j).getFloorPrice().equals(""))) {
                                    passThresoldPriceModelList.setThreshold_price("0");
                                    Log.e("floorPrice","0");
                                } else {
                                    passThresoldPriceModelList.setThreshold_price(String.valueOf(selectedDataArrayList.get(j).getFloorPrice()));
                                    Log.e("floorPrice", String.valueOf(selectedDataArrayList.get(j).getFloorPrice()));
                                }
                                passThresoldPriceModelList.setChecked(selectedDataArrayList.get(j).isSelected());
//                                passThresoldPriceModelList.sets(0);*/
//                                selectedDataArrayListONSELECTED.add(passThresoldPriceModelList);
                            }

                            Log.e("selectedDataArrayList", selectedDataArrayListONSELECTED.size() + "");
                        }

                        //////////////////////////////

                        mainLocalList.clear();
                        for (int i = 0; i < hagalProductList.size(); i++) {
                            mainLocalList.add(hagalProductList.get(i).getName());

                        }

                        inventoryAdapter = new TotalInventoryAdapter(activity, selectedDataArrayList, mainLocalList,
                                hagalProductList, selectedDataArrayListONSELECTED, getItemsPresenter);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(inventoryAdapter);

                    } else {
//                        Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {

                }
            });
        } else {
            Toast.makeText(activity, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }


    //////////upload Threshold Price
    public void postThresoldItems(ArrayList<PassThresoldPriceModelList> selectedDataArrayList) {

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity, "merchantID");
//        String auth_token = Utils.Auth_TOKEN;

        String authToken = "Bearer " + CSPreferences.readString(activity, "authToken");

        String merchantIDD = SharedPref.getMerchantID(Commons.MERCHANT_ID,"0");
        Log.e("MERCHANTIDD",merchantIDD);

        if (Utils.isNetworkConnected(activity)) {
//            getInventeryItemsView.showDialog(activity);
            WebServices2.getmInstance().postThresoldPriceApi(merchntId, authToken, selectedDataArrayList, new GetPostThresoldPriceHandler() {
                @Override
                public void onSuccess(PostProductExample postProductExample) {
//                    getInventeryItemsView.hideDialog();
//                    if (postProductExample.getProduct() != null) {
                    if (postProductExample.getSuccess() == true) {
                        CSPreferences.putString(activity, "haglStatus", "");
                        CSPreferences.putString(activity, "SingleStatusChange", "");

                        mProgressDialog = new ProgressDialog(activity);
                        mProgressDialog.setMessage("loading info...");
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
                        mProgressDialog.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                Toast.makeText(activity, "Item updated on Hagl", Toast.LENGTH_SHORT).show();
                                TotalInventoryActivity.checkboxList.clear();
                                inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.clear();
//                                inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.clear();
//                                inventoryAdapter.selectedDataArrayListChangeFloorUpload.clear();
//                                inventoryAdapter.selectedDataArrayListONSELECTED.clear();
                                getInventeryItems();
//                                activity.startActivity(activity.getIntent());
                            }
                        }, 3000);

                    } else {
//                        Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
//                    getInventeryItemsView.hideDialog();
//                    login_registerView.showMessage(activity, "Please try again");
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    /////////////////delete hagl product
    public void deleteHagalProductApi(ArrayList<PassThresoldPriceModelList> stringArrayList,
                                      ArrayList<HagalProductProduct> selectedDataArrayListONSELECTEDFINALL) {

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity, "merchantID");
//        String auth_token = Utils.Auth_TOKEN;

        String merchantIDD = SharedPref.getMerchantID(Commons.MERCHANT_ID,"0");
        Log.e("MERCHANTIDD",merchantIDD);

        if (Utils.isNetworkConnected(activity)) {
//            hagalListView.showDialog(activity);
            WebServices2.getmInstance().deleteHagalProductApi(stringArrayList, selectedDataArrayListONSELECTEDFINALL, merchntId, new DeleteHagalProductHandler() {
                @Override
                public void onSuccess(DeleteProductExample deleteProductExample) {
//                    hagalListView.hideDialog();
//                    if (postProductExample.getProduct() != null) {
                    if (deleteProductExample.getSuccess() == true) {

                        CSPreferences.putString(activity, "haglStatus", "");
                        mProgressDialog = new ProgressDialog(activity);
                        mProgressDialog.setMessage("loading info...");
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
                        mProgressDialog.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDialog.dismiss();
                                Toast.makeText(activity, "Item Removed", Toast.LENGTH_SHORT).show();
                                TotalInventoryActivity.checkboxList.clear();
                                inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.clear();
//                                inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.clear();
//                                inventoryAdapter.selectedDataArrayListChangeFloorUpload.clear();
//                                inventoryAdapter.selectedDataArrayListONSELECTED.clear();
                                getInventeryItems();
//                                activity.startActivity(activity.getIntent());
                            }
                        }, 3000);
                       /* Toast.makeText(activity, "Item Removed", Toast.LENGTH_SHORT).show();
//                        activity.finish();*/

                    } else {
//                        Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
//                    hagalListView.hideDialog();
//                    login_registerView.showMessage(activity, "Please try again");
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

}
