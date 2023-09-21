
package com.example.haglandroidapp.Models.GetHagalProductList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HagalProductQrCodeUrls {

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("small")
    @Expose
    private String small;
    @SerializedName("medium")
    @Expose
    private String medium;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

}
