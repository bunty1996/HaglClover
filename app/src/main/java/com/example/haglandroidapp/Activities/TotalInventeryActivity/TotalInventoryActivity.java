package com.example.haglandroidapp.Activities.TotalInventeryActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haglandroidapp.Activities.TotalInventeryActivity.presenter.GetItemsPresenter;
import com.example.haglandroidapp.Activities.TotalInventeryActivity.view.GetInventeryItemsView;
import com.example.haglandroidapp.Activities.TotalInventeryActivity.view.ValueChangeListener;
import com.example.haglandroidapp.Adapters.TotalInventoryAdapter;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductProduct;
import com.example.haglandroidapp.Models.PassThresoldPriceModelList;
import com.example.haglandroidapp.R;
import com.example.haglandroidapp.Utils.CSPreferences;
import com.example.haglandroidapp.Utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TotalInventoryActivity extends AppCompatActivity implements View.OnClickListener,
        GetInventeryItemsView {

    private RecyclerView recyclerView;
    private TextView txt_next;
    private Activity activity;
    private TotalInventoryAdapter totalInventoryAdapter;
    private EditText tool_searchBar;
    private CheckBox main_Checkbox;
    private ImageView iv_close;
    private GetItemsPresenter getItemsPresenter;
    private String thresoldPriceValue;
    ValueChangeListener valueChangeListener;

    private long onRecentBackPressedTime;

    String enteredValue;
    private List<String> editTextValues = new ArrayList<>();
    private ArrayList<String> dataList = new ArrayList<>();
    private int currentEditTextPosition = -1;
    private String statusss = "";

    public static ArrayList<String> checkboxList = new ArrayList<>();
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_hagl_inventory);
        hideKeyboard(this);
        activity = this;
        init();
        listiners();
        checkboxList.clear();

        getItemsPresenter = new GetItemsPresenter(activity, recyclerView, this,
                totalInventoryAdapter, getItemsPresenter);
        getItemsPresenter.getInventeryItems();

    }

    private void init() {

        recyclerView = findViewById(R.id.recyclerView);
        txt_next = findViewById(R.id.txt_next);
        main_Checkbox = findViewById(R.id.mainCheckBox);
        tool_searchBar = findViewById(R.id.tool_searchBar);
        iv_close = findViewById(R.id.iv_clear_search);
        iv_close.setVisibility(View.INVISIBLE);

        /////////////////Main CheckBox
        main_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main_Checkbox.isChecked()) {
//                    CSPreferences.putString(activity, "Multiselect", "true");
//                    Log.e("tttttttttt", "true");
                    getItemsPresenter.inventoryAdapter.selectAll();
//                    totalInventoryAdapter.selectedDataArrayList.size();
                } else {
                    getItemsPresenter.inventoryAdapter.unselectall();
//                    CSPreferences.putString(activity, "Multiselect", "");
//                    main_Checkbox.setChecked(false);
//                    getItemsPresenter.inventoryAdapter.productNameLocal.clear();
                    Log.e("tttttttttt", "");
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tool_searchBar.setText("");
                //  OFFSET = 0;
                // hitGetDistributorsListDataFromServer(OFFSET, LIMIT, "", Commons.VIEW_ALL);
                iv_close.setVisibility(View.INVISIBLE);
                getItemsPresenter.getInventeryItems();
            }
        });

        // Add Text Change Listener to EditText
        tool_searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charText, int start, int before, int count) {
//                if (tool_searchBar.getText().toString().trim().length() == 0) {
//                    iv_close.setVisibility(View.INVISIBLE);
                getItemsPresenter.filter(charText.toString());


//                } else {
//                    iv_close.setVisibility(View.VISIBLE);
//                }
                ///////

                ///////
            }

            ;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void listiners() {
        txt_next.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == txt_next) {
            Log.e("selectedHaglList", String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTED));
            String chechStatus = null;
