package com.example.haglandroidapp.Utils;


import com.example.haglandroidapp.Handler.DeleteHagalProductHandler;
import com.example.haglandroidapp.Handler.GetHagalListHandler;
import com.example.haglandroidapp.Handler.GetPostThresoldPriceHandler;
import com.example.haglandroidapp.Handler.GetProductDiscountHandler;
import com.example.haglandroidapp.Handler.UpdateHagalProductHandler;
import com.example.haglandroidapp.Models.DeleteProduct.DeleteProductExample;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductExample;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductProduct;
import com.example.haglandroidapp.Models.GetProductListModel.GetProductListExample;
import com.example.haglandroidapp.Models.PassThresoldPriceModelList;
import com.example.haglandroidapp.Models.PostThresoldPriceModel.PostProductExample;
import com.example.haglandroidapp.Models.UpdateHagalProductModel.UpdateHagalProductExample;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServices2 {
    private static final String TAG = "WebServices";
    //    public static final String BASE_URL = "http://93.188.167.68:4604/api/";

    //    public static final String BASE_URL = "https://apisandbox.dev.clover.com:443/v3/merchants/41HJH17GJD4H1/";
//    public static final String BASE_URL2 = "https://stage-api.hagglit.com/v1/clover/product/?merchant_id=41HJH17GJD4H1";
    public static final String BASE_URL2 = "https://stage-api.hagglit.com/v1/clover/";
    //    private static Retrofit retrofit = null;

    private static WebServices2 mInstance;
    public static API api;
    public static Retrofit retrofit = null;
    private ArrayList<String> stringArrayList;

    public WebServices2() {
        mInstance = this;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(API.class);

        }
    }

    public static WebServices2 getmInstance() {
        return mInstance;
    }

