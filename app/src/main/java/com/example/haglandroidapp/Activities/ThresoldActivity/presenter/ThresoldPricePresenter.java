package com.example.haglandroidapp.Activities.ThresoldActivity.presenter;

import android.app.Activity;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haglandroidapp.Activities.ThresoldActivity.view.ThresoldPriceView;
import com.example.haglandroidapp.Adapters.ThresholdPriceAdapter;
import com.example.haglandroidapp.Handler.GetPostThresoldPriceHandler;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductProduct;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsElement;
import com.example.haglandroidapp.Models.PassThresoldPriceModelList;
import com.example.haglandroidapp.Models.PostThresoldPriceModel.PostProductExample;
import com.example.haglandroidapp.Utils.CSPreferences;
import com.example.haglandroidapp.Utils.Utils;
import com.example.haglandroidapp.Utils.WebServices2;
import com.example.haglandroidapp.R;

import java.util.ArrayList;

public class ThresoldPricePresenter {

    private final Activity activity;
    private final ThresoldPriceView thresoldPriceView;
    public ThresholdPriceAdapter thresholdPriceAdapter;
    public ArrayList<PassThresoldPriceModelList> selectedDataArrayList;

    public ThresoldPricePresenter(Activity activity, ThresoldPriceView thresoldPriceView,
                                  RecyclerView recyclerView, ArrayList<GetTotalInventeryItemsElement> myOrderCarts) {

        this.activity = activity;
        this.thresoldPriceView = thresoldPriceView;
        selectedDataArrayList = new ArrayList<PassThresoldPriceModelList>();
        selectedDataArrayList.clear();

        for (int i = 0; i < myOrderCarts.size(); i++) {

            PassThresoldPriceModelList passThresoldPriceModelList = new PassThresoldPriceModelList();
            passThresoldPriceModelList.setProduct_id(myOrderCarts.get(i).getId());
            passThresoldPriceModelList.setSku("");
            passThresoldPriceModelList.setName(String.valueOf(myOrderCarts.get(i).getName()));
            passThresoldPriceModelList.setRetail_price(String.valueOf(myOrderCarts.get(i).getPrice()));
            passThresoldPriceModelList.setCloverid(String.valueOf(myOrderCarts.get(i).getId()));
            passThresoldPriceModelList.setStock(String.valueOf(myOrderCarts.get(i).getUnitName()));
            passThresoldPriceModelList.setThreshold_price("");
            passThresoldPriceModelList.setChecked(false);
            selectedDataArrayList.add(passThresoldPriceModelList);

        }

        thresholdPriceAdapter = new ThresholdPriceAdapter(activity, myOrderCarts, selectedDataArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(thresholdPriceAdapter);

    }

}