//            chechStatus = CSPreferences.readString(activity, "haglStatus");
            if (getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.size() == 0) {
//                Toast.makeText(activity, "No changes made.  Nothing to update....", Toast.LENGTH_SHORT).show();
                chechStatus = CSPreferences.readString(activity, "haglStatus");
                String SingleStatusChange = CSPreferences.readString(activity, "SingleStatusChange");
                Log.e("checkstatus", chechStatus);
                Log.e("SingleStatusChange", SingleStatusChange);
                if (SingleStatusChange.equalsIgnoreCase("SingleStatusChangeTrue")) {
//                    Log.e("listing", String.valueOf(getItemsPresenter.inventoryAdapter.strings));
                    if (getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.size() >= 1) {

                        ArrayList<PassThresoldPriceModelList> selectedDataArrayListSelected = new ArrayList<>();
                        selectedDataArrayListSelected.clear();


                        for (int idx = 0; idx < getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.size(); idx++) {
                            if (getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).isClover_product_status() == true) {

                                PassThresoldPriceModelList passThresoldPriceModelList22 = new PassThresoldPriceModelList();
                                String stringToAdd = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getProduct_id()); // Replace `yourArray` with the actual array name
                                // Add the string to the ArrayList
                                passThresoldPriceModelList22.setCloverid(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getProduct_id()));
                                passThresoldPriceModelList22.setSku("");
                                passThresoldPriceModelList22.setName(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getName()));

                                passThresoldPriceModelList22.setRetail_price(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getRetail_price());
                                passThresoldPriceModelList22.setProduct_id(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getProduct_id()));
                                passThresoldPriceModelList22.setStock(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getStock()));
                                passThresoldPriceModelList22.setThreshold_price(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getThreshold_price()));
                                passThresoldPriceModelList22.setClover_product_status(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).isClover_product_status());

                                selectedDataArrayListSelected.add(passThresoldPriceModelList22);
                            } else {

                            }
//                            break;
                        }
//                            }
////                                break;
//                        }
//                        }
                        Log.e("showArraylistSize", String.valueOf(selectedDataArrayListSelected.size()));

                        showcustomdialog(selectedDataArrayListSelected);

                    } else if (!(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload == null)) {

                        ArrayList<PassThresoldPriceModelList> selectedDataArrayListFinal = new ArrayList<>();
                        selectedDataArrayListFinal.clear();
                        boolean isEmptyDataCheck = false;
                        boolean emptyPrice = false;
                        boolean lessthanPrice = false;

                        for (int i = 0; i < getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.size(); i++) {
//                        for (int j = 0; j < getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTED.size(); j++) {
//                        if (getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).isChecked()) {
                            isEmptyDataCheck = true;
                            String str = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).getThreshold_price());
                            String number = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).getRetail_price());

                            String str11 = number;
                            String strNew = str11.replace("$", "");

                            double value1 = Double.parseDouble(strNew);

                            if (str == null || str.isEmpty()) {
                                emptyPrice = true;
                            } else {

                                double value2 = Double.parseDouble(str);

                                if (!(value1 > value2 &&
                                        value1 != value2 && value2 != 0)) {
                                    lessthanPrice = true;

                                } else {
                                    selectedDataArrayListFinal.add(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i));
                                }
                            }
