package com.rovertech.utomo.app.account.service;

import com.rovertech.utomo.app.account.model.CityOutput;
import com.rovertech.utomo.app.account.model.CityRequest;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sagartahelyani on 29-03-2016.
 */
public interface FetchCityService {

    @POST(AppConstant.FETCH_CITY)
    Call<CityOutput> doFetchCity(@Body CityRequest request);
}
