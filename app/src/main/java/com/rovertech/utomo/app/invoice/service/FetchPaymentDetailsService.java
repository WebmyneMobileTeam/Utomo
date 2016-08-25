package com.rovertech.utomo.app.invoice.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.invoice.model.PaymentProcessResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by priyasindkar on 29-04-2016.
 */
public interface FetchPaymentDetailsService {

    @GET(AppConstant.FETCH_TRANSACTION_PAYMENT_DETAILS)
    Call<PaymentProcessResponse> fetchPaymentDetails(@Path("BOOKINGID") int bookingId);
}