//                        }
//                        }
                        }

                        if (!isEmptyDataCheck) {
                            Toast.makeText(activity, "No changes made.  Nothing to update.", Toast.LENGTH_SHORT).show();
                        } else if (emptyPrice) {
                            Toast.makeText(activity, "Please enter Floor Price", Toast.LENGTH_SHORT).show();
                        } else if (lessthanPrice) {
                            Toast.makeText(activity, "Floor price should be less than the Retail Price", Toast.LENGTH_SHORT).show();
                        } else {

                            ArrayList<PassThresoldPriceModelList> selectedDataArrayList = new ArrayList<>();
                            selectedDataArrayList.clear();

                            for (int i = 0; i < getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.size(); i++) {

                                PassThresoldPriceModelList passThresoldPriceModelList22 = new PassThresoldPriceModelList();
                                String stringToAdd = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).getProduct_id()); // Replace `yourArray` with the actual array name
                                // Add the string to the ArrayList
                                Log.e("stringToAdd", stringToAdd);

                                passThresoldPriceModelList22.setCloverid(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).getProduct_id()));
                                passThresoldPriceModelList22.setSku("");
                                passThresoldPriceModelList22.setName(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).getName()));


                                passThresoldPriceModelList22.setRetail_price(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).getRetail_price());
                                passThresoldPriceModelList22.setProduct_id(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).getProduct_id()));
                                passThresoldPriceModelList22.setStock(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).getStock()));
                                passThresoldPriceModelList22.setThreshold_price(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(i).getThreshold_price()));
                                passThresoldPriceModelList22.setClover_product_status(true);

                                selectedDataArrayList.add(passThresoldPriceModelList22);
                            }
                            showcustomdialog(selectedDataArrayList);
                        }
//                    showcustomdialog(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload);
                    } else {
                        Toast.makeText(activity, "No changes made. Nothing to update....", Toast.LENGTH_SHORT).show();
                    }

                    ///////////////////////Save only floor price without Hagl upload Item
                } else if (SingleStatusChange.equalsIgnoreCase("SingleStatusChangeFalse")) {
                    if (getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.size() >= 1) {

                        ArrayList<PassThresoldPriceModelList> selectedDataArrayListSelected = new ArrayList<>();
                        selectedDataArrayListSelected.clear();

                        for (int idx = 0; idx < getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.size(); idx++) {
                            if (getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).isClover_product_status() == false) {

                                PassThresoldPriceModelList passThresoldPriceModelList22 = new PassThresoldPriceModelList();
                                String stringToAdd = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getProduct_id()); // Replace `yourArray` with the actual array name
                                // Add the string to the ArrayList
                                passThresoldPriceModelList22.setCloverid(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getProduct_id()));
                                passThresoldPriceModelList22.setSku("");
                                passThresoldPriceModelList22.setName(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getName()));

                                passThresoldPriceModelList22.setRetail_price(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getRetail_price());
                                passThresoldPriceModelList22.setProduct_id(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getProduct_id()));
                                passThresoldPriceModelList22.setStock(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getStock()));
                                passThresoldPriceModelList22.setThreshold_price(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).getThreshold_price()));
                                passThresoldPriceModelList22.setClover_product_status(getItemsPresenter.inventoryAdapter.selectedDataArrayListChangeFloorUpload.get(idx).isClover_product_status());

                                selectedDataArrayListSelected.add(passThresoldPriceModelList22);
                            }
                        }

                        Log.e("showArraylistSize", String.valueOf(selectedDataArrayListSelected.size()));

                        showcustomdialog(selectedDataArrayListSelected);
                    }

                } else {
                    Toast.makeText(activity, "No changes made. Nothing to update....", Toast.LENGTH_SHORT).show();
                }
