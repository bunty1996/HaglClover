package com.example.haglandroidapp.Activities.HagalItemActivity.presenter;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haglandroidapp.Activities.HagalItemActivity.view.HagalListView;
import com.example.haglandroidapp.Adapters.HagalListAdapter;
import com.example.haglandroidapp.Handler.DeleteHagalProductHandler;
import com.example.haglandroidapp.Handler.GetHagalListHandler;
import com.example.haglandroidapp.Handler.GetTotalInventeryItemsHandler;
import com.example.haglandroidapp.Handler.UpdateHagalProductHandler;
import com.example.haglandroidapp.Models.AddHaglProductLocalModel.AddHaglProductLocalModel;
import com.example.haglandroidapp.Models.DeleteProduct.DeleteProductExample;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductExample;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductProduct;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsElement;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsExample;
import com.example.haglandroidapp.Models.PassThresoldPriceModelList;
import com.example.haglandroidapp.Models.UpdateHagalProductModel.UpdateHagalProductExample;
import com.example.haglandroidapp.Models.itemsPojo;
import com.example.haglandroidapp.R;
import com.example.haglandroidapp.Utils.CSPreferences;
import com.example.haglandroidapp.Utils.Utils;
import com.example.haglandroidapp.Utils.WebServices;
import com.example.haglandroidapp.Utils.WebServices2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class HagalListPresenter {

    private final Activity activity;
    private final RecyclerView recyclerView;
    private final HagalListView hagalListView;
    public HagalListAdapter hagalListAdapter;
    public ArrayList<GetTotalInventeryItemsElement> inventeryProductName;
    public ArrayList<HagalProductProduct> productName;
    private List<itemsPojo> sql_arrayList;

    public ArrayList<AddHaglProductLocalModel> addHaglProductLocalModels = new ArrayList<>();
    private String thresoldPriceValue;


    public HagalListPresenter(Activity activity, RecyclerView recyclerView, HagalListView hagalListView) {

        this.activity = activity;
        this.recyclerView = recyclerView;
        this.hagalListView = hagalListView;
    }

    public void getHagalListApi() {

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity,"merchantID");
        if (Utils.isNetworkConnected(activity)) {
            hagalListView.showDialog(activity);
            WebServices2.getmInstance().getHagalListApi(merchntId, new GetHagalListHandler() {
                @Override
                public void onSuccess(HagalProductExample getItemsExample) {
                    hagalListView.hideDialog();
                    if (getItemsExample != null) {

                        productName = getItemsExample.getProducts();
                        getInventeryItems();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            productName.sort(Comparator.comparing(HagalProductProduct::getName));
                            Log.e("1234567890", String.valueOf(productName));
                        }
                        ////////

                        addHaglProductLocalModels.clear();

                        for (int i = 0; i < productName.size(); i++) {
                            AddHaglProductLocalModel localModel = new AddHaglProductLocalModel();
                            localModel.setId(productName.get(i).getId());
                            localModel.setName(productName.get(i).getName());
                            localModel.setListPrice(Integer.parseInt(productName.get(i).getListPrice()));
                            addHaglProductLocalModels.add(localModel);
                        }

                        Log.e("LocalArraylist", addHaglProductLocalModels.toString());

                        ///////
                        hagalListAdapter = new HagalListAdapter(activity, productName, inventeryProductName);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(hagalListAdapter);
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
//                        Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
                    hagalListView.hideDialog();
//                    login_registerView.showMessage(activity, "Please try again");
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }

    public void filter(String text) {


        final ArrayList<HagalProductProduct> filteredList = new ArrayList<>();

        for (int i = 0; i < productName.size(); i++) {

            final String textselect = productName.get(i).toString();
            if (textselect.contains(text)) {

                HagalProductProduct passInventeryModelList = new HagalProductProduct();
                passInventeryModelList.setName(productName.get(i).getName());
                passInventeryModelList.setListPrice(productName.get(i).getListPrice());
                passInventeryModelList.setFloorPrice(productName.get(i).getFloorPrice());
                filteredList.add(passInventeryModelList);
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        hagalListAdapter = new HagalListAdapter(activity, filteredList, inventeryProductName);
        recyclerView.setAdapter(hagalListAdapter);
        hagalListAdapter.notifyDataSetChanged();

        //////////

        ArrayList<HagalProductProduct> newList = new ArrayList<>();
        for (HagalProductProduct item : hagalListAdapter.productName) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) || (item.getName().toUpperCase().contains(text.toUpperCase()))) {
                newList.add(item);
            }
        }
        hagalListAdapter.setFilter(newList);
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //////////////get Inventery Products

    public void getInventeryItems() {
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
        if (Utils.isNetworkConnected(activity)) {
            WebServices.getmInstance().getItemsApi(merchantID,tokenn, new GetTotalInventeryItemsHandler() {
                @Override
                public void onSuccess(GetTotalInventeryItemsExample getItemsExample) {
                    if (getItemsExample != null) {

                        inventeryProductName = getItemsExample.getElements();

                        hagalListAdapter = new HagalListAdapter(activity, productName, inventeryProductName);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(hagalListAdapter);
                    } else {
//                        Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String message) {
//                    login_registerView.showMessage(activity, "Please try again");
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, R.string.internet_connection, Toast.LENGTH_SHORT).show();
        }

    }


    /////////updateHagalProductApi

//    public void updateHagalProductApi(int positionnn, String productId, ArrayList<PassThresoldPriceModelList> selectedDataArrayList) {

    public void updateHagalProductApi(String cloverProductId, String name, String sku, String listPrice, String cloverId,
                                      String unitName, String thresoldPrice) {

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity,"merchantID");
//        String auth_token = Utils.Auth_TOKEN;

        if (sku.equalsIgnoreCase("")) {
            sku = "123456789";
        }

//        addHaglProductLocalModels

        String searchString = listPrice;


        if (Utils.isNetworkConnected(activity)) {
            hagalListView.showDialog(activity);
            WebServices2.getmInstance().updateHagalProductApi(cloverProductId, name, sku, listPrice, cloverId, unitName, merchntId,
                    thresoldPrice, new UpdateHagalProductHandler() {
                @Override
                public void onSuccess(UpdateHagalProductExample updateHagalProductExample) {
                    hagalListView.hideDialog();
                    if (updateHagalProductExample.getProduct() != null) {
                        if (updateHagalProductExample.getSuccess() == true) {

                            Toast.makeText(activity, "Floor Price Chnaged Successfully", Toast.LENGTH_SHORT).show();
                            activity.finish();

//                    }else {
//                        Toast.makeText(activity, "Null Data", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
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


    ////////////delete Profuct
//    public void deleteHagalProductApi(String cloverProductId) {
    public void deleteHagalProductApi(ArrayList<PassThresoldPriceModelList> cloverProductIdList) {

//        String merchntId = Utils.MERCHANTSID;
        String merchntId = CSPreferences.readString(activity,"merchantID");
//        String auth_token = Utils.Auth_TOKEN;

//        if (Utils.isNetworkConnected(activity)) {
//            hagalListView.showDialog(activity);
//            WebServices2.getmInstance().deleteHagalProductApi(cloverProductIdList, selectedDataArrayListONSELECTEDFINALL, merchntId, new DeleteHagalProductHandler() {
//                @Override
//                public void onSuccess(DeleteProductExample deleteProductExample) {
//                    hagalListView.hideDialog();
////                    if (postProductExample.getProduct() != null) {
//                    if (deleteProductExample.getSuccess() == true) {
//
//                        Toast.makeText(activity, "Item Removed", Toast.LENGTH_SHORT).show();
//                        activity.finish();
//
//                       /* productName = getItemsExample.getElements();
//
//                        inventoryAdapter = new TotalInventoryAdapter(activity, getItemsExample.getElements());
//                        recyclerView.setHasFixedSize(true);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
//                        recyclerView.setAdapter(inventoryAdapter);*/
//
//
//                            /*if (getItemsExample.getIsSuccess() == true) {
////                                getInventeryItemsView.showMessage(activity, getItemsExample.getMessage());
////                            Toast.makeText(activity, getItemsExample.getMessage(), Toast.LENGTH_SHORT).show();
////                                CSPreferences.putString(activity, Utils.TOKEN, getItemsExample.getData().getToken());
//
//                            } else {
////                                forgotPasswordView.showMessage(activity, getItemsExample.getMessage());
//                            }*/
////                    }else {
////                        Toast.makeText(activity, "Null Data", Toast.LENGTH_SHORT).show();
//                    } else {
////                        Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onError(String message) {
//                    hagalListView.hideDialog();
////                    login_registerView.showMessage(activity, "Please try again");
//                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Toast.makeText(activity, R.string.internet_connection, Toast.LENGTH_SHORT).show();
//        }

    }

}
