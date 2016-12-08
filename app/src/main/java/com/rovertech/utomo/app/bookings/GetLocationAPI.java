package com.rovertech.utomo.app.bookings;

import com.rovertech.utomo.app.bookings.model.LocationResponse;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.notification.model.NotificationResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by vatsaldesai on 08-12-2016.
 */

public interface GetLocationAPI {

    @GET(AppConstant.GET_LOCATION)
    Call<LocationResponse> getLocationFromZip(@Path("PIN") String pin);
}
