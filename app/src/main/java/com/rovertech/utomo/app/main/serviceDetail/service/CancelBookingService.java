package com.rovertech.utomo.app.main.serviceDetail.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.serviceDetail.model.CancelBookingOutput;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sagartahelyani on 18-04-2016.
 */
public interface CancelBookingService {

    @GET(AppConstant.CANCEL_BOOKING)
    Call<CancelBookingOutput> doCancel(@Path("BOOKINGID") int bookingId);
}
