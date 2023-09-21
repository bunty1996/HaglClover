package com.example.haglandroidapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class DiscountPrice implements Parcelable {

    String discount_price ;
    String discount_type ;
    double appleddiscount;
    double state_min;
    double retail_price;
  int   qunatity;
  long cloverItemsCount ;
  String discount_name;
  String action_type;
    long totalstock;


    public DiscountPrice(){}

    public DiscountPrice(String discount_price, String discount_type, double appleddiscount, double state_min, long totalstock, int qunatity, long cloverItemsCount,
                         double retail_price) {
        this.discount_price = discount_price;
        this.discount_type = discount_type;
        this.appleddiscount = appleddiscount;
        this.state_min = state_min;
        this.totalstock = totalstock;
        this.qunatity = qunatity;
        this.retail_price = retail_price;
        this.cloverItemsCount = cloverItemsCount;
    }

    protected DiscountPrice(Parcel in) {
        discount_price = in.readString();
        discount_type = in.readString();
        appleddiscount = in.readDouble();
        state_min = in.readDouble();
        qunatity = in.readInt();
        totalstock=in.readInt();
        cloverItemsCount = in.readLong();
        retail_price = in.readDouble();
        discount_name  = in.readString();
        action_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(discount_price);
        dest.writeString(discount_type);
        dest.writeDouble(appleddiscount);
        dest.writeDouble(state_min);
        dest.writeDouble(totalstock);

        dest.writeInt(qunatity);
        dest.writeLong(cloverItemsCount);
        dest.writeDouble(retail_price);
        dest.writeString(discount_name);
        dest.writeString(action_type);
    }

    public String getDiscount_name() {
        return discount_name;
    }

    public void setDiscount_name(String discount_name) {
        this.discount_name = discount_name;
    }

    public static Creator<DiscountPrice> getCREATOR() {
        return CREATOR;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public double getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(double retail_price) {
        this.retail_price = retail_price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DiscountPrice> CREATOR = new Creator<DiscountPrice>() {
        @Override
        public DiscountPrice createFromParcel(Parcel in) {
            return new DiscountPrice(in);
        }

        @Override
        public DiscountPrice[] newArray(int size) {
            return new DiscountPrice[size];
        }
    };

    @Override
    public String toString() {
        return "DiscountPrice{" +
                "discount_price='" + discount_price + '\'' +
                ", discount_type='" + discount_type + '\'' +
                ", appleddiscount=" + appleddiscount +
                ", state_min=" + state_min +
                ", qunatity=" + qunatity +

                ", totalstock=" + totalstock +
                ", cloverItemsCount=" + cloverItemsCount +
                ", retail_price=" + retail_price +
                ", discount_name=" + discount_name +
                ", action_type=" + action_type +
                '}';
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public double getAppleddiscount() {
        return appleddiscount;
    }

    public void setAppleddiscount(double appleddiscount) {
        this.appleddiscount = appleddiscount;
    }

    public double getState_min() {
        return state_min;
    }

    public void setState_min(double state_min) {
        this.state_min = state_min;
    }

    public int getQunatity() {
        return qunatity;
    }

    public void settotalstock(long totalstock) {
        this.totalstock = totalstock;
    }

    public long gettotalstock() {
        return totalstock;
    }

    public void setQunatity(int totalstock) {
        this.totalstock = totalstock;
    }

    public long getCloverItemsCount() {
        return cloverItemsCount;
    }

    public void setCloverItemsCount(long cloverItemsCount) {
        this.cloverItemsCount = cloverItemsCount;
    }


}
