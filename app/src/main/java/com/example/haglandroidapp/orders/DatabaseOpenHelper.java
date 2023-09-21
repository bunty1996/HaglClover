/**
 * Created by ${Raman} on ${26/08/2020}.
 */

package com.example.haglandroidapp.orders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.example.haglandroidapp.Models.DiscountPojo;
import com.example.haglandroidapp.Models.GlobalFunctions;
import com.example.haglandroidapp.Models.LastDiscountBackup;
import com.example.haglandroidapp.Models.itemsPojo;
import com.example.haglandroidapp.Utils.Commons;
import com.example.haglandroidapp.Utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOpenHelper {
    myDbHelper myhelper;

    public DatabaseOpenHelper(Context context) {
        myhelper = new myDbHelper(context);
    }

    // inventery items functionality close
    // Insert inventory data
    public void insertInventoryList(List<itemsPojo> inventory_list, String TAG, String type) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        dbb.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < inventory_list.size(); i++) {
                Log.d("insertInventoryList", inventory_list.size() + "");
                contentValues.put(myDbHelper.PRODUCT_NAME, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getProduct_name(), TAG));
                contentValues.put(myDbHelper.SIZE, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getSize(), TAG));
                contentValues.put(myDbHelper.UPC, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getUpc(), TAG));
                contentValues.put(myDbHelper.CATEGORY, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getCategory(), TAG));
                contentValues.put(myDbHelper.PRODUCT_ID, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getProduct_id(), TAG));
                contentValues.put(myDbHelper.TAX, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getTax(), TAG));

//                if (type.equals(Commons.VERTICAL)) {
//                    Log.d("callVertical", inventory_list.get(i).getAddcart() + "");
//                    contentValues.put(myDbHelper.PRODUCT_ID, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getProduct_id(), TAG));
//                    contentValues.put(myDbHelper.STOCK, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getStock() + "", TAG));
//                    contentValues.put(myDbHelper.DISTRIBUTOR, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getDistributor(), TAG));
//                    contentValues.put(myDbHelper.IS_ADDTOCART, GlobalFunctions.chkSqlFieldEWntryNull(String.valueOf(inventory_list.get(i).getAddcart()), TAG));
//                }
                contentValues.put(myDbHelper.PRODUCT_ID, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getProduct_id(), TAG));

                contentValues.put(myDbHelper.RETAIL_PRICE,
                        GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getRetail_price(), TAG));
                contentValues.put(myDbHelper.CASE_COST, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getCase_cost(), TAG));
                contentValues.put(myDbHelper.UNITS, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getUnits(), TAG));
                contentValues.put(myDbHelper.BOTTLE_COST, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getBottle_cost(), TAG));
                //if (inventory_list.get(i).getState_min() == null ) {
                // contentValues.put(myDbHelper.STATE_MIN, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getBottle_cost(), TAG));
                // } else {
                contentValues.put(myDbHelper.STATE_MIN, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getState_min(), TAG));
                contentValues.put(myDbHelper.STOCK, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getStock(), TAG));
                contentValues.put(myDbHelper.CATEGORY, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getCategory(), TAG));
                contentValues.put(myDbHelper.DISTRIBUTOR, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getDistributor(), TAG));

                //  }
                contentValues.put(myDbHelper.CLOVER_ID, GlobalFunctions.chkSqlFieldEWntryNull(inventory_list.get(i).getClover_id(), TAG));
                dbb.insert(myDbHelper.TABLE_NAME, null, contentValues);
            }
            dbb.setTransactionSuccessful();
        } finally {
            dbb.endTransaction();
        }

