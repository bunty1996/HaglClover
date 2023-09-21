
package com.example.haglandroidapp.Models.GetTotalInventeryItems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTotalInventeryItemsExample {

    @SerializedName("elements")
    @Expose
    private ArrayList<GetTotalInventeryItemsElement> elements;
    @SerializedName("href")
    @Expose
    private String href;

    public ArrayList<GetTotalInventeryItemsElement> getElements() {
        return elements;
    }

    public void setElements(ArrayList<GetTotalInventeryItemsElement> elements) {
        this.elements = elements;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
