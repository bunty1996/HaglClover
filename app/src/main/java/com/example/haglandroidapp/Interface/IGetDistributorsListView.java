package com.example.haglandroidapp.Interface;

import com.android.volley.VolleyError;
import com.example.haglandroidapp.serverhandler.RequestCode;

import org.json.JSONObject;

public interface IGetDistributorsListView {

    void controlGetDistributorsProgressBar(boolean isShowProgressBar);
    void onGetDistributorsSuccess(JSONObject jsonObject, RequestCode requestCode, String isSearched);
    void onGetDistributorsFaillure(VolleyError error);
    void showGetDistributorsErrorMessage(String errorMessage);
}
