package com.example.haglandroidapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DiscountPojo implements Parcelable, Serializable {
    Boolean isSelect  ;
    String discount_id;
    String dis_type;
    String qunatity;
    String service_charge;
    String dis_name;
    String price;
    String detail;
    String products;
    String expires;
    String status;
    String fromwhichScreen ;
    String discount_serverid;
    String usage_type;
    String setLimit;

    public DiscountPojo(){

    }

    public DiscountPojo(Boolean isSelect, String discount_id, String dis_type,
                        String detail, String products, String expires, String status,
                        String dis_name, String qunatity, String service_charge, String price, String fromwhichScreen, String discount_serverid
   , String usage_type, String setLimit ) {
        this.isSelect = isSelect;
        this.discount_id = discount_id;
        this.dis_type = dis_type;
        this.detail = detail;
        this.products = products;
        this.expires = expires;
        this.status = status;
        this.dis_name = dis_name;
        this.qunatity = qunatity;
        this.service_charge = service_charge;

        this.price = price;
        this.fromwhichScreen = fromwhichScreen;
        this.discount_serverid = discount_serverid;
        this.setLimit = setLimit;
        this.usage_type = usage_type;
    }


    protected DiscountPojo(Parcel in) {
        byte tmpIsSelect = in.readByte();
        isSelect = tmpIsSelect == 0 ? null : tmpIsSelect == 1;
        discount_id = in.readString();
        dis_type = in.readString();
        qunatity = in.readString();
        service_charge=in.readString();
        dis_name = in.readString();
        price = in.readString();
        detail = in.readString();
        products = in.readString();
        expires = in.readString();
        status = in.readString();
        fromwhichScreen = in.readString();
        discount_serverid = in.readString();
        usage_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSelect == null ? 0 : isSelect ? 1 : 2));
        dest.writeString(discount_id);
        dest.writeString(dis_type);
        dest.writeString(qunatity);
        dest.writeString(service_charge);
        dest.writeString(dis_name);
        dest.writeString(price);
        dest.writeString(detail);
        dest.writeString(products);
        dest.writeString(expires);
        dest.writeString(status);
        dest.writeString(fromwhichScreen);
        dest.writeString(discount_serverid);
        dest.writeString(usage_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DiscountPojo> CREATOR = new Creator<DiscountPojo>() {
        @Override
        public DiscountPojo createFromParcel(Parcel in) {
            return new DiscountPojo(in);
        }

        @Override
        public DiscountPojo[] newArray(int size) {
            return new DiscountPojo[size];
        }
    };

    @Override
    public String toString() {
        return "DiscountPojo{" +
                "isSelect=" + isSelect +
                ", discount_id='" + discount_id + '\'' +
                ", dis_type='" + dis_type + '\'' +
                ", qunatity='" + qunatity + '\'' +
                ", dis_name='" + dis_name + '\'' +
                ", service_charge='" + service_charge + '\'' +

                ", price='" + price + '\'' +
                ", detail='" + detail + '\'' +
                ", products='" + products + '\'' +
                ", expires='" + expires + '\'' +
                ", status='" + status + '\'' +
                ", fromwhichScreen='" + fromwhichScreen + '\'' +
                ", discount_serverid='" + discount_serverid + '\'' +
                ", usage_type='" + usage_type + '\'' +

                ", setLimit='" + setLimit + '\'' +

                '}';
    }

    public String getFromwhichScreen() {
        return fromwhichScreen;
    }

    public void setFromwhichScreen(String fromwhichScreen) {
        this.fromwhichScreen = fromwhichScreen;
    }

    public String getDiscount_serverid() {
        return discount_serverid;
    }

    public void setDiscount_serverid(String discount_serverid) {
        this.discount_serverid = discount_serverid;
    }

    public String getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(String usage_type) {
        this.usage_type = usage_type;
    }

    public String getSetLimit() {
        return setLimit;
    }

    public void setSetLimit(String setLimit) {
        this.setLimit = setLimit;
    }

    public String getQunatity() {
        return qunatity;
    }

    public void setQunatity(String qunatity) {
        this.qunatity = qunatity;
    }

    public String getDis_name() {
        return dis_name;
    }

    public void setDis_name(String dis_name) {
        this.dis_name = dis_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public String getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(String discount_id) {
        this.discount_id = discount_id;
    }



    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDis_type() {
        return dis_type;
    }

    public void setDis_type(String dis_type) {
        this.dis_type = dis_type;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService_charge() {
        return service_charge;
    }

    public void setService_charge(String service_charge) {
        this.service_charge = service_charge;
    }
}




