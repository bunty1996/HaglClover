package com.example.haglandroidapp.Activities.TotalInventeryActivity.view;

import android.app.Activity;

public interface GetInventeryItemsView {
    void onEditTextValueChanged(int position, String value);

    public void showDialog(Activity activity);
    public void hideDialog();
    public void showMessage(Activity activity, String message);

}
