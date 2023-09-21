package com.example.haglandroidapp.Handler;


import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductExample;

public interface GetHagalListHandler {

    public void onSuccess( HagalProductExample PostProductExample);
    public void onError(String message);
}
