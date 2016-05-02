package com.rovertech.utomo.app.main.notification.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.notification.model.RescheduleBookingRequest;
import com.rovertech.utomo.app.main.notification.model.RescheduleResp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by vaibhavirana on 29-04-2016.
 */
public interface RescheduleBookingApi {

    @POST(AppConstant.RESCHEDUAL_BOOKING)
    Call<RescheduleResp> RescheduleBooking(@Body RescheduleBookingRequest request);
}
