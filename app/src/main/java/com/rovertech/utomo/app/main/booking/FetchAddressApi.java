package com.rovertech.utomo.app.main.booking;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.booking.model.AddressResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sagartahelyani on 11-05-2016.
 */
public interface FetchAddressApi {

    @GET(AppConstant.FETCH_ADDRESS)
    Call<AddressResponse> fetchAddress(@Path("USERID") int userId);
}
