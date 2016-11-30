package com.rovertech.utomo.app.bookings.CurrentBooking.service;

import com.rovertech.utomo.app.bookings.CurrentBooking.model.UserBookingsResponse;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by raghavthakkar on 13-04-2016.
 */
public interface BookingActivitiesAPI {

    String USERID = "userID";
    String MODE = "mode";
    int CurrentBooking = 1;
    int PastBooking = 2;

    @GET(AppConstant.USER_BOOKINGS + "{" + USERID + "}/{" + MODE + "}")
    Call<UserBookingsResponse> fetchUserBooking(@Path(USERID) int userID, @Path(MODE) int mode);
}
