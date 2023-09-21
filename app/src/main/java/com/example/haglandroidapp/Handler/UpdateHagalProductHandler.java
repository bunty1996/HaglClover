package com.example.haglandroidapp.Handler;

import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductExample;
import com.example.haglandroidapp.Models.UpdateHagalProductModel.UpdateHagalProductExample;

public interface UpdateHagalProductHandler {

    public void onSuccess(UpdateHagalProductExample updateHagalProductExample);

    public void onError(String message);
}
