package com.rovertech.utomo.app.invoice.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.helper.RetrofitErrorHelper;
import com.rovertech.utomo.app.invoice.OrderDetailsActivity;
import com.rovertech.utomo.app.invoice.model.PaymentApiRequest;
import com.rovertech.utomo.app.invoice.model.PaymentApiResponse;
import com.rovertech.utomo.app.invoice.model.PaymentProcessResponse;
import com.rovertech.utomo.app.invoice.service.FetchPaymentDetailsService;
import com.rovertech.utomo.app.invoice.service.PaymentApiService;

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

    @Override
    public void doPayment(long totalDiscount, int bookingId, long offerId, PaymentProcessResponse paymentProcessResponse, int serviceCentreId) {

        if (view != null) {
            view.showProgress();
        }

        final PaymentApiRequest apiRequest = new PaymentApiRequest();
        apiRequest.AdminDiscount = totalDiscount;
        apiRequest.BookingID = bookingId;
        apiRequest.SCOfferDiscount = paymentProcessResponse.PaymentProcess.Data.get(0).SCOfferDiscount;
        apiRequest.OfferID = (int) offerId;
        apiRequest.PayableAmount = paymentProcessResponse.PaymentProcess.Data.get(0).PayableAmount - totalDiscount;
        apiRequest.ServiceCentreId = serviceCentreId;
        apiRequest.TotalAmount = paymentProcessResponse.PaymentProcess.Data.get(0).TotalAmount;
        apiRequest.UserId = PrefUtils.getUserID(mContext);

        Log.e("req", Functions.jsonString(apiRequest));

        PaymentApiService apiService = UtomoApplication.retrofit.create(PaymentApiService.class);
        Call<PaymentApiResponse> call = apiService.doPayment(apiRequest);
        call.enqueue(new Callback<PaymentApiResponse>() {
            @Override
            public void onResponse(Call<PaymentApiResponse> call, Response<PaymentApiResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    PaymentApiResponse paymentApiResponse = response.body();

                    Log.e("res", Functions.jsonString(paymentApiResponse));
                    if (paymentApiResponse.Payment.ResponseCode == 1) {
                        Functions.showToast(mContext, "Payment Success");

                        Intent paymentIntent = new Intent(mContext, OrderDetailsActivity.class);
                        paymentIntent.putExtra("payment", new Gson().toJson(paymentApiResponse));
                        paymentIntent.putExtra("SCOfferDiscount", apiRequest.SCOfferDiscount);
                        mContext.startActivity(paymentIntent);

                    } else {
                        view.hideProgress();
                        Functions.showToast(mContext, paymentApiResponse.Payment.ResponseMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentApiResponse> call, Throwable t) {

            }
        });
    }
}
