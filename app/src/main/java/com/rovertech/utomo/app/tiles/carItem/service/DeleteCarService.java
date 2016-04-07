package com.rovertech.utomo.app.tiles.carItem.service;

import com.rovertech.utomo.app.account.model.ManiBasicLoginSignUp;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.tiles.carItem.model.DeleteVehicleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public interface DeleteCarService {

    @GET(AppConstant.DELETE_CAR)
    Call<DeleteVehicleResponse> deletCar(@Path("USERID") int userId, @Path("VEHICLELID") int vehicleId);
}
