package com.example.haglandroidapp.Handler;


import com.example.haglandroidapp.Models.PostThresoldPriceModel.PostProductExample;

public interface GetPostThresoldPriceHandler {

    public void onSuccess( PostProductExample PostProductExample);
    public void onError(String message);
}
