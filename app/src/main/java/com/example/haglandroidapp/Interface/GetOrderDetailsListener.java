package com.example.haglandroidapp.Interface;

import com.clover.sdk.v3.order.Order;

import java.util.List;

public interface GetOrderDetailsListener {
    // you can define any parameter as per your requirement
    public void callback(Order result, List<String> lv_data, List<String> lv_discountsa, String orderid, String whichTask);


}
