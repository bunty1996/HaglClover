
package com.example.haglandroidapp.Models.GetProductListModel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductListExample {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order")
    @Expose
    private ArrayList<GetProductListOrder> order;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<GetProductListOrder> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<GetProductListOrder> order) {
        this.order = order;
    }

}
