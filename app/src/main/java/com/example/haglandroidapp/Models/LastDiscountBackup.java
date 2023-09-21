package com.example.haglandroidapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class LastDiscountBackup implements Parcelable {
    String bkup_discountid;
    String bkup_discountname;
    String bkup_discounttype;
    String bkup_discount;


    public LastDiscountBackup(){

    }
    public LastDiscountBackup(String bkup_discountid, String bkup_discountname, String bkup_discounttype, String bkup_discount) {
        this.bkup_discountid = bkup_discountid;
        this.bkup_discountname = bkup_discountname;
        this.bkup_discounttype = bkup_discounttype;
        this.bkup_discount = bkup_discount;
    }

    protected LastDiscountBackup(Parcel in) {
        bkup_discountid = in.readString();
        bkup_discountname = in.readString();
        bkup_discounttype = in.readString();
        bkup_discount = in.readString();
    }

    public static final Parcelable.Creator<LastDiscountBackup> CREATOR = new Creator<LastDiscountBackup>() {
        @Override
        public LastDiscountBackup createFromParcel(Parcel in) {
            return new LastDiscountBackup(in);
        }

        @Override
        public LastDiscountBackup[] newArray(int size) {
            return new LastDiscountBackup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bkup_discountid);
        parcel.writeString(bkup_discountname);
        parcel.writeString(bkup_discounttype);
        parcel.writeString(bkup_discount);
    }

    public String getBkup_discountid() {
        return bkup_discountid;
    }

    public void setBkup_discountid(String bkup_discountid) {
        this.bkup_discountid = bkup_discountid;
    }

    public String getBkup_discountname() {
        return bkup_discountname;
    }

    public void setBkup_discountname(String bkup_discountname) {
        this.bkup_discountname = bkup_discountname;
    }

    public String getBkup_discounttype() {
        return bkup_discounttype;
    }

    public void setBkup_discounttype(String bkup_discounttype) {
        this.bkup_discounttype = bkup_discounttype;
    }

    public String getBkup_discount() {
        return bkup_discount;
    }

    public void setBkup_discount(String bkup_discount) {
        this.bkup_discount = bkup_discount;
    }

    public static Creator<LastDiscountBackup> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "LastDiscountBackup{" +
                "bkup_discountid='" + bkup_discountid + '\'' +
                ", bkup_discountname='" + bkup_discountname + '\'' +
                ", bkup_discounttype='" + bkup_discounttype + '\'' +
                ", bkup_discount=" + bkup_discount +
                '}';
    }
}