//        ShowMessage.message(MyApplication.context(), "uploading products...");

        Toast.makeText(MyApplication.context(), "uploading products...", Toast.LENGTH_SHORT).show();

    }

    // Upadte inventery data
    public int updateInventeryData(itemsPojo itemsPojo, String TAG, String type, int show_message) {

        SQLiteDatabase db = myhelper.getWritableDatabase();
        Log.d("Updated product", itemsPojo + "");
        ContentValues values = new ContentValues();

        if (type.equals(Commons.VERTICAL)) {
            values.put(myDbHelper.STOCK, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getStock(), TAG));
            values.put(myDbHelper.DISTRIBUTOR, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getDistributor(), TAG));
            values.put(myDbHelper.IS_ADDTOCART, GlobalFunctions.chkSqlFieldEWntryNull(String.valueOf(itemsPojo.getAddcart()), TAG));
            values.put(myDbHelper.PRODUCT_ID, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getProduct_id(), TAG));
        } else {
            values.put(myDbHelper.UNITS, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getUnits(), TAG));
        }
        values.put(myDbHelper.PRODUCT_ID, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getProduct_id(), TAG));

        values.put(myDbHelper.CASE_COST, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getCase_cost(), TAG));
        values.put(myDbHelper.BOTTLE_COST, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getBottle_cost(), TAG));
        values.put(myDbHelper.STATE_MIN, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getState_min(), TAG));
        values.put(myDbHelper.RETAIL_PRICE, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getRetail_price(), TAG));
        values.put(myDbHelper.UPC, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getUpc(), TAG));
        values.put(myDbHelper.SIZE, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getSize(), TAG));
        values.put(myDbHelper.PRODUCT_NAME, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getProduct_name(), TAG));
        values.put(myDbHelper.STOCK, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getStock(), TAG));
        values.put(myDbHelper.DISTRIBUTOR, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getDistributor(), TAG));
        values.put(myDbHelper.CATEGORY, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getCategory(), TAG));
        values.put(myDbHelper.TAX, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getTax(), TAG));

        int i = db.update(myDbHelper.TABLE_NAME, values,
                myDbHelper.CLOVER_ID + "='" + itemsPojo.getClover_id() + "'", null);
        Log.d("Record Updated>>>", i + "");
        if (show_message != 1) {
//            ShowMessage.message(MyApplication.context(), "Product Updated Successfully");
            Toast.makeText(MyApplication.context(), "Product Updated Successfully", Toast.LENGTH_SHORT).show();
        }
        return i;
    }

    // inventory items get one record
    public itemsPojo getOnerecord(String clover_id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        //String query = "SELECT * FROM todo WHERE category=" + v;
        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.TABLE_NAME + " where " + myDbHelper.CLOVER_ID + "='" + clover_id + "'", null);
        itemsPojo item = new itemsPojo();
        if (cursor.moveToFirst()) {
            do {
                item.setUid(cursor.getString(cursor.getColumnIndex(myDbHelper.UID)));
                item.setProduct_name(cursor.getString(cursor.getColumnIndex(myDbHelper.PRODUCT_NAME)));
                item.setSize(cursor.getString(cursor.getColumnIndex(myDbHelper.SIZE)));
                item.setUpc(cursor.getString(cursor.getColumnIndex(myDbHelper.UPC)));
                item.setCategory(cursor.getString(cursor.getColumnIndex(myDbHelper.CATEGORY)));
                item.setRetail_price(cursor.getString(cursor.getColumnIndex(myDbHelper.RETAIL_PRICE)));
                item.setCase_cost(cursor.getString(cursor.getColumnIndex(myDbHelper.CASE_COST)));
                item.setUnits(cursor.getString(cursor.getColumnIndex(myDbHelper.UNITS)));
                item.setBottle_cost(cursor.getString(cursor.getColumnIndex(myDbHelper.BOTTLE_COST)));
                item.setState_min(cursor.getString(cursor.getColumnIndex(myDbHelper.STATE_MIN)));
                item.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.CLOVER_ID)));
            } while (cursor.moveToNext());
        }
        return item;
    }


    // Get vertical whole list
    public List<itemsPojo> getVerticalWholeInventeryListData() {
        List<itemsPojo> lv_inventery = new ArrayList<>();
        SQLiteDatabase db = myhelper.getWritableDatabase();

        String[] columns = {myDbHelper.UID, myDbHelper.PRODUCT_NAME, myDbHelper.SIZE, myDbHelper.UPC, myDbHelper.CATEGORY, myDbHelper.RETAIL_PRICE, myDbHelper.CASE_COST, myDbHelper.UNITS, myDbHelper.BOTTLE_COST, myDbHelper.STATE_MIN, myDbHelper.CLOVER_ID, myDbHelper.STOCK, myDbHelper.DISTRIBUTOR, myDbHelper.PRODUCT_ID, myDbHelper.IS_ADDTOCART};

        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                itemsPojo item = new itemsPojo();
                item.setUid(cursor.getString(cursor.getColumnIndex(myDbHelper.UID)));
                item.setProduct_name(cursor.getString(cursor.getColumnIndex(myDbHelper.PRODUCT_NAME)));
                item.setSize(cursor.getString(cursor.getColumnIndex(myDbHelper.SIZE)));
                item.setUpc(cursor.getString(cursor.getColumnIndex(myDbHelper.UPC)));
                item.setProduct_id(cursor.getString(cursor.getColumnIndex(myDbHelper.PRODUCT_ID)));
                item.setCategory(cursor.getString(cursor.getColumnIndex(myDbHelper.CATEGORY)));
                item.setRetail_price(cursor.getString(cursor.getColumnIndex(myDbHelper.RETAIL_PRICE)));
                item.setCase_cost(cursor.getString(cursor.getColumnIndex(myDbHelper.CASE_COST)));
                item.setUnits(cursor.getString(cursor.getColumnIndex(myDbHelper.UNITS)));
                item.setBottle_cost(cursor.getString(cursor.getColumnIndex(myDbHelper.BOTTLE_COST)));
                item.setState_min(cursor.getString(cursor.getColumnIndex(myDbHelper.STATE_MIN)));
                item.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.CLOVER_ID)));
                item.setAddcart(cursor.getInt(cursor.getColumnIndex(myDbHelper.IS_ADDTOCART)));
                item.setStock(cursor.getString(cursor.getColumnIndex(myDbHelper.STOCK)));
                item.setDistributor(cursor.getString(cursor.getColumnIndex(myDbHelper.DISTRIBUTOR)));

                lv_inventery.add(item);
                //  Log.d("lv_inventerty?>>>>",lv_inventery+"");
            } while (cursor.moveToNext());
        }
        return lv_inventery;
    }


    // Get whole list
    public List<itemsPojo> getWholeInventeryListData(String type) {
        List<itemsPojo> lv_inventery = new ArrayList<>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID, myDbHelper.PRODUCT_ID, myDbHelper.PRODUCT_NAME, myDbHelper.TAX, myDbHelper.SIZE, myDbHelper.DISTRIBUTOR, myDbHelper.UPC, myDbHelper.CATEGORY, myDbHelper.STOCK, myDbHelper.RETAIL_PRICE, myDbHelper.CASE_COST, myDbHelper.UNITS, myDbHelper.BOTTLE_COST, myDbHelper.STATE_MIN, myDbHelper.CLOVER_ID};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, null, null, null, null, null);
  /*     try {
           db.beginTransaction();*/
        if (cursor.moveToFirst()) {
            do {

                itemsPojo item = new itemsPojo();

                item.setUid(cursor.getString(cursor.getColumnIndex(myDbHelper.UID)));
                item.setProduct_name(cursor.getString(cursor.getColumnIndex(myDbHelper.PRODUCT_NAME)));
                item.setSize(cursor.getString(cursor.getColumnIndex(myDbHelper.SIZE)));
                item.setDistributor(cursor.getString(cursor.getColumnIndex(myDbHelper.DISTRIBUTOR)));
                item.setTax(cursor.getString(cursor.getColumnIndex(myDbHelper.TAX)));
                item.setUpc(cursor.getString(cursor.getColumnIndex(myDbHelper.UPC)));
                item.setCategory(cursor.getString(cursor.getColumnIndex(myDbHelper.CATEGORY)));
                item.setStock(cursor.getString(cursor.getColumnIndex(myDbHelper.STOCK)));
                item.setProduct_id(cursor.getString(cursor.getColumnIndex(myDbHelper.PRODUCT_ID)));

                item.setRetail_price(cursor.getString(cursor.getColumnIndex(myDbHelper.RETAIL_PRICE)));
                item.setCase_cost(cursor.getString(cursor.getColumnIndex(myDbHelper.CASE_COST)));
                item.setUnits(cursor.getString(cursor.getColumnIndex(myDbHelper.UNITS)));
                item.setBottle_cost(cursor.getString(cursor.getColumnIndex(myDbHelper.BOTTLE_COST)));
                item.setState_min(cursor.getString(cursor.getColumnIndex(myDbHelper.STATE_MIN)));
                item.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.CLOVER_ID)));

                lv_inventery.add(item);
                //  Log.d("lv_inventerty?>>>>",lv_inventery+"");
            } while (cursor.moveToNext());

        }
