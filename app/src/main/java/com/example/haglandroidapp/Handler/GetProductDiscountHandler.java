package com.example.haglandroidapp.Handler;

import com.example.haglandroidapp.Models.GetProductListModel.GetProductListExample;

public interface GetProductDiscountHandler {

    public void onSuccess(GetProductListExample getProductListExample);

    public void onError(String message);
}