//                for (int i = 0; i < getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTED.size(); i++) {
////                    Log.e("ertghj", String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayList.get(i).getFloorPrice()));
//                }

            } else if (getItemsPresenter.inventoryAdapter.typeOfItemHagl == ("DELETE")) {
//                Toast.makeText(activity, "DELETE VALUE", Toast.LENGTH_SHORT).show();

                showcustomdialog2(getItemsPresenter.inventoryAdapter.stringArrayList, getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL);

            } else {
                chechStatus = CSPreferences.readString(activity, "haglStatus");
                Log.e("checkstatus", chechStatus);
                if (chechStatus.equalsIgnoreCase("HAGLUPLOAD")) {

//                    Toast.makeText(activity, "HAGLUPLOAD VALUE", Toast.LENGTH_SHORT).show();

                    ArrayList<HagalProductProduct> selectedDataArrayListFinal = new ArrayList<>();
                    selectedDataArrayListFinal.clear();
                    boolean isEmptyDataCheck = false;
                    boolean emptyPrice = false;
                    boolean lessthanPrice = false;
                    String str = null;

//                    for (int j = 0; j < getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTED.size(); j++) {
                    for (int i = 0; i < getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.size(); i++) {

                        // Iterate over the secondArrayList to get the names
                        for (HagalProductProduct secondObject : getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL) {
                            String nameToSearch = secondObject.getName();
                            String priceToAdd = "0"; // Default price if no match is found

                            // Search for the name in the firstArrayList and update the price if a match is found
                            for (PassThresoldPriceModelList firstObject : getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTED) {
                                if (firstObject.getName().equals(nameToSearch)) {
                                    // If the name matches, get the price
                                    priceToAdd = firstObject.getThreshold_price();
                                    break; // Found a match, no need to search further
                                }
                            }

                            // Update the price in the second ArrayList
                            secondObject.setFloorPrice(Integer.valueOf(priceToAdd));
                        }


                        if (getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).isChecked()) {
                            isEmptyDataCheck = true;

                            str = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).getFloorPrice());
                            String number = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).getListPrice());

                            String str11 = number;
                            String strNew = str11.replace("$", "");
                            String strNew2 = strNew.replace(",", "");

                            double value1 = Double.parseDouble(strNew2);
//                            try {
//                                NumberFormat.getNumberInstance(Locale.FRANCE).parse(strNew2);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            if (str == null || str.isEmpty()) {
                                emptyPrice = true;
                            } else if (str.equalsIgnoreCase("0")) {
                                Toast.makeText(activity, "Please enter Floor Price", Toast.LENGTH_SHORT).show();
//                                statusss = "0";
                            } else {

                                double value2 = Double.parseDouble(str);

                                if (!(value1 > value2 &&
                                        value1 != value2 && value2 != 0)) {
                                    lessthanPrice = true;

                                } else {
                                    selectedDataArrayListFinal.add(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i));
                                }
                            }
                        }

//                        }
                    }

                    if (!isEmptyDataCheck) {
                        Toast.makeText(activity, "No changes made.  Nothing to update.", Toast.LENGTH_SHORT).show();
                    } else if (emptyPrice) {
                        Toast.makeText(activity, "Please enter Floor Price", Toast.LENGTH_SHORT).show();
                    } else if (lessthanPrice) {
                        Toast.makeText(activity, "Floor price should be less than the Retail Price", Toast.LENGTH_SHORT).show();
                    } else {

                        ArrayList<PassThresoldPriceModelList> selectedDataArrayList2 = new ArrayList<>();
                        selectedDataArrayList2.clear();

                        for (int i = 0; i < getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.size(); i++) {

                            PassThresoldPriceModelList passThresoldPriceModelList22 = new PassThresoldPriceModelList();
                            String stringToAdd = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).getCloverProductId()); // Replace `yourArray` with the actual array name
                            // Add the string to the ArrayList
                            Log.e("stringToAdd", stringToAdd);

                            passThresoldPriceModelList22.setCloverid(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).getCloverProductId()));
                            passThresoldPriceModelList22.setSku("");
                            passThresoldPriceModelList22.setName(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).getName()));

                            passThresoldPriceModelList22.setRetail_price(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).getListPrice());
                            passThresoldPriceModelList22.setProduct_id(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).getCloverProductId()));
                            passThresoldPriceModelList22.setStock(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).getUnitName()));
                            passThresoldPriceModelList22.setThreshold_price(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.get(i).getFloorPrice()));
                            passThresoldPriceModelList22.setClover_product_status(true);

                            selectedDataArrayList2.add(passThresoldPriceModelList22);
                        }
                        statusss = "";
                        for (int k = 0; k < selectedDataArrayList2.size(); k++) {
                            if (selectedDataArrayList2.get(k).getThreshold_price().equalsIgnoreCase("0")) {
//                                Toast.makeText(activity, "Please Enter Floor Price", Toast.LENGTH_SHORT).show();
                                statusss = "0";
                            }
                        }

                        if (statusss == "" || statusss == null) {
                            showcustomdialog(selectedDataArrayList2);
                        }
                    }

                } else if (chechStatus.equalsIgnoreCase("HAGLUPLOADAFTERCHECKBOX")) {

//                    Toast.makeText(activity, "HAGLUPLOAD VALUE", Toast.LENGTH_SHORT).show();

                    ArrayList<HagalProductProduct> selectedDataArrayListFinal = new ArrayList<>();
                    selectedDataArrayListFinal.clear();
                    boolean isEmptyDataCheck = false;
                    boolean emptyPrice = false;
                    boolean lessthanPrice = false;
                    String str = null;

                    /////////////////////


                    /////////////////////

                    for (int i = 0; i < getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.size(); i++) {
//                        for (int j = 0; j < getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTED.size(); j++) {
                        if (getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getCheckboxStatus().equalsIgnoreCase("Checked")) {
                            isEmptyDataCheck = true;
                            str = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getFloorPrice());
                            String number = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getListPrice());

                            String str11 = number;
                            String strNew = str11.replace("$", "");

                            double value1 = Double.parseDouble(strNew);

                            if (str == null || str.isEmpty()) {
                                emptyPrice = true;
                            } else if (str.equalsIgnoreCase("0") || str.equalsIgnoreCase("null")) {
                                Toast.makeText(activity, "Please enter Floor Price", Toast.LENGTH_SHORT).show();
//                                statusss = "0";
                            } else {
                                double value2;
                                if (str.equalsIgnoreCase("null") || str == null) {
                                    value2 = Double.parseDouble("0");
                                } else {
                                    value2 = Double.parseDouble(str);
                                }


                                if (!(value1 > value2 &&
                                        value1 != value2 && value2 != 0)) {
                                    lessthanPrice = true;

                                } else {
                                    selectedDataArrayListFinal.add(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i));
                                }
                            }
                        }
