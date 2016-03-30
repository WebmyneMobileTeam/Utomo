package com.rovertech.utomo.app.addCar.service;

import com.rovertech.utomo.app.addCar.model.MakeModel;
import com.rovertech.utomo.app.addCar.model.YearModel;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sagartahelyani on 25-03-2016.
 */
public interface FetchYearService {

    @GET(AppConstant.FETCH_YEAR)
    Call<YearModel> fetchYear(@Path("DEALERSHIP") String dealerShip);
}