//       }finally {
//           db.endTransaction();
//       }
        return lv_inventery;
    }

    // For removing inventory items
    public void removeStateMinimumDataAll() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        db.delete(myDbHelper.TABLE_NAME, null, null);
        db.close();
    }
    // inventery items functionality close table 1
    // discount items functionality start table 2

    //Insert discount record at creation time
    public long inserCreatedDiscount(DiscountPojo discountPojo, String TAG) {
        Log.d("YESs", discountPojo.getSetLimit() + "");
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        long id;
        dbb.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(myDbHelper.DIS_NAME, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getDis_name(), TAG));
            contentValues.put(myDbHelper.DIS_TYPE, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getDis_type(), TAG));
            contentValues.put(myDbHelper.DISCOUNT_SERVERID, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getDiscount_serverid(), TAG));
            contentValues.put(myDbHelper.QUANTITY, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getQunatity(), TAG));
            contentValues.put(myDbHelper.EXPIRAY, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getExpires(), TAG));
            contentValues.put(myDbHelper.PRICE, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getPrice(), TAG));
            contentValues.put(myDbHelper.STATUS, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getStatus(), TAG));


            contentValues.put(myDbHelper.USAGE, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getUsage_type(), TAG));
            contentValues.put(myDbHelper.LIMIT, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getSetLimit(), TAG));
            id = dbb.insert(myDbHelper.TABLE_NAME2, null, contentValues);
            Log.d("YESs", discountPojo.getExpires() + "");
            dbb.setTransactionSuccessful();
        } finally {
            dbb.endTransaction();
        }
        getDiscountListData();
        return id;

        // ShowMessage.message(MyApplication.context(), "Created Successfully");
    }

    // Call methoid for getting discount id
    public String getIdFromCreatedDiscount(String discount_name) {
        String id = "";
        SQLiteDatabase db = myhelper.getWritableDatabase();
        //String query = "SELECT * FROM todo WHERE category=" + v;
        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.TABLE_NAME2 + " where " + myDbHelper.DIS_NAME + "='" + discount_name + "'", null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndex(myDbHelper.DIS_ID));
                Log.d("iddddddd>>", id + "");

            } while (cursor.moveToNext());
        }

        return id;
    }

    // Get all List data
    public List<DiscountPojo> getDiscountListData() {
        List<DiscountPojo> lv_discount = new ArrayList<>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.TABLE_NAME2, null);
        if (cursor.moveToFirst()) {
            do {
                DiscountPojo discountPojo = new DiscountPojo();
                discountPojo.setDis_name(cursor.getString(cursor.getColumnIndex(myDbHelper.DIS_NAME)));
                discountPojo.setPrice(cursor.getString(cursor.getColumnIndex(myDbHelper.PRICE)));
                discountPojo.setQunatity(cursor.getString(cursor.getColumnIndex(myDbHelper.QUANTITY)));
                discountPojo.setDis_type(cursor.getString(cursor.getColumnIndex(myDbHelper.DIS_TYPE)));
                discountPojo.setDiscount_serverid(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_SERVERID)));
                discountPojo.setDiscount_id(cursor.getString(cursor.getColumnIndex(myDbHelper.DIS_ID)));
                discountPojo.setExpires(cursor.getString(cursor.getColumnIndex(myDbHelper.EXPIRAY)));
                discountPojo.setStatus(cursor.getString(cursor.getColumnIndex(myDbHelper.STATUS)));
                discountPojo.setUsage_type(cursor.getString(cursor.getColumnIndex(myDbHelper.USAGE)));
                discountPojo.setSetLimit(cursor.getString(cursor.getColumnIndex(myDbHelper.LIMIT)));
                discountPojo.setSelect(false);
                lv_discount.add(discountPojo);
                Log.e("lv_parentRecord555>>>>", discountPojo + "");
            } while (cursor.moveToNext());
        }

        // Log.d("lv_parentRecord?>>>>", lv_discount + "");
        return lv_discount;
    }

    // UPdate discount data accoarding to discount id
    public int updateDiscount(DiscountPojo discountPojo, String TAG) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Log.d("Discount ID>>>", discountPojo + "");
        ContentValues values = new ContentValues();
        values.put(myDbHelper.DIS_NAME, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getDis_name(), TAG));
        values.put(myDbHelper.DIS_TYPE, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getDis_type(), TAG));
        values.put(myDbHelper.DISCOUNT_SERVERID, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getDis_type(), TAG));
        values.put(myDbHelper.QUANTITY, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getQunatity(), TAG));
        values.put(myDbHelper.EXPIRAY, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getExpires(), TAG));
        values.put(myDbHelper.PRICE, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getPrice(), TAG));
        values.put(myDbHelper.STATUS, GlobalFunctions.chkSqlFieldEWntryNull(discountPojo.getStatus(), TAG));
        int i = db.update(myDbHelper.TABLE_NAME2, values, myDbHelper.DIS_ID + " = " + discountPojo.getDiscount_id(), null);
        // ShowMessage.message(MyApplication.context(), "Updated Record");
        return i;

    }

    // Delete one record of discount table
    public void deleteDiscount(long _id) {
        Log.d("gotdeletedId>>>", _id + "");
        SQLiteDatabase db = myhelper.getWritableDatabase();
        db.delete(myDbHelper.TABLE_NAME2, myDbHelper.DIS_ID + "=" + _id, null);
        db.delete(myDbHelper.TABLE_NAME3, myDbHelper.SELETECD_DISID + "=" + _id, null);
        // getParentIdRecord();
//        ShowMessage.message(MyApplication.context(), "Deleted Record Successfully");
        Toast.makeText(MyApplication.context(), "Deleted Record Successfully", Toast.LENGTH_SHORT).show();
    }

    // For removing Discount items
    public void removeDiscountDataAll() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        db.delete(myDbHelper.TABLE_NAME2, null, null);
        db.close();
    }
// Discount table functionality close

    // DiscountItems table functionality start
