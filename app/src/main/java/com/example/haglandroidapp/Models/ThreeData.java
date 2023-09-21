package com.example.haglandroidapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ThreeData implements Parcelable, Comparable<ThreeData> {

    String lineItems;
    double price;
    int how_many;

    protected ThreeData(Parcel in) {
        lineItems = in.readString();
        price = in.readDouble();
        how_many = in.readInt();
    }

    public static final Creator<ThreeData> CREATOR = new Creator<ThreeData>() {
        @Override
        public ThreeData createFromParcel(Parcel in) {
            return new ThreeData(in);
        }

        @Override
        public ThreeData[] newArray(int size) {
            return new ThreeData[size];
        }
    };

    @Override
    public String toString() {
        return "ThreeData{" +
                "lineItems='" + lineItems + '\'' +
                ", price=" + price +
                ", how_many=" + how_many +
                '}';
    }

    public ThreeData(){}

    public ThreeData(String lineItems, double price, int how_many) {
        this.lineItems = lineItems;
        this.price = price;

        this.how_many = how_many;
    }

    public String getLineItems() {
        return lineItems;
    }

    public void setLineItems(String lineItems) {
        this.lineItems = lineItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getHow_many() {
        return how_many;
    }

    public void setHow_many(int how_many) {
        this.how_many = how_many;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(lineItems);
        parcel.writeDouble(price);
        parcel.writeInt(how_many);
    }

    @Override
    public int compareTo(ThreeData threeData) {
        return 0;
    }
}

