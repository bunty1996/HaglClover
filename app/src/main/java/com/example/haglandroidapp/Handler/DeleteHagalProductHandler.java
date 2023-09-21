package com.example.haglandroidapp.Handler;

import com.example.haglandroidapp.Models.DeleteProduct.DeleteProductExample;
import com.example.haglandroidapp.Models.UpdateHagalProductModel.UpdateHagalProductExample;

public interface DeleteHagalProductHandler {

    public void onSuccess(DeleteProductExample deleteProductExample);

    public void onError(String message);
}
