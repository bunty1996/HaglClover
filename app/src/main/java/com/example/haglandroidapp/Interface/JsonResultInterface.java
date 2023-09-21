package com.example.haglandroidapp.Interface;


import com.example.haglandroidapp.serverhandler.RequestCode;

import org.json.JSONObject;

public interface JsonResultInterface {

    public void JsonResultSuccess(JSONObject result, RequestCode requestCode) throws Exception;
    public void JsonError(JSONObject result, RequestCode requestCode) throws Exception;
}
