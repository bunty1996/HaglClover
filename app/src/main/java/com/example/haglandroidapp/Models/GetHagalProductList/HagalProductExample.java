
package com.example.haglandroidapp.Models.GetHagalProductList;

import java.util.ArrayList;

import com.example.haglandroidapp.Models.UpdateHagalProductModel.UpdateHagalProductProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HagalProductExample {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("products")
    @Expose
    private ArrayList<HagalProductProduct> products;
    @SerializedName("count")
    @Expose
    private String count;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<HagalProductProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<HagalProductProduct> products) {
        this.products = products;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
