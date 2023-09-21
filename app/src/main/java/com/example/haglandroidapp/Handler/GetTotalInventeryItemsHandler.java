package com.example.haglandroidapp.Handler;

import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsExample;

public interface GetTotalInventeryItemsHandler {

    public void onSuccess(GetTotalInventeryItemsExample addAddressExample);
    public void onError(String message);
}
