package com.rovertech.utomo.app.invoice.presenter;

import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.RetrofitErrorHelper;
import com.rovertech.utomo.app.invoice.model.PaymentProcessResponse;
import com.rovertech.utomo.app.invoice.service.FetchPaymentDetailsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyasindkar on 29-04-2016.
 */
public class InvoicePresenterImpl implements InvoicePresenter {
    private Context mContext;
    private InvoiceView view;

    public InvoicePresenterImpl(Context mContext, InvoiceView view) {
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public void getTransactionProcessDetails(int bookingId) {
        if (view != null) {
            view.showProgress();
        }

        //call WS
        FetchPaymentDetailsService paymentDetailsService = UtomoApplication.retrofit.create(FetchPaymentDetailsService.class);
        Call<PaymentProcessResponse> call = paymentDetailsService.fetchPaymentDetails(bookingId);

        call.enqueue(new Callback<PaymentProcessResponse>() {
            @Override
            public void onResponse(Call<PaymentProcessResponse> call, Response<PaymentProcessResponse> response) {
                view.hideProgress();
                if (response.body() == null) {
                    Functions.showToast(mContext, "Error occurred.");
                } else {
                    Log.e("onResponse", Functions.jsonString(response.body()));
                    view.setPaymentAndOfferDetails(response.body());
                }
            }

            @Override
            public void onFailure(Call<PaymentProcessResponse> call, Throwable t) {
                view.hideProgress();
                Log.e("ERROR", t.toString());
                RetrofitErrorHelper.showErrorMsg(t, mContext);
            }
        });


    }
}
