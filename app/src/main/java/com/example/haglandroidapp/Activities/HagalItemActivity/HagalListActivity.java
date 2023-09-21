package com.example.haglandroidapp.Activities.HagalItemActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haglandroidapp.Activities.HagalItemActivity.presenter.HagalListPresenter;
import com.example.haglandroidapp.Activities.HagalItemActivity.view.HagalListView;
import com.example.haglandroidapp.Activities.SwipeToDeleteCallback;
import com.example.haglandroidapp.Adapters.HagalListAdapter;
import com.example.haglandroidapp.Models.GetHagalProductList.HagalProductProduct;
import com.example.haglandroidapp.Models.UpdateHagalProductModel.UpdateHagalProductProduct;
import com.example.haglandroidapp.R;
import com.example.haglandroidapp.Utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;

public class HagalListActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, HagalListView {

    private RecyclerView recyclerView;
    private Activity activity;
    private TextView txt_next;
    private HagalListAdapter hagalListAdapter;

    private Spinner spin;
    private String[] arraylist;
    private HagalListPresenter hagalListPresenter;
    private RelativeLayout rl_main;
    private EditText tool_searchBar;
    private String thresoldPriceValue;
    private int i;

    private CheckBox main_Checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_hagal_list);
        hideKeyboard(this);
        activity = this;
        init();
        listiners();

        hagalListPresenter = new HagalListPresenter(activity, recyclerView, this);
        hagalListPresenter.getHagalListApi();
//        enableSwipeToDeleteAndUndo();

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

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (arraylist[pos].contains("ACTION")) {
//                    Toast.makeText(activity, arraylist[pos], Toast.LENGTH_SHORT).show();
                    txt_next.setText("UPLOAD FLOOR PRICE");
                } else if (arraylist[pos].contains("UPDATE FLOOR PRICE")) {
//                    Toast.makeText(activity, arraylist[pos], Toast.LENGTH_SHORT).show();
                    txt_next.setText("UPDATE FLOOR PRICE");
                } else if (arraylist[pos].contains("REMOVE ITEM")) {
//                    Toast.makeText(activity, arraylist[pos], Toast.LENGTH_SHORT).show();
                    txt_next.setText("REMOVE ITEM");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void init() {

        recyclerView = findViewById(R.id.recyclerView);
        txt_next = findViewById(R.id.txt_next);
        rl_main = findViewById(R.id.rl_main);
        tool_searchBar = findViewById(R.id.tool_searchBar);

        spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        // Add Text Change Listener to EditText
        tool_searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charText, int start, int before, int count) {
//                if (tool_searchBar.getText().toString().trim().length() == 0) {
//                    iv_close.setVisibility(View.INVISIBLE);
                hagalListPresenter.filter(charText.toString());
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

        main_Checkbox = findViewById(R.id.mainCheckBox);

        main_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main_Checkbox.isChecked()) {
                    hagalListPresenter.hagalListAdapter.selectAll();
                } else {
                    hagalListPresenter.hagalListAdapter.unselectall();
                }

            }
        });


    }

    private void listiners() {
        txt_next.setOnClickListener(this);
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
    public void onClick(View v) {

        if (v == txt_next) {

            String txtValue = txt_next.getText().toString();
            Log.e("Selected Value", txtValue);

            if (hagalListPresenter.hagalListAdapter.selectedDataArrayList.size() != 0) {
                if (txtValue.contains("UPLOAD FLOOR PRICE")) {
                    Toast.makeText(activity, "Please Select Action", Toast.LENGTH_SHORT).show();
                } else if (txtValue.contains("UPDATE FLOOR PRICE")) {

                    String str = hagalListPresenter.hagalListAdapter.thresoldPrice11;

                    for (int i = 0; i < hagalListPresenter.addHaglProductLocalModels.size(); i++) {
                        if (hagalListPresenter.addHaglProductLocalModels.get(i).getName().equals(hagalListPresenter.hagalListAdapter.name)) {
                            if (!(hagalListPresenter.addHaglProductLocalModels.get(i).getListPrice() > Integer.valueOf(str) &&
                                    hagalListPresenter.addHaglProductLocalModels.get(i).getListPrice() != Integer.valueOf(str))) {

                                hagalListPresenter.hagalListAdapter.unselectall();

                                Toast.makeText(activity, "Floor price should be less than the Retail Price", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(activity, "Please enter less amount from the Retail Price", Toast.LENGTH_SHORT).show();
                                thresoldPriceValue = str;
                            } else {
                                hagalListPresenter.updateHagalProductApi(hagalListPresenter.hagalListAdapter.cloverProductId, hagalListPresenter.hagalListAdapter.name,
                                        hagalListPresenter.hagalListAdapter.sku, hagalListPresenter.hagalListAdapter.listPrice,
                                        hagalListPresenter.hagalListAdapter.cloverId, hagalListPresenter.hagalListAdapter.unitName, hagalListPresenter.hagalListAdapter.thresoldPrice11);
                            }
                        }
                    }

                } else if (txtValue.contains("UPDATE FLOOR PRICE")) {

                    String str = hagalListPresenter.hagalListAdapter.thresoldPrice11;

                    for (int i = 0; i < hagalListPresenter.addHaglProductLocalModels.size(); i++) {
                        if (hagalListPresenter.addHaglProductLocalModels.get(i).getName().equals(hagalListPresenter.hagalListAdapter.name)) {
                            if (!(hagalListPresenter.addHaglProductLocalModels.get(i).getListPrice() > Integer.valueOf(str) &&
                                    hagalListPresenter.addHaglProductLocalModels.get(i).getListPrice() != Integer.valueOf(str))) {

                                hagalListPresenter.hagalListAdapter.unselectall();

                                Toast.makeText(activity, "Floor price should be less than the Retail Price", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(activity, "Please enter less amount from the Retail Price", Toast.LENGTH_SHORT).show();
                                thresoldPriceValue = str;
                            } else {
                                hagalListPresenter.updateHagalProductApi(hagalListPresenter.hagalListAdapter.cloverProductId, hagalListPresenter.hagalListAdapter.name,
                                        hagalListPresenter.hagalListAdapter.sku, hagalListPresenter.hagalListAdapter.listPrice,
                                        hagalListPresenter.hagalListAdapter.cloverId, hagalListPresenter.hagalListAdapter.unitName, hagalListPresenter.hagalListAdapter.thresoldPrice11);
                            }
                        }
                    }

                } else if (txtValue.contains("REMOVE ITEM")) {
                    ArrayList<String> stringArrayList = new ArrayList<>();
                    stringArrayList.clear();
                    for (int i = 0; i < hagalListPresenter.hagalListAdapter.selectedDataArrayList.size(); i++) {

                        String stringToAdd = hagalListPresenter.hagalListAdapter.selectedDataArrayList.get(i).getProduct_id(); // Replace `yourArray` with the actual array name

                        // Add the string to the ArrayList

                        stringArrayList.add(stringToAdd);
                    }

//                    hagalListPresenter.deleteHagalProductApi(hagalListPresenter.hagalListAdapter.cloverProductId);
//                    hagalListPresenter.deleteHagalProductApi(stringArrayList);
                }

            } else {
                Toast.makeText(activity, "Please Select Hagl Item", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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


}