// Insert record of added products acc to primary id
    public void inserCreatedDiscountProducts(List<itemsPojo> lv_products, String TAG, String primaryid, String whatToDo) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < lv_products.size(); i++) {
                Log.d("DiscountProducts>>>>", lv_products.get(i) + "");
                contentValues.put(myDbHelper.SELETECD_DISID, GlobalFunctions.chkSqlFieldEWntryNull(primaryid, TAG));
                contentValues.put(myDbHelper.SELETECD_DISTYPE, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getPer_type(), TAG));
                contentValues.put(myDbHelper.SELETECD_DISQUANTITY, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getDiscount_quantity(), TAG));
                contentValues.put(myDbHelper.SELETECD_DISPRICE, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getDiscount_price(), TAG));
                contentValues.put(myDbHelper.SELECETD_STATEMINIMUM, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getState_min(), TAG));
                contentValues.put(myDbHelper.SELECETD_BOTTLECOST, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getBottle_cost(), TAG));
                contentValues.put(myDbHelper.SELETECD_PRODUCTNAME, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getProduct_name(), TAG));
                contentValues.put(myDbHelper.SELETECD_DISPRODUCTID, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getClover_id(), TAG));
                contentValues.put(myDbHelper.SELECETD_SIZE, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getSize(), TAG));
                contentValues.put(myDbHelper.SELECETD_UPC, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getUpc(), TAG));
                contentValues.put(myDbHelper.SELECETD_CATEGORY, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getCategory(), TAG));
                contentValues.put(myDbHelper.SELECETD_REATILPRICE, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getRetail_price(), TAG));
                contentValues.put(myDbHelper.SELETECD_DISNAME, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getDiscount_name(), TAG));

                contentValues.put(myDbHelper.SELETECD_USAGE, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getUsage(), TAG));
                contentValues.put(myDbHelper.SELETECD_LIMIT, GlobalFunctions.chkSqlFieldEWntryNull(lv_products.get(i).getLimit(), TAG));
                dbb.insert(myDbHelper.TABLE_NAME3, null, contentValues);
            }
        } catch (Exception e) {
            //Log.d("HappyEnding3", e.toString());// everything else
            e.printStackTrace();
        }
        if (whatToDo.equals("Duplicate")) {
            Toast.makeText(MyApplication.context(), "Duplicate Record Created", Toast.LENGTH_SHORT).show();
//            ShowMessage.message(MyApplication.context(), "Duplicate Record Created");
        } else if (whatToDo.equals("Insert")) {
            Toast.makeText(MyApplication.context(), "Insert Successfully", Toast.LENGTH_SHORT).show();
//            ShowMessage.message(MyApplication.context(), "Insert Successfully");
        } else if (whatToDo.equals("Update")) {
            Toast.makeText(MyApplication.context(), "Updated Record Successfully", Toast.LENGTH_SHORT).show();
//            ShowMessage.message(MyApplication.context(), "Updated Record Successfully");
        } else {

        }
    }

    public List<itemsPojo> searchQueryForProductNameAndUPC(String search_str, String type) {
        String query;
        if (type.equals(Commons.VERTICAL)) {
            query = "SELECT * FROM " + myDbHelper.TABLE_NAME + " where " + myDbHelper.PRODUCT_NAME + " LIKE '%" + search_str + "%'" + " OR " + myDbHelper.PRODUCT_ID + " LIKE '%" + search_str + "%'" + " OR " + myDbHelper.UPC + " LIKE '%" + search_str + "%'";
        } else {
            query = "SELECT * FROM " + myDbHelper.TABLE_NAME + " where " + myDbHelper.PRODUCT_NAME + " LIKE '%" + search_str + "%'" + " OR " + myDbHelper.UPC + " LIKE '%" + search_str + "%'";
        }
        SQLiteDatabase db = myhelper.getWritableDatabase();
        List<itemsPojo> lv_queryProducts = new ArrayList<>();
        Cursor mCursor = db.rawQuery(query, null);
        Log.d("search_str", search_str + "query : " + query);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    itemsPojo item = new itemsPojo();
                    item.setUid(mCursor.getString(mCursor.getColumnIndex(myDbHelper.UID)));
                    item.setProduct_name(mCursor.getString(mCursor.getColumnIndex(myDbHelper.PRODUCT_NAME)));
                    item.setSize(mCursor.getString(mCursor.getColumnIndex(myDbHelper.SIZE)));
                    item.setUpc(mCursor.getString(mCursor.getColumnIndex(myDbHelper.UPC)));
                    if (type.equals(Commons.VERTICAL)) {
                        item.setAddcart(mCursor.getInt(mCursor.getColumnIndex(myDbHelper.IS_ADDTOCART)));
                        item.setStock(mCursor.getString(mCursor.getColumnIndex(myDbHelper.STOCK)));
                        item.setDistributor(mCursor.getString(mCursor.getColumnIndex(myDbHelper.DISTRIBUTOR)));
                        item.setProduct_id(mCursor.getString(mCursor.getColumnIndex(myDbHelper.PRODUCT_ID)));
                    }

                    item.setCategory(mCursor.getString(mCursor.getColumnIndex(myDbHelper.CATEGORY)));
                    item.setRetail_price(mCursor.getString(mCursor.getColumnIndex(myDbHelper.RETAIL_PRICE)));
                    item.setCase_cost(mCursor.getString(mCursor.getColumnIndex(myDbHelper.CASE_COST)));
                    item.setUnits(mCursor.getString(mCursor.getColumnIndex(myDbHelper.UNITS)));
                    item.setBottle_cost(mCursor.getString(mCursor.getColumnIndex(myDbHelper.BOTTLE_COST)));
                    item.setState_min(mCursor.getString(mCursor.getColumnIndex(myDbHelper.STATE_MIN)));
                    item.setClover_id(mCursor.getString(mCursor.getColumnIndex(myDbHelper.CLOVER_ID)));
                    lv_queryProducts.add(item);
                    //  Log.d("lv_queryProducts?>>>>",lv_queryProducts+"");
                } while (mCursor.moveToNext());
            }
        }
        Log.d("lv_queryProducts", lv_queryProducts.toString());
        return lv_queryProducts;


    }

    public List<itemsPojo> getProductItemsAccToPrimaryId(String primary_key, String TAG) {
        List<itemsPojo> product_items = new ArrayList<>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Log.d("product_items>>>>", primary_key + "");
        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.TABLE_NAME3 + " where " + myDbHelper.SELETECD_DISID + "='" + primary_key + "'", null);
        if (cursor.moveToFirst()) {
            do {
                itemsPojo item = new itemsPojo();
                item.setPrimary_id(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISID)));
                item.setDiscount_price(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISPRICE)));
                item.setBottle_cost(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_BOTTLECOST)));
                item.setState_min(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_STATEMINIMUM)));
                item.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISPRODUCTID)));
                item.setPer_type(cursor.getString((cursor.getColumnIndex(myDbHelper.SELETECD_DISTYPE))));
                item.setDiscount_quantity(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISQUANTITY)));
                item.setProduct_name(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_PRODUCTNAME)));
                item.setSize(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_SIZE)));
                item.setUpc(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_UPC)));
                item.setCategory(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_CATEGORY)));
                item.setRetail_price(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_REATILPRICE)));
                product_items.add(item);
                Log.d("product_items>>>>", product_items + "");
            } while (cursor.moveToNext());
            Log.d("product_items>>>>", product_items + "");


        }
        return product_items;
    }

    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    public List<itemsPojo> getProductItemAccToProductId(String[] lv_productsid, String TAG) {
        Log.d("product_id", lv_productsid + "");
        SQLiteDatabase db = myhelper.getWritableDatabase();
        List<itemsPojo> lv_sql_products = new ArrayList<>();
        List<itemsPojo> lv_finalproducts = new ArrayList<>();

        String query = "Select * from " + myDbHelper.TABLE_NAME3 + " where " + myDbHelper.SELETECD_DISPRODUCTID + " IN (" + makePlaceholders(lv_productsid.length) + ")";
        Cursor cursor = db.rawQuery(query, lv_productsid);
        if (cursor.moveToFirst()) {

            do {
                itemsPojo item = new itemsPojo();
                item.setPrimary_id(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISID)));
                item.setDiscount_price(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISPRICE)));
                item.setState_min(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_STATEMINIMUM)));
                item.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISPRODUCTID)));
                item.setPer_type(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISTYPE)));
                item.setDiscount_quantity(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISQUANTITY)));
                item.setProduct_name(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_PRODUCTNAME)));
                item.setSize(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_SIZE)));
                item.setUpc(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_UPC)));
                item.setCategory(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_CATEGORY)));
                item.setRetail_price(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_REATILPRICE)));
                lv_sql_products.add(item);
                Log.d("GERtproduct_items>>>>", item + "");
                Log.d("lv_sql_products>>>>", lv_sql_products + "");
            } while (cursor.moveToNext());


        }


        return lv_sql_products;
    }

    // inventory items get one record
    public itemsPojo getOneRecordFromThirdtableandAddUpFirsttableValues(String clover_id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        //String query = "SELECT * FROM todo WHERE category=" + v;
        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.TABLE_NAME + " where " + myDbHelper.CLOVER_ID + "='" + clover_id + "'", null);
        itemsPojo item = new itemsPojo();
        if (cursor.moveToFirst()) {
            do {

                //item.setUid(cursor.getString(cursor.getColumnIndex(myDbHelper.UID)));
                item.setProduct_name(cursor.getString(cursor.getColumnIndex(myDbHelper.PRODUCT_NAME)));
                item.setSize(cursor.getString(cursor.getColumnIndex(myDbHelper.SIZE)));
                item.setUpc(cursor.getString(cursor.getColumnIndex(myDbHelper.UPC)));

                item.setCategory(cursor.getString(cursor.getColumnIndex(myDbHelper.CATEGORY)));
                item.setRetail_price(cursor.getString(cursor.getColumnIndex(myDbHelper.RETAIL_PRICE)));
                item.setCase_cost(cursor.getString(cursor.getColumnIndex(myDbHelper.CASE_COST)));
                item.setUnits(cursor.getString(cursor.getColumnIndex(myDbHelper.UNITS)));
                item.setBottle_cost(cursor.getString(cursor.getColumnIndex(myDbHelper.BOTTLE_COST)));
                item.setState_min(cursor.getString(cursor.getColumnIndex(myDbHelper.STATE_MIN)));
                //item.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.CLOVER_ID)));
            } while (cursor.moveToNext());

        }
        return item;
    }

    public itemsPojo getOneProductItemAccToProductId(String product_id, String TAG) {
        Log.d("product_id", product_id + "");
        itemsPojo item_1 = new itemsPojo();
        item_1 = getOneRecordFromThirdtableandAddUpFirsttableValues(product_id);
        SQLiteDatabase db = myhelper.getWritableDatabase();
        itemsPojo item = new itemsPojo();

        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.TABLE_NAME3 + " where " + myDbHelper.SELETECD_DISPRODUCTID + "='" + product_id + "'", null);

        if (cursor.moveToFirst()) {
            do {
                item.setPrimary_id(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISID)));
                item.setDiscount_name(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISNAME)));
                item.setDiscount_price(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISPRICE)));
                //item_3.setState_min(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_STATEMINIMUM)));
                item.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISPRODUCTID)));
                item.setPer_type(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISTYPE)));
                item.setDiscount_quantity(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISQUANTITY)));
                item.setProduct_name(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_PRODUCTNAME)));

                item.setLimit(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_LIMIT)));
                item.setUsage(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_USAGE)));
                item.setSize(item_1.getSize());
                item.setUpc(item_1.getUpc());
                item.setCategory(item_1.getCategory());
                item.setRetail_price(item_1.getRetail_price());
                item.setState_min(item_1.getState_min());
                item.setBottle_cost(item_1.getBottle_cost());
                item.setCase_cost(item_1.getCase_cost());
                item.setUnits(item_1.getUnits());

                // lv_sql_products.add(item);
                Log.d("GERtproduct_items>>>>", item + "");
                //Log.d("lv_sql_products>>>>", lv_sql_products + "");
            } while (cursor.moveToNext());

        }
        return item;
    }

    // For getting record of prodcut
    public itemsPojo getParentIdRecord(String product_id, String discount_qunatity) {

        itemsPojo item = new itemsPojo();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Log.d("product_id33", product_id + "");

        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.TABLE_NAME3 +
                " where " + myDbHelper.SELETECD_DISPRODUCTID + "='" + product_id + "' AND " +
                myDbHelper.SELETECD_DISQUANTITY + "<=" + discount_qunatity + " ORDER BY " +
                myDbHelper.SELETECD_DISQUANTITY + " DESC LIMIT 0,1 ", null);
        if (cursor.moveToFirst()) {

            do {

                //item.setPrimary_id(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISID)));
                item.setPrimary_id(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISID)));
                item.setPer_type(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISTYPE)));
                item.setDiscount_quantity(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISQUANTITY)));
                item.setDiscount_price(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISPRICE)));
                item.setState_min(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_STATEMINIMUM)));
                item.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_DISPRODUCTID)));
                item.setProduct_name(cursor.getString(cursor.getColumnIndex(myDbHelper.SELETECD_PRODUCTNAME)));
                item.setSize(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_SIZE)));
                item.setUpc(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_UPC)));
                item.setCategory(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_CATEGORY)));
                item.setRetail_price(cursor.getString(cursor.getColumnIndex(myDbHelper.SELECETD_REATILPRICE)));

            } while (cursor.moveToNext());
        }
        Log.d("lv_parentRecord?>>>>", item + "");
        return item;
    }

    // This is used as update product data(delete existing data acc to primary id and vcreate new one )
    public void deleteListingData(List<itemsPojo> lv_selectedList, String primary_key, String TAG) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Log.d("Isdeleted>>>>", primary_key + "");
        int chk = db.delete(myDbHelper.TABLE_NAME3, myDbHelper.SELETECD_DISID + "=" + primary_key, null);
        inserCreatedDiscountProducts(lv_selectedList, TAG, primary_key, "Update");
        //ShowMessage.message(MyApplication.context(), "Deleted Record Successfully");
    }

    // For removing Discount items
    public void removeDiscounProductstDataAll() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        db.delete(myDbHelper.TABLE_NAME3, null, null);
        db.close();
    }


    // public long saveAddedDiscountBacup(LastCountBackup lastCountBackup, String TAG) {
    //  SQLiteDatabase dbb = myhelper.getWritableDatabase();
    //  long id;
    //  dbb.beginTransaction();
