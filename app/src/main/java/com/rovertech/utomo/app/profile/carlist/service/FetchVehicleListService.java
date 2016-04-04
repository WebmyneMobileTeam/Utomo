package com.rovertech.utomo.app.profile.carlist.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.centerListing.model.CentreListRequest;
import com.rovertech.utomo.app.profile.carlist.model.FetchVehicleRequest;
import com.rovertech.utomo.app.profile.carlist.model.VehicleListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sagartahelyani on 30-03-2016.
 */
public interface FetchVehicleListService {

    @POST(AppConstant.FETCH_VEHICLE_LIST)
    Call<VehicleListResponse> doFetchVehicleList(@Body FetchVehicleRequest request);
}
