package com.example.haglandroidapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class DeleteLineItemPojo implements Parcelable {
    String line_id;
    String clover_id;
    String item_name;
    int count;

    public DeleteLineItemPojo(){

    }

    protected DeleteLineItemPojo(Parcel in) {
        line_id = in.readString();
        clover_id = in.readString();
        item_name = in.readString();
        count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(line_id);
        dest.writeString(clover_id);
        dest.writeString(item_name);
        dest.writeInt(count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DeleteLineItemPojo> CREATOR = new Creator<DeleteLineItemPojo>() {
        @Override
        public DeleteLineItemPojo createFromParcel(Parcel in) {
            return new DeleteLineItemPojo(in);
        }

        @Override
        public DeleteLineItemPojo[] newArray(int size) {
            return new DeleteLineItemPojo[size];
        }
    };

    @Override
    public String toString() {
        return "DeleteLineItemPojo{" +
                "line_id='" + line_id + '\'' +
                ", clover_id='" + clover_id + '\'' +
                ", item_name='" + item_name + '\'' +
                ", count=" + count +
                '}';
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getClover_id() {
        return clover_id;
    }

    public void setClover_id(String clover_id) {
        this.clover_id = clover_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
