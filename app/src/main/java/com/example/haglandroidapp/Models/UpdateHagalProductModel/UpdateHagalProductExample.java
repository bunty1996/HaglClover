
package com.example.haglandroidapp.Models.UpdateHagalProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateHagalProductExample {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("product")
    @Expose
    private UpdateHagalProductProduct product;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public UpdateHagalProductProduct getProduct() {
        return product;
    }

    public void setProduct(UpdateHagalProductProduct product) {
        this.product = product;
    }

}
