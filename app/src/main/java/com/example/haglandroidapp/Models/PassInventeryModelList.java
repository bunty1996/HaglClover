package com.example.haglandroidapp.Models;

public class PassInventeryModelList {

    private String id;
    private String name;
    private String stocksData;
    private String retailPrice;
    private boolean isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setName(String productName) {
        this.name = productName;
    }

    public String getStocksData() {
        return stocksData;
    }

    public void setStocksData(String stocksData) {
        this.stocksData = stocksData;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }
}
