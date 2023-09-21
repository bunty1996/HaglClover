
package com.example.haglandroidapp.Models.PostThresoldPriceModel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostProductExample {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("product")
    @Expose
    private ArrayList<PostProductProduct> product;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<PostProductProduct> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<PostProductProduct> product) {
        this.product = product;
    }

}
