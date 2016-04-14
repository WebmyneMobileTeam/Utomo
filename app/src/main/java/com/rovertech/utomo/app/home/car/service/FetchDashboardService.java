package com.rovertech.utomo.app.home.car.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.home.car.model.DashboardRequest;
import com.rovertech.utomo.app.home.car.model.DashboardResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sagartahelyani on 12-04-2016.
 */
public interface FetchDashboardService {

    @POST(AppConstant.FETCH_DASHBOARD)
    Call<DashboardResponse> doFetchDashboard(@Body DashboardRequest request);

}