//    ///////////
//    public void getItemsApi(String token, final GetTotalInventeryItemsHandler getItemsHandler) {
//        api.getItemsApi(token).enqueue(new Callback<GetTotalInventeryItemsExample>() {
//            @Override
//            public void onResponse(Call<GetTotalInventeryItemsExample> call, Response<GetTotalInventeryItemsExample> response) {
//                if (response != null) {
//                    getItemsHandler.onSuccess(response.body());
//                } else {
//                    String responceData = SocketConnection.convertStreamToString(response.errorBody().byteStream());
//                    try {
//                        JSONObject jsonObject = new JSONObject(responceData);
//                        String message = jsonObject.optString("message");
//                        getItemsHandler.onError(message);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetTotalInventeryItemsExample> call, Throwable t) {
//                getItemsHandler.onError(t.getMessage());
//            }
//        });
//    }

    /////////

    public void postThresoldPriceApi(String merchant_id, String auth_token, ArrayList<PassThresoldPriceModelList> selectedDataArrayList,
                                     final GetPostThresoldPriceHandler postThresoldPriceHandler) {

        /*{
            "merchant_id": "ZHE83ZX41HJH17GJD4H1XC9TW1",
                "auth_token": "7y29834y21k3j4h23k4n213k423lk4n12k3n4",
                "elements": [
            {
                "product_id": "GD7JFA7J2VEQ8",
                    "sku": "8734874398",
                    "name": "testing product",
                    "retail_price": "31.00",
                    "cloverid": "GD7JFA7J2VEQ8",
                    "stock": "148",
                    "threshold_price": "23"
            }
  ]
        }*/

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("merchant_id", merchant_id);
        jsonObject.addProperty("auth_token", auth_token);

        JsonArray jsonArray = new Gson().toJsonTree(selectedDataArrayList).getAsJsonArray();
        jsonObject.add("elements", jsonArray);

        api.postThresoldPriceApi(merchant_id, jsonObject).enqueue(new Callback<PostProductExample>() {
            @Override
            public void onResponse(Call<PostProductExample> call, Response<PostProductExample> response) {
                if (response != null) {
                    postThresoldPriceHandler.onSuccess(response.body());
                } else {
                    String responceData = SocketConnection.convertStreamToString(response.errorBody().byteStream());
                    try {
                        JSONObject jsonObject = new JSONObject(responceData);
                        String message = jsonObject.optString("message");
                        postThresoldPriceHandler.onError(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostProductExample> call, Throwable t) {
                postThresoldPriceHandler.onError(t.getMessage());
            }
        });
    }


    //////getHagalListApi

    ///////////
    public void getHagalListApi(String merchant_id, final GetHagalListHandler getHagalListHandler) {
        api.getHagalListApi(merchant_id).enqueue(new Callback<HagalProductExample>() {
            @Override
            public void onResponse(Call<HagalProductExample> call, Response<HagalProductExample> response) {
                if (response != null) {
                    getHagalListHandler.onSuccess(response.body());
                } else {
                    String responceData = SocketConnection.convertStreamToString(response.errorBody().byteStream());
                    try {
                        JSONObject jsonObject = new JSONObject(responceData);
                        String message = jsonObject.optString("message");
                        getHagalListHandler.onError(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HagalProductExample> call, Throwable t) {
                getHagalListHandler.onError(t.getMessage());
            }
        });
    }

//    /////////Update hagal product
//    public void updateHagalProductApi(int positionnn, String product_id, String merchant_id, ArrayList<PassThresoldPriceModelList> selectedDataArrayList,
//                                      final UpdateHagalProductHandler updateHagalProductHandler) {

    public void updateHagalProductApi(String cloverProductId, String name, String sku, String listPrice,
                                      String cloverId, String unitName, String merchntId, String thresoldPrice,
                                      final UpdateHagalProductHandler updateHagalProductHandler) {

       /* {
            "sku": "8734874398",
                "name": "Crompton Fan",
                "retail_price": "200",
                "cloverid": "CMGZTBTRBTV9P",
                "stock": "148",
                "threshold_price": "220"
        }*/

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sku", sku);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("retail_price", listPrice);
        jsonObject.addProperty("cloverid", cloverId);
        jsonObject.addProperty("stock", unitName);
        jsonObject.addProperty("threshold_price", thresoldPrice);

//        JsonArray jsonArray = new Gson().toJsonTree(selectedDataArrayList).getAsJsonArray();
//        jsonObject.add("elements", jsonArray);

        api.updateHagalProductApi(cloverProductId, merchntId, jsonObject).enqueue(new Callback<UpdateHagalProductExample>() {
            @Override
            public void onResponse(Call<UpdateHagalProductExample> call, Response<UpdateHagalProductExample> response) {
                if (response != null) {
                    updateHagalProductHandler.onSuccess(response.body());
                } else {
                    String responceData = SocketConnection.convertStreamToString(response.errorBody().byteStream());
                    try {
                        JSONObject jsonObject = new JSONObject(responceData);
                        String message = jsonObject.optString("message");
                        updateHagalProductHandler.onError(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateHagalProductExample> call, Throwable t) {
                updateHagalProductHandler.onError(t.getMessage());
            }
        });
    }

    //////delete Product

    public void deleteHagalProductApi(ArrayList<PassThresoldPriceModelList> cloverProductIdArraylist,
                                      ArrayList<HagalProductProduct> selectedData, String merchntId,
                                      final DeleteHagalProductHandler deleteHagalProductHandler) {

        stringArrayList = new ArrayList<>();
        stringArrayList.clear();
       /* for (int i = 0; i < cloverProductIdArraylist.size(); i++) {
            String stringToAdd = String.valueOf(cloverProductIdArraylist.get(i).getCloverid());

            stringArrayList.add(stringToAdd);
        }*/

        for (int i = 0; i < selectedData.size(); i++) {
            String stringToAdd = String.valueOf(selectedData.get(i).getCloverProductId());

            stringArrayList.add(stringToAdd);
        }

        JsonObject jsonObject = new JsonObject();

        JsonArray jsonArray = new Gson().toJsonTree(stringArrayList).getAsJsonArray();
        jsonObject.add("product_id", jsonArray);

        api.deleteHagalProductApi(merchntId, jsonObject).enqueue(new Callback<DeleteProductExample>() {
            @Override
            public void onResponse(Call<DeleteProductExample> call, Response<DeleteProductExample> response) {
                if (response != null) {
                    deleteHagalProductHandler.onSuccess(response.body());
                } else {
                    String responceData = SocketConnection.convertStreamToString(response.errorBody().byteStream());
                    try {
                        JSONObject jsonObject = new JSONObject(responceData);
                        String message = jsonObject.optString("message");
                        deleteHagalProductHandler.onError(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteProductExample> call, Throwable t) {
                deleteHagalProductHandler.onError(t.getMessage());
            }
        });
    }

    ///////////getPurchage OrderList

    public void getProductPurchageDiscountApi(String merchntId, String random_number,
                                              final GetProductDiscountHandler getProductDiscountHandler) {

        api.getProductPurchageDiscount(merchntId, random_number).enqueue(new Callback<GetProductListExample>() {

            @Override
            public void onResponse(Call<GetProductListExample> call, Response<GetProductListExample> response) {
                if (response.body() != null) {
                    getProductDiscountHandler.onSuccess(response.body());
                } else {

//                    try {
//                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
//                        String message = jsonObject.optString("message");
//                        getProductDiscountHandler.onError(message);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    String responceData = SocketConnection.convertStreamToString(response.errorBody().byteStream());
                    try {
                        JSONObject jsonObject = new JSONObject(responceData);
                        String message = jsonObject.optString("message");
                        getProductDiscountHandler.onError(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetProductListExample> call, Throwable t) {
                getProductDiscountHandler.onError(t.getMessage());
            }
        });
    }
}
