package com.rovertech.utomo.app.bookings;

import com.rovertech.utomo.app.bookings.model.BookingRequest;
import com.rovertech.utomo.app.bookings.model.RequestForBooking;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by raghavthakkar on 13-04-2016.
 */
public interface BookingRequestAPI {

    @POST(AppConstant.BOOKING_ACTIVITIES)
    Call<RequestForBooking> bookingService(@Body BookingRequest bookingRequest);


}