//                        }
                    }

                    if (!isEmptyDataCheck) {
                        Toast.makeText(activity, "No changes made.  Nothing to update.", Toast.LENGTH_SHORT).show();
                    } else if (emptyPrice) {
                        Toast.makeText(activity, "Please enter Floor Price", Toast.LENGTH_SHORT).show();
                    } else if (lessthanPrice) {
                        Toast.makeText(activity, "Floor price should be less than the Retail Price", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<PassThresoldPriceModelList> selectedDataArrayList2 = new ArrayList<>();
                        selectedDataArrayList2.clear();

                        for (int i = 0; i < getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.size(); i++) {

                            PassThresoldPriceModelList passThresoldPriceModelList22 = new PassThresoldPriceModelList();
                            String stringToAdd = String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getCloverProductId()); // Replace `yourArray` with the actual array name
                            // Add the string to the ArrayList
                            Log.e("stringToAdd", stringToAdd);

                            passThresoldPriceModelList22.setCloverid(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getCloverProductId()));
                            passThresoldPriceModelList22.setSku("");
                            passThresoldPriceModelList22.setCheckboxStatus("Checked");
                            passThresoldPriceModelList22.setName(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getName()));



                            passThresoldPriceModelList22.setRetail_price(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getListPrice());
                            passThresoldPriceModelList22.setProduct_id(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getCloverProductId()));
                            passThresoldPriceModelList22.setStock(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getUnitName()));
                            passThresoldPriceModelList22.setThreshold_price(String.valueOf(getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALLAFTERCHECKBOX.get(i).getFloorPrice()));
                            passThresoldPriceModelList22.setClover_product_status(true);

                            selectedDataArrayList2.add(passThresoldPriceModelList22);
                        }

                        statusss = "";
                        for (int k = 0; k < selectedDataArrayList2.size(); k++) {
                            if (selectedDataArrayList2.get(k).getThreshold_price().equalsIgnoreCase("0")) {
//                                Toast.makeText(activity, "Please Enter Floor Price", Toast.LENGTH_SHORT).show();
                                statusss = "0";
                            }
                        }

                        if (statusss == "" || statusss == null) {
                            showcustomdialog(selectedDataArrayList2);
                        }
