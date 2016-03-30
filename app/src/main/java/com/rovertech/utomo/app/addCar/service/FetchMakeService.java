package com.rovertech.utomo.app.addCar.service;

import com.rovertech.utomo.app.addCar.model.FetchMakeModel;
import com.rovertech.utomo.app.addCar.model.MakeModel;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public interface FetchMakeService {

    @GET(AppConstant.FETCH_MAKE)
    Call<MakeModel> fetchMake();
}
