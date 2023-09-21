
package com.example.haglandroidapp.Models.GetProductListModel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductListOrder {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("total_amount_saved")
    @Expose
    private Integer totalAmountSaved;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("random_number")
    @Expose
    private String randomNumber;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("order_items")
    @Expose
    private ArrayList<GetProductListOrderItem> orderItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalAmountSaved() {
        return totalAmountSaved;
    }

    public void setTotalAmountSaved(Integer totalAmountSaved) {
        this.totalAmountSaved = totalAmountSaved;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public ArrayList<GetProductListOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<GetProductListOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
