package com.rovertech.utomo.app.main.serviceDetail.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sagartahelyani on 15-04-2016.
 */
public interface BookingDetailService {

    @GET(AppConstant.BOOKING_DETAILS)
    Call<UserBookingDetailsResponse> fetchDetails(@Path("BOOKINGID") int bookingId);
}
