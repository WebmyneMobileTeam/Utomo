package com.rovertech.utomo.app.invoice.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.home.car.model.DashboardRequest;
import com.rovertech.utomo.app.invoice.model.PaymentApiRequest;
import com.rovertech.utomo.app.invoice.model.PaymentApiResponse;
import com.rovertech.utomo.app.invoice.model.PaymentProcessResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by priyasindkar on 29-04-2016.
 */
public interface PaymentApiService {

    @POST(AppConstant.DO_PAYMENT)
    Call<PaymentApiResponse> doPayment(@Body PaymentApiRequest request);
}