//        try {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(myDbHelper.LINEITEM_ID,
//                    GlobalFunctions.chkSqlFieldEWntryNull(lastCountBackup.getLineitem_id(), TAG));
//            contentValues.put(myDbHelper.LINEITEMDISCOUNT_ID,
//                    GlobalFunctions.chkSqlFieldEWntryNull(lastCountBackup.getLineDiscount_id(), TAG));
//
//
//            id = dbb.insert(myDbHelper.TABLE_ADDEDISCOUNTBACKUP, null, contentValues);
//            Log.d("saveAddedDiscountBacup", id + "");
//            dbb.setTransactionSuccessful();
//        } finally {
//            dbb.endTransaction();
//        }
    // return id;
    // }

    // Save distributors data in locallly
//    public long inserDistributorsData(DistributorsModel distributorsModel, String TAG) {
//        SQLiteDatabase dbb = myhelper.getWritableDatabase();
//        long id;
//        dbb.beginTransaction();
//        try {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(myDbHelper.DISTRIBUTOR_NAME, GlobalFunctions.chkSqlFieldEWntryNull(distributorsModel.getDistributorName(), TAG));
//            id = dbb.insert(myDbHelper.DISTRIBUTOR_TABLE, null, contentValues);
//            dbb.setTransactionSuccessful();
//        } finally {
//            dbb.endTransaction();
//        }
//        // Log.d("inserDistributorsDataid", id + "");
//        return id;
//    }

    // Get Distributors list from local storage
    // Get all List data