//                        showcustomdialog(selectedDataArrayList2);
                    }

                } else if (chechStatus.equalsIgnoreCase("DELETE")) {
//                    Toast.makeText(activity, "DELETE VALUE", Toast.LENGTH_SHORT).show();

                    showcustomdialog2(getItemsPresenter.inventoryAdapter.stringArrayList, getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL);

                } else {
                    Toast.makeText(activity, "No changes made.  Nothing to update....", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void showcustomdialog2
            (ArrayList<PassThresoldPriceModelList> selectedDataArrayList, ArrayList<HagalProductProduct> selectedDataArrayListONSELECTEDFINALL) {


        ArrayList<PassThresoldPriceModelList> newList = new ArrayList<PassThresoldPriceModelList>();
        for (PassThresoldPriceModelList element : selectedDataArrayList) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }

        ///////////////////////
        //////////////////////
        ArrayList<HagalProductProduct> newListSecond = new ArrayList<HagalProductProduct>();
        for (HagalProductProduct element11 : selectedDataArrayListONSELECTEDFINALL) {
            if (!newListSecond.contains(element11)) {
                newListSecond.add(element11);
            }
        }

        /////////////////////

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.hagalprice_uploaddialog);

        TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
        TextView txt_Ok = dialog.findViewById(R.id.txt_Ok);
//        TextView td_applycode = dialog.findViewById(R.id.td_applycode);

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getItemsPresenter.deleteHagalProductApi(newList, newListSecond);
                selectedDataArrayList.clear();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showcustomdialog
            (ArrayList<PassThresoldPriceModelList> selectedDataArrayList) {
        String status = "";
        ArrayList<PassThresoldPriceModelList> newList = new ArrayList<PassThresoldPriceModelList>();
        for (PassThresoldPriceModelList element : selectedDataArrayList) {
            if (!newList.contains(element.getName())) {
                newList.add(element);
            }
        }
        for (PassThresoldPriceModelList element : newList) {
            if (!(element.getThreshold_price() == "null" || element.getThreshold_price().equalsIgnoreCase("0"))) {
                status = "yes";
            } else {
                status = "no";
                Toast.makeText(activity, "Please enter Floor Price", Toast.LENGTH_SHORT).show();
            }
        }

        if (status.equalsIgnoreCase("yes")) {

            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.hagalprice_uploaddialog);

            TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);
            TextView txt_Ok = dialog.findViewById(R.id.txt_Ok);
//        TextView td_applycode = dialog.findViewById(R.id.td_applycode);

            txt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            txt_Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getItemsPresenter.postThresoldItems(newList);
                    selectedDataArrayList.clear();
                    getItemsPresenter.inventoryAdapter.selectedDataArrayListONSELECTEDFINALL.clear();
//                thresoldPricePresenter.thresholdPriceAdapter.unselectall();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    @Override
    public void onEditTextValueChanged(int position, String value) {
//        this.enteredValue = value;
//
//        Log.e("098765", enteredValue + "");
    }

    @Override
    public void showDialog(Activity activity) {
        Utils.showDialogMethod(activity, activity.getFragmentManager());
    }

    @Override
    public void hideDialog() {
        Utils.hideDialog();
    }

    @Override
    public void showMessage(Activity activity, String message) {
        Utils.showMessage(activity, message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CSPreferences.putString(activity, "Multiselect", "");
        main_Checkbox.setChecked(false);
        getItemsPresenter.getInventeryItems();
    }

    public void refresh(View view) {          //refresh is onClick name given to the button
        onRestart();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(TotalInventoryActivity.this, TotalInventoryActivity.class);  //your class
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - onRecentBackPressedTime > 2000) {
            onRecentBackPressedTime = System.currentTimeMillis();

            Snackbar snackbar = Snackbar
                    .make(main_Checkbox, "Please press BACK Again to exit without saving data", Snackbar.LENGTH_LONG);
            snackbar.show();

//                Toast.makeText(this, "Please press BACK Again to exit without saving data", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
//        }
    }

}