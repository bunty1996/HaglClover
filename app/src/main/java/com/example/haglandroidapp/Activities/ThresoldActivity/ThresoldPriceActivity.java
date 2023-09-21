package com.example.haglandroidapp.Activities.ThresoldActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haglandroidapp.Activities.ThresoldActivity.presenter.ThresoldPricePresenter;
import com.example.haglandroidapp.Activities.ThresoldActivity.view.ThresoldPriceView;
import com.example.haglandroidapp.Adapters.ThresholdPriceAdapter;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductProduct;
import com.example.haglandroidapp.Models.GetTotalInventeryItems.GetTotalInventeryItemsElement;
import com.example.haglandroidapp.Models.PassThresoldPriceModelList;
import com.example.haglandroidapp.R;
import com.example.haglandroidapp.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ThresoldPriceActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, ThresoldPriceView {

    private RecyclerView recyclerView;
    private TextView txt_next;
    private Activity activity;
    private ThresholdPriceAdapter thresholdPriceAdapter;
    private CheckBox main_Checkbox;
    private Spinner spin;
    private String[] arraylist;
    private ThresoldPricePresenter thresoldPricePresenter;
    private ArrayList<PassThresoldPriceModelList> selectedDataArrayList = new ArrayList<>();

    private final String[] productName = new String[]{"Johny Walker", "Red Label",
            "Chevas Regal", "Jack Daniel", "JimBim", "Sambooka", "Famous Grouse", "Johny Walker", "Red Label",
            "Chevas Regal", "Jack Daniel", "JimBim"}; //inline initialization

    private ArrayList<GetTotalInventeryItemsElement> myOrderCarts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_thresold_price);
        hideKeyboard(this);
        activity = this;
        init();
        listiners();

        String dataString = getIntent().getStringExtra("orderlistData");
        myOrderCarts = new Gson().fromJson(dataString, new TypeToken<ArrayList<GetTotalInventeryItemsElement>>() {
        }.getType());

        Log.e("myOrderCarts", "" + myOrderCarts);


        thresoldPricePresenter = new ThresoldPricePresenter(activity, this, recyclerView, myOrderCarts);

        //Creating the ArrayAdapter instance having the country list

        arraylist = getResources().getStringArray(R.array.spinner_array);
        int selectedItem = -1;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraylist) {

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = null;
                v = super.getDropDownView(position, null, parent);
                // If this is the selected item position
                if (position == selectedItem) {
                    v.setBackgroundColor(Color.BLUE);
                } else {
                    // for other views
                    v.setBackgroundColor(getResources().getColor(R.color.light_green));
                }
                return v;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(dataAdapter);
        spin.setPrompt("Action");
//        spin.setPopupBackgroundResource(getResources().getColor(R.color.light_green));


    }

    private void init() {

        recyclerView = findViewById(R.id.recyclerView);
        txt_next = findViewById(R.id.txt_next);

        spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        main_Checkbox = findViewById(R.id.mainCheckBox);

        main_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main_Checkbox.isChecked()) {
                    boolean isSelected = true;
//                    CSPreferences.putString(activity, "ThresholdMultiselect", "true");
//                    thresoldPricePresenter.thresholdPriceAdapter.selectAll();
                    for (int i = 0; i < thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.size(); i++) {
                        thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.get(i).setChecked(true);
                    }
                    thresoldPricePresenter.thresholdPriceAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.size(); i++) {
                        thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.get(i).setChecked(false);
                    }
                    thresoldPricePresenter.thresholdPriceAdapter.notifyDataSetChanged();
                }

            }
        });

    }

    private void listiners() {
        txt_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == txt_next) {

            if (thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.size() == 0) {
                Toast.makeText(activity, "Select product", Toast.LENGTH_SHORT).show();

            } else {

                ArrayList<HagalProductProduct> selectedDataArrayListFinal = new ArrayList<>();
                selectedDataArrayListFinal.clear();
                boolean isEmptyDataCheck = false;
                boolean emptyPrice = false;
                boolean lessthanPrice = false;

                for (int i = 0; i < thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.size(); i++) {

                    if (thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.get(i).isChecked()) {
                        isEmptyDataCheck = true;
                        String str = thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.get(i).getThreshold_price();
//                    if (myOrderCarts.get(i).getName().equals(
//                            thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.get(i).getName())) {

//                        double value1 = Double.parseDouble(String.valueOf(myOrderCarts.get(i).getPrice()));
//                        double value1 = Double.parseDouble(thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.get(i).getRetail_price());

                        String number = thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.get(i).getRetail_price();
//                        double parsed = Double.parseDouble(number);
//                        String formato = NumberFormat.getCurrencyInstance().format((parsed / 100));
//
//                        String str11 = formato;
                        String str11 = number;
                        String strNew = str11.replace("$", "");

                        double value1 = Double.parseDouble(strNew);

                        if (str == null || str.isEmpty()) {
                            emptyPrice = true;
//                            Toast.makeText(activity, "Please ", Toast.LENGTH_SHORT).show();
                        } else {

                            double value2 = Double.parseDouble(str);

                            if (!(value1 > value2 &&
                                    value1 != value2)) {
                                lessthanPrice = true;

                            } else {
//                                selectedDataArrayListFinal.add(thresoldPricePresenter.thresholdPriceAdapter.selectedDataArrayList.get(i));
                            }
                        }
                    }
                }

                if (!isEmptyDataCheck) {
                    Toast.makeText(activity, "Please select atleast one", Toast.LENGTH_SHORT).show();
                } else if (emptyPrice) {
                    Toast.makeText(activity, "Please enter Floor Price", Toast.LENGTH_SHORT).show();
                } else if (lessthanPrice) {
                    Toast.makeText(activity, "Floor price should be less than the Retail Price", Toast.LENGTH_SHORT).show();
                } else {
                    showcustomdialog(selectedDataArrayListFinal);
                }

//            Toast.makeText(activity, "Send To Hagal", Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(activity, ThresoldPriceActivity.class);
//            startActivity(intent);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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


    public void showcustomdialog
            (ArrayList<HagalProductProduct> selectedDataArrayList) {

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

//                thresoldPricePresenter.postThresoldItems(selectedDataArrayList);
                thresoldPricePresenter.thresholdPriceAdapter.unselectall();

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        CSPreferences.putString(activity, "ThresholdMultiselect", "");
//        main_Checkbox.setChecked(false);
    }
}