//    public List<DistributorsModel> getDistributorsSpinnerItems() {
//        List<DistributorsModel> lv_distributors = new ArrayList<>();
//        SQLiteDatabase db = myhelper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.DISTRIBUTOR_TABLE, null);
//        if (cursor.moveToFirst()) {
//            do {
//                DistributorsModel distributorsModel = new DistributorsModel();
//                distributorsModel.setDistributorName(cursor.getString(cursor.getColumnIndex(myDbHelper.DISTRIBUTOR_NAME)));
//                lv_distributors.add(distributorsModel);
//            } while (cursor.moveToNext());
//        }
//        //Log.d("distributorsModel>>>>", lv_distributors.get(0).getDistributorName() + "");
//        return lv_distributors;
//    }

    // Get Size list from local storage
    // Get all List data

    // Save bacup of last discount data in locallly
    public long saveBackupOfDiscount(LastDiscountBackup discountBackup, String TAG) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        Log.d("discountBackup", discountBackup + "");
        long id;
        dbb.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(myDbHelper.DISCOUNT_BACKUP_NAME, GlobalFunctions.chkSqlFieldEWntryNull(discountBackup.getBkup_discountname(), TAG));
            contentValues.put(myDbHelper.DISCOUNT_BACKUPCLOVER_ID, GlobalFunctions.chkSqlFieldEWntryNull(discountBackup.getBkup_discountname(), TAG));

            contentValues.put(myDbHelper.DISCOUNT_BACKUP_NAME, GlobalFunctions.chkSqlFieldEWntryNull(discountBackup.getBkup_discountname(), TAG));
            contentValues.put(myDbHelper.DISCOUNT_BACKUP_TYPE, GlobalFunctions.chkSqlFieldEWntryNull(discountBackup.getBkup_discounttype(), TAG));
            contentValues.put(myDbHelper.DISCOUNT_BACKUP_DISCOUNT, GlobalFunctions.chkSqlFieldEWntryNull(discountBackup.getBkup_discount(), TAG));
            id = dbb.insert(myDbHelper.DISCOUNT_BACKUP, null, contentValues);
            dbb.setTransactionSuccessful();
        } finally {
            dbb.endTransaction();
        }
        Log.d("saveBackupOfDiscount", id + "");
        return id;
    }

    //Get last discount bacup
    public List<LastDiscountBackup> getLastDiscountBackup() {
        List<LastDiscountBackup> lv_backup = new ArrayList<>();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.DISCOUNT_BACKUP, null);
        if (cursor.moveToFirst()) {
            do {
                LastDiscountBackup lastDiscountBackup = new LastDiscountBackup();
                lastDiscountBackup.setBkup_discountid(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_BACKUP_ID)));
                lastDiscountBackup.setBkup_discounttype(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_BACKUP_TYPE)));
                lastDiscountBackup.setBkup_discountname(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_BACKUP_NAME)));
                lastDiscountBackup.setBkup_discount(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_BACKUP_DISCOUNT)));
                lv_backup.add(lastDiscountBackup);
            } while (cursor.moveToNext());
        }
        Log.d("distributorsModel>>>>", lv_backup + "");
        return lv_backup;
    }

    //     UPdate discount data accoarding to discount id
    public int updateDiscount(LastDiscountBackup lastDiscountBackup, String TAG) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        Log.d("lastDiscountBack", lastDiscountBackup + "");
        ContentValues values = new ContentValues();
        //  values.put(myDbHelper.DISCOUNT_BACKUP_NAME, GlobalFunctions.chkSqlFieldEWntryNull(lastDiscountBackup.getBkup_discountname(), TAG));
        values.put(myDbHelper.DISCOUNT_BACKUP_DISCOUNT, GlobalFunctions.chkSqlFieldEWntryNull(lastDiscountBackup.getBkup_discount(), TAG));
        int i = db.update(myDbHelper.DISCOUNT_BACKUP, values, myDbHelper.DISCOUNT_BACKUP_ID + " = " + lastDiscountBackup.getBkup_discountid(), null);
        // ShowMessage.message(MyApplication.context(), "Updated Record");
        return i;
    }

    // Save distributors data in locallly
    public long insertQunatityValue(itemsPojo itemsPojo, String TAG) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        long id;
        dbb.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(myDbHelper.DISCOUNT_QUNATITY_ID, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getPrimary_id(), TAG));
            contentValues.put(myDbHelper.DISCOUNT_TOTAL_QUNATITY, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getTotal_quantity(), TAG));
            contentValues.put(myDbHelper.DISCOUNT_CLOVER_ID, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getClover_id(), TAG));
            contentValues.put(myDbHelper.DISCOUNT_START_QUNATITY, GlobalFunctions.chkSqlFieldEWntryNull(itemsPojo.getDiscount_quantity(), TAG));
            id = dbb.insert(myDbHelper.DISCOUNT_QUNATITY, null, contentValues);
            dbb.setTransactionSuccessful();
        } finally {
            dbb.endTransaction();
        }
        Log.d("insertQunatityValue", id + "");
        return id;
    }

    //Get last discount bacup
    public itemsPojo getInsertQunatityValue(String clover_id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        itemsPojo itemsPojo = new itemsPojo();
        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.DISCOUNT_QUNATITY + " where " + myDbHelper.DISCOUNT_QUNATITY_ID + "='" + clover_id + "'", null);
        if (cursor.moveToFirst()) {
            do {

                itemsPojo.setPrimary_id(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_QUNATITY_ID)));
                itemsPojo.setTotal_quantity(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_TOTAL_QUNATITY)));
                itemsPojo.setDiscount_quantity(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_START_QUNATITY)));
                itemsPojo.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_CLOVER_ID)));
            } while (cursor.moveToNext());
        }
        Log.d("lv_qunatities>>>>", itemsPojo + "");
        return itemsPojo;
    }

    //Get last discount bacup
    public itemsPojo getOneInsertQunatityValue(String clover_id) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        itemsPojo itemsPojo = new itemsPojo();
        Cursor cursor = db.rawQuery("Select * from " + myDbHelper.DISCOUNT_QUNATITY + " where " + myDbHelper.DISCOUNT_CLOVER_ID + "='" + clover_id + "'", null);
        if (cursor.moveToFirst()) {
            do {
                itemsPojo.setPrimary_id(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_QUNATITY_ID)));
                itemsPojo.setTotal_quantity(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_TOTAL_QUNATITY)));
                itemsPojo.setDiscount_quantity(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_START_QUNATITY)));
                itemsPojo.setClover_id(cursor.getString(cursor.getColumnIndex(myDbHelper.DISCOUNT_CLOVER_ID)));
            } while (cursor.moveToNext());
        }
        Log.d("lv_qunatities>>>>", itemsPojo + "");
        return itemsPojo;
    }

    // UPdate discount data accoarding to discount id
    public int updateQunatityValue(itemsPojo itemsPojo, String TAG) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(myDbHelper.DISCOUNT_TOTAL_QUNATITY, itemsPojo.getTotal_quantity());

        int i = db.update(myDbHelper.DISCOUNT_QUNATITY, values, myDbHelper.DISCOUNT_QUNATITY_ID + " = '" + itemsPojo.getPrimary_id() + "'", null);
        // ShowMessage.message(MyApplication.context(), "Updated Record");
        return i;

    }

    // For removing Discount items
    public void removeLastBackupTableData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        db.delete(myDbHelper.DISCOUNT_BACKUP, null, null);
        db.delete(myDbHelper.DISCOUNT_QUNATITY, null, null);
        db.close();
    }


    static class myDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "cloverDataApp";    // Database Name
        private static final String TABLE_NAME = "cloverListTable";   // Table Name
        private static final String TABLE_NAME2 = "discountTable";
        private static final String TABLE_NAME3 = "discountIdsTable";
        private static final String DISTRIBUTOR_TABLE = "distributorTable";
        private static final String DISCOUNT_BACKUP = "last_discount_bckup";
        private static final String TABLE_ADDEDISCOUNTBACKUP = "LineItemsDiscountBackup";

        private static final String DISCOUNT_QUNATITY = "discount_quantity_table";

        private static final int DATABASE_Version = 1;    // Database Version

        private static final String DISCOUNT_QUNATITY_ID = "discount_quantity_id";
        private static final String DISCOUNT_CLOVER_ID = "discount_clover_id";
        private static final String DISCOUNT_TOTAL_QUNATITY = "discount_total_quantity";
        private static final String DISCOUNT_START_QUNATITY = "discount_startup_quantity";
        //fields for products(1st table)
        private static final String UID = "id";     // Column I (Primary Key)
        private static final String PRODUCT_NAME = "product_name";    //Column II
        private static final String PRODUCT_ID = "product_id";
        private static final String IS_ADDTOCART = "add_cart";
        private static final String SIZE = "size";    // Column III
        private static final String UPC = "upc";
        private static final String STOCK = "stock";
        private static final String SERVICE_CHARGE = "servicecharge";

        private static final String DISTRIBUTOR = "distributor";
        private static final String CATEGORY = "category";
        private static final String RETAIL_PRICE = "retail_price";
        private static final String CASE_COST = "case_cost";
        private static final String UNITS = "units";
        private static final String BOTTLE_COST = "bottle_cost";
        private static final String STATE_MIN = "state_min";
        private static final String CLOVER_ID = "clover_id";


        // Fields for all discounts table(2nd table)
        private static final String DIS_ID = "discount_id";
        private static final String DIS_NAME = "discount_name";
        private static final String DISCOUNT_SERVERID = "discount_serverid";
        private static final String DIS_TYPE = "type";
        private static final String QUANTITY = "quantity";
        private static final String EXPIRAY = "expires";
        private static final String PRICE = "price";
        private static final String STATUS = "status";
        private static final String USAGE = "usage";
        private static final String LIMIT = "setlimit";

        // Fields for parent id(complete table of discount with selected products)(3rd table)
        private static final String SELETECD_ID = "Selected_id";
        private static final String SELETECD_PRODUCTNAME = "Selected_proname";
        private static final String SELETECD_DISID = "Selected_discountId";
        private static final String SELETECD_DISQUANTITY = "Selected_disqunatity";
        private static final String SELETECD_DISNAME = "Selected_disname";
        private static final String SELETECD_DISTYPE = "Selected_distype";

        private static final String SELETECD_DISPRICE = "Selected_disprice";
        private static final String SELETECD_DISPRODUCTID = "Selected_disproductId";
        private static final String SELECETD_SIZE = "Selected_size";
        private static final String SELECETD_UPC = "Selected_upc";
        private static final String SELECETD_CATEGORY = "Selected_category";
        private static final String SELECETD_REATILPRICE = "Selected_retailPrice";
        private static final String SELECETD_BOTTLECOST = "Selected_bottlecost";
        private static final String SELECETD_STATEMINIMUM = "Selected_stateminimum";
        private static final String LINEITEM_ID = "lineItem_id";
        private static final String BACKUPTABLE_ID = "backup_id";
        private static final String LINEITEMDISCOUNT_ID = "lineItemDiscount_id";
        private static final String TAX = "tax";

        private static final String SELETECD_USAGE = "Selected_usage";
        private static final String SELETECD_LIMIT = "Selected_limit";
        private static final String DISTRIBUTOR_NAME = "distributor_name";

