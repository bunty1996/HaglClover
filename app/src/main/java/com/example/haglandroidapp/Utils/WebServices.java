package com.example.haglandroidapp.Utils;


import com.example.haglandroidapp.Handler.GetTotalInventeryItemsHandler;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsExample;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServices {
    private static final String TAG = "WebServices";
    //    public static final String BASE_URL = "http://93.188.167.68:4604/api/";

    // (AndrewMartin MerchantID)
//    public static final String BASE_URL = "https://apisandbox.dev.clover.com:443/v3/merchants/41HJH17GJD4H1/";

    //    (Hagl MerchantID)
//    public static final String BASE_URL = "https://apisandbox.dev.clover.com:443/v3/merchants/";
//    public static final String BASE_URL = "https://apisandbox.dev.clover.com/v3/merchants/";
//    public static final String BASE_URL ="https://apisandbox.dev.clover.com/v3/merchants/";

//    https://apisandbox.dev.clover.com:443/v3/merchants/A3KC1QFWWNX71/items
//    https://apisandbox.dev.clover.com:443/v3/merchants/

    public static final String BASE_URL = "https://apisandbox.dev.clover.com:443/v3/merchants/";

//    public static final String BASE_URL2 = "https://stage-api.hagglit.com/v1/clover/product/?merchant_id=41HJH17GJD4H1";
    //    private static Retrofit retrofit = null;

    private static WebServices mInstance;
    public static API api;
    public static Retrofit retrofit = null;

    public WebServices() {

        mInstance = this;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(API.class);

        }
    }


    public static WebServices getmInstance() {
        return mInstance;
    }
//    public static WebServices getmInstance2() {
//        return mInstance;
//    }


    ///////////
    public void getItemsApi(String merchant_id, String token, final GetTotalInventeryItemsHandler getItemsHandler) {
        api.getItemsApi(merchant_id, token).enqueue(new Callback<GetTotalInventeryItemsExample>() {
            @Override
            public void onResponse(Call<GetTotalInventeryItemsExample> call, Response<GetTotalInventeryItemsExample> response) {
                if (response != null) {
                    getItemsHandler.onSuccess(response.body());
                } else {
                    String responceData = SocketConnection.convertStreamToString(response.errorBody().byteStream());
                    try {
                        JSONObject jsonObject = new JSONObject(responceData);
                        String message = jsonObject.optString("message");
                        getItemsHandler.onError(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetTotalInventeryItemsExample> call, Throwable t) {
                getItemsHandler.onError(t.getMessage());
            }
        });
    }

    /////////

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


}
