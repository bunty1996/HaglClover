
package com.example.haglandroidapp.Models.PostThresoldPriceModel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostProductProduct {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("list_price")
    @Expose
    private Integer listPrice;
    @SerializedName("floor_price")
    @Expose
    private Integer floorPrice;
    @SerializedName("barcode_id")
    @Expose
    private Object barcodeId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;
    @SerializedName("sort")
    @Expose
    private Object sort;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("negotiation_factor")
    @Expose
    private Integer negotiationFactor;
    @SerializedName("product_status")
    @Expose
    private String productStatus;
    @SerializedName("product_qr_id")
    @Expose
    private Object productQrId;
    @SerializedName("product_qr_url")
    @Expose
    private Object productQrUrl;
    @SerializedName("inventory_status")
    @Expose
    private String inventoryStatus;
    @SerializedName("clover_product_id")
    @Expose
    private String cloverProductId;
    @SerializedName("clover_id")
    @Expose
    private String cloverId;
    @SerializedName("clover_merchant_id")
    @Expose
    private String cloverMerchantId;
    @SerializedName("product_category")
    @Expose
    private String productCategory;
    @SerializedName("attachments")
    @Expose
    private ArrayList<Object> attachments;
    @SerializedName("store")
    @Expose
    private PostProductStore store;
    @SerializedName("qr_code_urls")
    @Expose
    private PostProductQrCodeUrls__1 qrCodeUrls;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCloverProductId(String cloverProductId) {
        this.cloverProductId = cloverProductId;
    }

    public void setCloverId(String cloverId) {
        this.cloverId = cloverId;
    }

    public Integer getListPrice() {
        return listPrice;
    }

    public void setListPrice(Integer listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(Integer floorPrice) {
        this.floorPrice = floorPrice;
    }

    public Object getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(Object barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
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

    public Integer getNegotiationFactor() {
        return negotiationFactor;
    }

    public void setNegotiationFactor(Integer negotiationFactor) {
        this.negotiationFactor = negotiationFactor;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public Object getProductQrId() {
        return productQrId;
    }

    public void setProductQrId(Object productQrId) {
        this.productQrId = productQrId;
    }

    public Object getProductQrUrl() {
        return productQrUrl;
    }

    public void setProductQrUrl(Object productQrUrl) {
        this.productQrUrl = productQrUrl;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getCloverProductId() {
        return cloverProductId;
    }

    public String getCloverId() {
        return cloverId;
    }

    public String getCloverMerchantId() {
        return cloverMerchantId;
    }

    public void setCloverMerchantId(String cloverMerchantId) {
        this.cloverMerchantId = cloverMerchantId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public ArrayList<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Object> attachments) {
        this.attachments = attachments;
    }

    public PostProductStore getStore() {
        return store;
    }

    public void setStore(PostProductStore store) {
        this.store = store;
    }

    public PostProductQrCodeUrls__1 getQrCodeUrls() {
        return qrCodeUrls;
    }

    public void setQrCodeUrls(PostProductQrCodeUrls__1 qrCodeUrls) {
        this.qrCodeUrls = qrCodeUrls;
    }

}
