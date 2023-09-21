package com.example.haglandroidapp.Activities.CloverConnectror;

import android.util.Log;

import com.clover.remote.Challenge;
import com.clover.remote.client.DefaultCloverConnectorListener;
import com.clover.remote.client.ICloverConnector;
import com.clover.remote.client.messages.ConfirmPaymentRequest;
import com.clover.remote.client.messages.SaleResponse;
import com.clover.remote.client.messages.VerifySignatureRequest;
import com.clover.sdk.v3.payments.Payment;

public class TestListener extends DefaultCloverConnectorListener {
    private String TAG;

    public TestListener(ICloverConnector cloverConnector) {
        super(cloverConnector);
    }

    @Override
    public void onConfirmPaymentRequest(ConfirmPaymentRequest request) {
        Log.d(TAG, "Confirm Payment Request");
//        addText("Confirm Payment Request");

        Challenge[] challenges = request.getChallenges();
        if (challenges != null && challenges.length > 0) {
            for (Challenge challenge : challenges) {
                Log.d(TAG, "Received a challenge: " + challenge.type);
//                addText("Received a challenge: " + challenge.type);
            }
        }

//        addText("Automatically processing challenges");
        Log.d(TAG, "Automatically processing challenges");
        cloverConnector.acceptPayment(request.getPayment());
    }

    @Override
    public void onSaleResponse(SaleResponse response) {
        try {
            if (response.isSuccess()) {
                Payment payment = response.getPayment();
//                if (payment.getExternalPaymentId().equals(pendingSale.getExternalId())) {
                    String saleRequest = ("Sale Request Successful\n");
                    saleRequest += ("  ID: " + payment.getId() + "\n");
                    saleRequest += ("  External ID: " + payment.getExternalPaymentId() + "\n");
                    saleRequest += ("  Order ID: " + payment.getOrder().getId() + "\n");
                    saleRequest += ("  Amount: " + payment.getAmount() + "\n");
                    saleRequest += ("  Tip Amount: " + payment.getTipAmount() + "\n");
                    saleRequest += ("  Tax Amount: " + payment.getTaxAmount() + "\n");
                    saleRequest += ("  Offline: " + payment.getOffline() + "\n");
                    saleRequest += ("  Authorization Code: " + payment.getCardTransaction().getAuthCode() + "\n");
                    saleRequest += ("  Card Type: " + payment.getCardTransaction().getCardType() + "\n");
                    saleRequest += ("  Last 4: " + payment.getCardTransaction().getLast4());
//                    addText(saleRequest);
                    Log.d(TAG, "sales request: " + saleRequest);
//                } else {
////                    addText("Sale Request/Response mismatch - " + pendingSale.getExternalId() + " vs " + payment.getExternalPaymentId());
//                    Log.d(TAG, "Sale Request/Response mismatch - " + pendingSale.getExternalId() + " vs " + payment.getExternalPaymentId());
//                }
            } else {
//                addText("Sale Request Failed - " + response.getReason());
                Log.d(TAG, "Sale Request Failed - " + response.getReason());
            }
        } catch (Exception ex) {
            Log.d(TAG, "Error handling sale response");
            ex.printStackTrace();
        }
    }

    @Override
    public void onVerifySignatureRequest(VerifySignatureRequest request) {
        super.onVerifySignatureRequest(request);
//        addText("Verify Signature Request - Signature automatically accepted by default");
        Log.d(TAG, "Verify Signature Request - Signature automatically accepted by default");
    }


}
