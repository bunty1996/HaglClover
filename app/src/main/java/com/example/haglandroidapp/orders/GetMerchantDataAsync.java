package com.example.haglandroidapp.orders;

import android.accounts.Account;
import android.os.AsyncTask;
import android.util.Log;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.util.CloverAuth;
import com.example.haglandroidapp.Activities.DashboardActivity;
import com.example.haglandroidapp.Handler.GetMerchnatResult;
import com.example.haglandroidapp.Models.GlobalFunctions;
import com.example.haglandroidapp.Utils.CSPreferences;
import com.example.haglandroidapp.Utils.Commons;
import com.example.haglandroidapp.Utils.MyApplication;
import com.example.haglandroidapp.Utils.SharedPref;
import com.example.haglandroidapp.Utils.Utils;
import com.example.haglandroidapp.signup.signupallsteps.model.SignupModel;


public class GetMerchantDataAsync extends AsyncTask<Object, Void, SignupModel> {

    private static final String TAG = GetMerchantDataAsync.class.getSimpleName();
    Account mAccount;
    static GetMerchnatResult getMerchnatResult;

    public GetMerchantDataAsync() {

    }

    @Override
    protected SignupModel doInBackground(Object... params) {
        SignupModel signupModel = null;
        try {
            CloverAuth.AuthResult authResult = CloverAuth.authenticate(MyApplication.context());
            if (authResult == null) {
                Log.e("AUTHRESULTNULL>>>", Commons.NULL_AUTHRESULT);
            } else if (authResult.authToken == null) {
                Log.e("NOAUTHTOKEN>>", Commons.NO_AUTHTOKEN);
            } else {
                Log.e("authResult>>", authResult + "");
                String merchant_id = authResult.merchantId;
                SharedPref.init(MyApplication.context());
                SharedPref.saveMerchantId(Commons.MERCHANT_ID, merchant_id);
                signupModel = new SignupModel();
                signupModel.setAuth_token(authResult.authToken);
                CSPreferences.putString(MyApplication.context(), "authToken", authResult.authToken);
                Utils.TOKEN = authResult.authToken;
                signupModel.setMerchant_id(authResult.merchantId);
                CSPreferences.putString(MyApplication.context(), "merchantID", merchant_id);
                SharedPref.saveMerchantId(Commons.MERCHANT_ID, merchant_id);
                signupModel.setOwner_name(GlobalFunctions.getSplittedFullName(
                        authResult.authData.get(Commons.AUTH_ACCOUNT) + ""));
                signupModel.setEmail(GlobalFunctions.getSplittedEmail(
                        authResult.authData.get(Commons.AUTH_ACCOUNT) + ""));
                DashboardActivity.getInventeryItems();
            }

        } catch (Exception e) {
            Log.e(TAG, Commons.ERROR_AUTHRES, e);
        }

        return signupModel;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mAccount = CloverAccount.getAccount(MyApplication.context());
        //Log.d("mAccountsdff",mAccount.toString());

    }

    public static void setMerchantDataChangeListener(GetMerchnatResult getMerchantData) {
        getMerchnatResult = getMerchantData;
    }


    @Override
    protected void onPostExecute(SignupModel signupModel) {
        super.onPostExecute(signupModel);
//        getMerchnatResult.getMerchantResult(signupModel);
    }


}
