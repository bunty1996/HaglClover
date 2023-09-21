package com.example.haglandroidapp.Activities.TotalInventeryActivity;

import java.util.ArrayList;
import java.util.List;

public interface EditTextListener {

    void onEditTextValueChanged(int position,String value);

    void onEditTextValueChanged(ArrayList<String> editTextValues);

//    public interface ValueChangeListener {
        void onValueChanged(int position, String newValue);
//    }
}
