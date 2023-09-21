package com.example.haglandroidapp.Models;

public class PassThresoldPriceModelList {

    /*"product_id": "GD7JFA7J2VEQ9",
            "sku": "8734874398",
            "name": "testing product2",
            "retail_price": "31.00",
            "cloverid": "GD7JFA7J2VEQ9",
            "stock": "148",
            "threshold_price": "25"*/

    private String product_id;
    private String sku;
    private String name;
    private String retail_price;
    private String cloverid;
    private String stock;
    private String threshold_price;
    private Boolean cloverStatus;

    private String CheckboxStatus;

    private boolean isChecked = false;
    private boolean checboxxxxxxSelected = false;


    private boolean clover_product_status = false;

    public boolean isClover_product_status() {
        return clover_product_status;
    }

    public void setClover_product_status(boolean clover_product_status) {
        this.clover_product_status = clover_product_status;
    }

    public void setChecboxxxxxxSelected(boolean checboxxxxxxSelected) {
        this.checboxxxxxxSelected = checboxxxxxxSelected;
    }

    public boolean isChecboxxxxxxSelected() {
        return checboxxxxxxSelected;
    }

    public String getCheckboxStatus() {
        return CheckboxStatus;
    }

    public void setCheckboxStatus(String checkboxStatus) {
        CheckboxStatus = checkboxStatus;
    }


    public Boolean getCloverStatus() {
        return cloverStatus;
    }

    public void setCloverStatus(Boolean cloverStatus) {
        this.cloverStatus = cloverStatus;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(String retail_price) {
        this.retail_price = retail_price;
    }

    public String getCloverid() {
        return cloverid;
    }

    public void setCloverid(String cloverid) {
        this.cloverid = cloverid;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getThreshold_price() {
        return threshold_price;
    }

    public void setThreshold_price(String threshold_price) {
        this.threshold_price = threshold_price;
    }
}