//  Table for saving last discount, bcoz when limit os over then we will use last applied discount


        private static final String DISCOUNT_BACKUP_ID = "backup_id";
        private static final String DISCOUNT_BACKUPCLOVER_ID = "backup_clover_id";
        private static final String DISCOUNT_BACKUP_NAME = "backup_name";
        private static final String DISCOUNT_BACKUP_DISCOUNT = "backup_applieddiscount";
        private static final String DISCOUNT_BACKUP_TYPE = "backup_type";


        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " VARCHAR(255) ," + PRODUCT_ID + " VARCHAR(255), " + PRODUCT_NAME + " VARCHAR(255) ," + SIZE + " VARCHAR(225), " + UPC + " VARCHAR(255) " +
                ", " + CATEGORY + " VARCHAR(255) , " + RETAIL_PRICE + " VARCHAR(255) , " + CASE_COST + " VARCHAR(255) , " + UNITS + " VARCHAR(255) ," + TAX + " VARCHAR(255) " +

                ", " + BOTTLE_COST + " VARCHAR(255) , " + STATE_MIN + " VARCHAR(255) , " + STOCK + " VARCHAR(255) , " + DISTRIBUTOR + " VARCHAR(255) ," + CLOVER_ID + " VARCHAR(255), " + IS_ADDTOCART + " INTEGER DEFAULT 0);";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


        private static final String CREATE_TABLE2 = "CREATE TABLE " + TABLE_NAME2 +
                " (" + DIS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " + DIS_NAME + " VARCHAR(255) , " + DISCOUNT_SERVERID + " VARCHAR(255), " + DIS_TYPE + " VARCHAR(225), " + QUANTITY + " VARCHAR(255) " +
                ", " + EXPIRAY + " VARCHAR(255) , " + PRICE + " VARCHAR(255) ,  " + LIMIT + " VARCHAR(255) , " + USAGE + " VARCHAR(255) , " + STATUS + " VARCHAR(255) );";
        private static final String DROP_TABLE2 = "DROP TABLE IF EXISTS " + TABLE_NAME2;


        private static final String CREATE_TABLE3 = "CREATE TABLE " + TABLE_NAME3 +
                " (" + SELETECD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SELETECD_PRODUCTNAME + " VARCHAR(255) ," + SELECETD_SIZE +
                " VARCHAR(225), " + SELECETD_CATEGORY + " VARCHAR(255) " +
                ", " + SELECETD_UPC + " VARCHAR(255) , " + SELECETD_REATILPRICE + " VARCHAR(255) ,"
                + SELETECD_DISID + " VARCHAR(255)," + SELETECD_DISQUANTITY + " VARCHAR(255),"
                + SELETECD_DISPRICE + " VARCHAR(255)," + SELECETD_STATEMINIMUM + " VARCHAR(255)," + SELECETD_BOTTLECOST + " VARCHAR(255),"
                + SELETECD_DISPRODUCTID + " VARCHAR(255)," + SELETECD_USAGE + " VARCHAR(255)," + SELETECD_LIMIT + " VARCHAR(255)," + SELETECD_DISNAME + " VARCHAR(255)," + SELETECD_DISTYPE + " VARCHAR(255) );";
        private static final String DROP_TABLE3 = "DROP TABLE IF EXISTS " + TABLE_NAME3;


        private static final String CREATE_TABLE4 = "CREATE TABLE " + TABLE_ADDEDISCOUNTBACKUP +
                " (" + BACKUPTABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + LINEITEMDISCOUNT_ID + " VARCHAR(255) ," + LINEITEM_ID + " VARCHAR(225));";
        private static final String DROP_TABLE4 = "DROP TABLE IF EXISTS " + TABLE_ADDEDISCOUNTBACKUP;


        private static final String CREATE_DISTRIBUTOR = "CREATE TABLE " + DISTRIBUTOR_TABLE + " (  " + DISTRIBUTOR_NAME + " VARCHAR(255) );";
        private static final String DROP_DISTRIBUTOR = "DROP TABLE IF EXISTS " + DISTRIBUTOR_TABLE;


        private static final String CREATE_DISCOUNT_BACKUP = "CREATE TABLE " + DISCOUNT_BACKUP + " (  " + DISCOUNT_BACKUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " + DISCOUNT_BACKUP_NAME + " VARCHAR(255) , " + DISCOUNT_BACKUP_DISCOUNT + " VARCHAR(255) ," + DISCOUNT_BACKUPCLOVER_ID + " VARCHAR(255) ,    " + DISCOUNT_BACKUP_TYPE + " VARCHAR(255) );";
        private static final String DROP_DISCOUNT_BACKUP = "DROP TABLE IF EXISTS " + DISCOUNT_BACKUP;

        private static final String CREATE_DISCOUNT_QUNATITY = "CREATE TABLE " + DISCOUNT_QUNATITY + " (  " + DISCOUNT_QUNATITY_ID + " VARCHAR(255) , " + DISCOUNT_TOTAL_QUNATITY + " VARCHAR(255) , " + DISCOUNT_CLOVER_ID + " VARCHAR(255), " + DISCOUNT_START_QUNATITY + " VARCHAR(255));";
        private static final String DROP_DISCOUNT_QUNATITY = "DROP TABLE IF EXISTS " + DISCOUNT_QUNATITY;

        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {
            Log.e("onCreate", "" + db);
            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_TABLE2);
                db.execSQL(CREATE_TABLE3);
                db.execSQL(CREATE_TABLE4);
                db.execSQL(CREATE_DISTRIBUTOR);
                db.execSQL(CREATE_DISCOUNT_BACKUP);
                db.execSQL(CREATE_DISCOUNT_QUNATITY);
            } catch (Exception e) {
                // ShowMessage.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.e("onUpgrade", "" + db);

            try {
                // ShowMessage.message(context, "OnUpgrade");
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                onCreate(db);
                db.execSQL(DROP_TABLE);
                db.execSQL(DROP_TABLE2);
                db.execSQL(DROP_TABLE3);
                db.execSQL(DROP_TABLE4);
                db.execSQL(DROP_DISTRIBUTOR);
                db.execSQL(DROP_DISCOUNT_BACKUP);
                db.execSQL(DROP_DISCOUNT_QUNATITY);
                db.execSQL(DROP_DISCOUNT_QUNATITY);
                onCreate(db);
            } catch (Exception e) {
                // ShowMessage.message(context, "" + e);
            }
        }
    }

}
