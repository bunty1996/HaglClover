package com.example.haglandroidapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class itemsPojo implements Parcelable, Serializable {
    boolean isProductselected = false;
    boolean isSelectedItemDeleted = false;

    boolean isEnabled = false;
    boolean isVisiblity = false;
    String primary_id;
    String uid;
    String product_name;
    String product_id;
    String size;
    String tax;

    String upc;
    String category;
    String distributor="";
    String retail_price;
    String case_cost;
    String units;
    String bottle_cost;
    String discount_price;
    String discount_quantity;
    String clover_quantity;
    String total_quantity;
    String stock;
    String qty_soldas;
    double total_price;


    String state_min="Enter State min";
    String clover_id;
    String per_type;
    int addcart;
    String limit;
    String usage;
    String discount_name;

    public itemsPojo() {

    }

    public itemsPojo(String qty_soldas, String tax, String usage, String total_quantity, String limit, boolean isProductselected, boolean isSelectedItemDeleted, String product_id, String primary_id, String uid, String product_name, String size, String upc, String category, String retail_price, String case_cost, String units, String bottle_cost, String discount_price, String discount_quantity, String clover_quantity, double total_price, String state_min, String clover_id, String per_type, String stock, String distributor, int is_addcart, String discount_name) {
        this.isProductselected = isProductselected;
        this.isSelectedItemDeleted = isSelectedItemDeleted;
        this.primary_id = primary_id;
        this.tax = tax;

        this.uid = uid;
        this.product_name = product_name;
        this.size = size;
        this.upc = upc;
        this.category = category;
        this.retail_price = retail_price;
        this.case_cost = case_cost;
        this.units = units;
        this.bottle_cost = bottle_cost;
        this.discount_price = discount_price;
        this.discount_quantity = discount_quantity;
        this.clover_quantity = clover_quantity;
        this.total_price = total_price;
        this.state_min = state_min;
        this.clover_id = clover_id;
        this.per_type = per_type;
        this.stock = stock;
        this.distributor = distributor;
        this. product_id = product_id;
        this.addcart = is_addcart;
        this.limit = limit;
        this.usage = usage;
        this.discount_name = discount_name;
        this.qty_soldas =qty_soldas;
        this.total_quantity = total_quantity;
    }

    protected itemsPojo(Parcel in) {
        isProductselected = in.readByte() != 0;
        isSelectedItemDeleted = in.readByte() != 0;
        isEnabled = in.readByte() != 0;
        isVisiblity = in.readByte() != 0;
        primary_id = in.readString();
        uid = in.readString();
        product_name = in.readString();
        product_id = in.readString();
        size = in.readString();
        tax = in.readString();

        upc = in.readString();
        category = in.readString();
        distributor = in.readString();
        retail_price = in.readString();
        case_cost = in.readString();
        units = in.readString();
        bottle_cost = in.readString();
        discount_price = in.readString();
        discount_quantity = in.readString();
        clover_quantity = in.readString();
        stock = in.readString();
        qty_soldas = in.readString();
        total_price = in.readDouble();
        state_min = in.readString();
        clover_id = in.readString();
        per_type = in.readString();
        addcart = in.readInt();
        limit = in.readString();
        usage = in.readString();
        discount_name = in.readString();
        total_quantity = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isProductselected ? 1 : 0));
        dest.writeByte((byte) (isSelectedItemDeleted ? 1 : 0));
        dest.writeByte((byte) (isEnabled ? 1 : 0));
        dest.writeByte((byte) (isVisiblity ? 1 : 0));
        dest.writeString(primary_id);
        dest.writeString(uid);
        dest.writeString(tax);

        dest.writeString(product_name);
        dest.writeString(product_id);
        dest.writeString(size);
        dest.writeString(upc);
        dest.writeString(category);
        dest.writeString(distributor);
        dest.writeString(retail_price);
        dest.writeString(case_cost);
        dest.writeString(units);
        dest.writeString(bottle_cost);
        dest.writeString(discount_price);
        dest.writeString(discount_quantity);
        dest.writeString(clover_quantity);
        dest.writeString(stock);
        dest.writeString(qty_soldas);
        dest.writeDouble(total_price);
        dest.writeString(state_min);
        dest.writeString(clover_id);
        dest.writeString(per_type);
        dest.writeInt(addcart);
        dest.writeString(limit);
        dest.writeString(usage);
        dest.writeString(discount_name);
        dest.writeString(total_quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<itemsPojo> CREATOR = new Creator<itemsPojo>() {
        @Override
        public itemsPojo createFromParcel(Parcel in) {
            return new itemsPojo(in);
        }

        @Override
        public itemsPojo[] newArray(int size) {
            return new itemsPojo[size];
        }
    };

    public String getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(String total_quantity) {
        this.total_quantity = total_quantity;
    }

    public boolean isProductselected() {
        return isProductselected;
    }

    public void setProductselected(boolean productselected) {
        isProductselected = productselected;
    }

    public boolean isSelectedItemDeleted() {
        return isSelectedItemDeleted;
    }

    public void setSelectedItemDeleted(boolean selectedItemDeleted) {
        isSelectedItemDeleted = selectedItemDeleted;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isVisiblity() {
        return isVisiblity;
    }

    public void setVisiblity(boolean visiblity) {
        isVisiblity = visiblity;
    }

    public String getPrimary_id() {
        return primary_id;
    }

    public void setPrimary_id(String primary_id) {
        this.primary_id = primary_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(String retail_price) {
        this.retail_price = retail_price;
    }

    public String getCase_cost() {
        return case_cost;
    }

    public void setCase_cost(String case_cost) {
        this.case_cost = case_cost;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getBottle_cost() {
        return bottle_cost;
    }

    public void setBottle_cost(String bottle_cost) {
        this.bottle_cost = bottle_cost;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getDiscount_quantity() {
        return discount_quantity;
    }

    public void setDiscount_quantity(String discount_quantity) {
        this.discount_quantity = discount_quantity;
    }

    public String getClover_quantity() {
        return clover_quantity;
    }

    public void setClover_quantity(String clover_quantity) {
        this.clover_quantity = clover_quantity;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getQty_soldas() {
        return qty_soldas;
    }

    public void setQty_soldas(String qty_soldas) {
        this.qty_soldas = qty_soldas;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getState_min() {
        return state_min;
    }

    public void setState_min(String state_min) {
        this.state_min = state_min;
    }

    public String getClover_id() {
        return clover_id;
    }

    public void setClover_id(String clover_id) {
        this.clover_id = clover_id;
    }

    public String getPer_type() {
        return per_type;
    }

    public void setPer_type(String per_type) {
        this.per_type = per_type;
    }

    public int getAddcart() {
        return addcart;
    }

    public void setAddcart(int addcart) {
        this.addcart = addcart;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getDiscount_name() {
        return discount_name;
    }public String getTax() {
        return tax;
    }

    public void setDiscount_name(String discount_name) {
        this.discount_name = discount_name;
    }
    public void setTax(String tax) {
        this.tax = tax;
    }


    @Override
    public String toString() {
        return "itemsPojo{" +
                "isProductselected=" + isProductselected +
                ", isSelectedItemDeleted=" + isSelectedItemDeleted +
                ", isEnabled=" + isEnabled +
                ", isVisiblity=" + isVisiblity +
                ", primary_id='" + primary_id + '\'' +
                ", uid='" + uid + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", size='" + size + '\'' +
                ", upc='" + upc + '\'' +
                ", category='" + category + '\'' +
                ", tax='" + tax + '\'' +

                ", distributor='" + distributor + '\'' +
                ", retail_price='" + retail_price + '\'' +
                ", case_cost='" + case_cost + '\'' +
                ", units='" + units + '\'' +
                ", bottle_cost='" + bottle_cost + '\'' +
                ", discount_price='" + discount_price + '\'' +
                ", discount_quantity='" + discount_quantity + '\'' +
                ", clover_quantity='" + clover_quantity + '\'' +
                ", stock='" + stock + '\'' +
                ", qty_soldas='" + qty_soldas + '\'' +
                ", total_price=" + total_price +
                ", state_min='" + state_min + '\'' +
                ", clover_id='" + clover_id + '\'' +
                ", per_type='" + per_type + '\'' +
                ", addcart=" + addcart +
                ", limit='" + limit + '\'' +
                ", usage='" + usage + '\'' +
                ", discount_name='" + discount_name + '\'' +
                ", total_quantity='" + total_quantity + '\'' +

                '}';
    }
}
