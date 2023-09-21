
package com.example.haglandroidapp.Models.GetHagalProductList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HagalProductProduct {

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
    private Object description;
    @SerializedName("list_price")
    @Expose
    private String listPrice;
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
    @SerializedName("qr_code_urls")
    @Expose
    private HagalProductQrCodeUrls qrCodeUrls;

    @SerializedName("unitName")
    @Expose
    private String unitName;

    @SerializedName("clover_status")
    @Expose
    private Boolean cloverStatus;

    @SerializedName("clover_product_status")
    @Expose
    private Boolean cloverProductStatus;

    private int status;

    private String CheckboxStatus;

    private boolean isChecked = false;
    private  boolean isVisible;
    private boolean isSelected;

    public boolean statusFORCHECKBOX = false;

    public boolean isStatusFORCHECKBOX() {
        return statusFORCHECKBOX;
    }

    public void setStatusFORCHECKBOX(boolean statusFORCHECKBOX) {
        this.statusFORCHECKBOX = statusFORCHECKBOX;
    }

    public String getCheckboxStatus() {
        return CheckboxStatus;
    }

    public void setCheckboxStatus(String checkboxStatus) {
        CheckboxStatus = checkboxStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Boolean getCloverStatus() {
        return cloverStatus;
    }

    public void setCloverStatus(Boolean cloverStatus) {
        this.cloverStatus = cloverStatus;
    }

    public Boolean getCloverProductStatus() {
        return cloverProductStatus;
    }

    public void setCloverProductStatus(Boolean cloverProductStatus) {
        this.cloverProductStatus = cloverProductStatus;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }



    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getUnitName() {
        return unitName;

    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

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

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
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

    public Object getCloverProductId() {
        return cloverProductId;
    }

    public void setCloverProductId(String cloverProductId) {
        this.cloverProductId = cloverProductId;
    }

    public Object getCloverId() {
        return cloverId;
    }

    public void setCloverId(String cloverId) {
        this.cloverId = cloverId;
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

    public HagalProductQrCodeUrls getQrCodeUrls() {
        return qrCodeUrls;
    }

    public void setQrCodeUrls(HagalProductQrCodeUrls qrCodeUrls) {
        this.qrCodeUrls = qrCodeUrls;
    }

}
