
package com.example.haglandroidapp.Models.GetProductListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductListOrderItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("list_price")
    @Expose
    private Integer listPrice;
    @SerializedName("final_price")
    @Expose
    private Integer finalPrice;
    @SerializedName("amount_saved")
    @Expose
    private Integer amountSaved;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("merchant_status")
    @Expose
    private String merchantStatus;
    @SerializedName("product")
    @Expose
    private GetProductListProduct product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getListPrice() {
        return listPrice;
    }

    public void setListPrice(Integer listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getAmountSaved() {
        return amountSaved;
    }

    public void setAmountSaved(Integer amountSaved) {
        this.amountSaved = amountSaved;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(String merchantStatus) {
        this.merchantStatus = merchantStatus;
    }

    public GetProductListProduct getProduct() {
        return product;
    }

    public void setProduct(GetProductListProduct product) {
        this.product = product;
    }

}
