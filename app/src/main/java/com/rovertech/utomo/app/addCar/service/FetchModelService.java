package com.rovertech.utomo.app.addCar.service;

import com.rovertech.utomo.app.addCar.model.MakeModel;
import com.rovertech.utomo.app.addCar.model.VehicleModel;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public interface FetchModelService {

    @GET(AppConstant.FETCH_MODEL)
    Call<VehicleModel> fetchModels(@Path("DEALERSHIP") String dealerShip, @Path("YEAR") String year);
}
