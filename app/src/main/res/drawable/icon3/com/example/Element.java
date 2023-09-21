
package com.example;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Element {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("hidden")
    @Expose
    private Boolean hidden;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("autoManage")
    @Expose
    private Boolean autoManage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alternateName")
    @Expose
    private String alternateName;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("priceType")
    @Expose
    private String priceType;
    @SerializedName("defaultTaxRates")
    @Expose
    private Boolean defaultTaxRates;
    @SerializedName("unitName")
    @Expose
    private String unitName;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("isRevenue")
    @Expose
    private Boolean isRevenue;
    @SerializedName("modifiedTime")
    @Expose
    private Long modifiedTime;
    @SerializedName("priceWithoutVat")
    @Expose
    private Integer priceWithoutVat;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getAutoManage() {
        return autoManage;
    }

    public void setAutoManage(Boolean autoManage) {
        this.autoManage = autoManage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public Boolean getDefaultTaxRates() {
        return defaultTaxRates;
    }

    public void setDefaultTaxRates(Boolean defaultTaxRates) {
        this.defaultTaxRates = defaultTaxRates;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Boolean getIsRevenue() {
        return isRevenue;
    }

    public void setIsRevenue(Boolean isRevenue) {
        this.isRevenue = isRevenue;
    }

    public Long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getPriceWithoutVat() {
        return priceWithoutVat;
    }

    public void setPriceWithoutVat(Integer priceWithoutVat) {
        this.priceWithoutVat = priceWithoutVat;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

}
