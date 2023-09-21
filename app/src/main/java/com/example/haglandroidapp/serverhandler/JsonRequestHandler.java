/**
 * Created by ${Raman} on ${04/10/2020}.
 */

package com.example.haglandroidapp.serverhandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.StrictMode;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
//for calling this from main activity
//use getJsonRequest request in myapplication for intialise process

// JsonRequestHandler jsonRequestHandler = MyApplication.getJsonRequest();
public class JsonRequestHandler {


//    ProgressDialog dialog;
//    private String tag_string_req = "string_req";
//    private String tag_json_obj = "jobj_req";
//    String TAG = "Result ";
//    int MY_SOCKET_TIMEOUT_MS = 200000;
//    JsonResultInterface jsonResultInterface;
//
//
//    Activity activity;
//
//    public void postJsonReq(final JsonResultInterface jsonResultInterfaces, Activity activity,  JSONObject object,
//                            final RequestCode request_code,
//                            final boolean isSpinnerRequired) {
//        this.activity = activity;
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                .detectAll()
//                .penaltyLog()
//                .build();
//
//        Log.e("object is ", object.toString());
//        StrictMode.setThreadPolicy(policy);
//        jsonResultInterface = (JsonResultInterface) jsonResultInterfaces;
//        if (isSpinnerRequired) {
//            showProgressDialog();
//        }
//        Log.d("APPURL.SIGNUP>>>", APPURL.SIGNUP_BASEURL + "");
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                APPURL.SIGNUP_BASEURL, object,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("HSONRESULT>>>", response + "");
//                        try {
//                            if (isSpinnerRequired) {
//                                hideProgressDialog();
//                            }
//                            handleResponse(request_code, response);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Log.d(TAG, response.toString());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("APPURLError>>>", error.getMessage() + "");
//                if (isSpinnerRequired) {
//                    dialog.dismiss();
//                }
//                //VolleyLog.d(TAG, "Error: " + error.toString());
//                if (isSpinnerRequired) {
//                    hideProgressDialog();
//                }
//                if (error.toString().contains("NoConnectionError")) {
//                    ShowMessage.message(MyApplication.getAppContext(), "Please check your internet connection");
//
//                } else if (error.toString().contains("ServerError")) {
//                    ShowMessage.message(MyApplication.getAppContext(), "Server not responding");
//                } else {
//
//                    ShowMessage.message(MyApplication.getAppContext(), error.toString());
//                }
//            }
//        }) {
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                return params;
//            }
//        };
//
//
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(jsonObjReq,
//                tag_json_obj);
//
//    }
//
//    private void handleResponse(RequestCode request_code, JSONObject response) throws JSONException {
//        try {
//           // if (response.getString(Commons.STATUS).equals("1")) {
//
//                jsonResultInterface.JsonResultSuccess(response, request_code);
//
//           // } else {
//               // jsonResultInterface.JsonError(response, request_code);
//           // }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void showProgressDialog() {
//        dialog = new ProgressDialog(activity);
//        dialog.setMessage("Please Wait...");
//        dialog.show();
//    }
//
//    public void hideProgressDialog() {
//        if (dialog.isShowing()) {
//            dialog.dismiss();
//        }
//    }


}
