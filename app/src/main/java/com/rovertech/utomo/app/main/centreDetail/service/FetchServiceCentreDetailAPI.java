package com.rovertech.utomo.app.main.centreDetail.service;


import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by raghavthakkar on 30-03-2016.
 */
public interface FetchServiceCentreDetailAPI {

    @GET(AppConstant.FETCH_SERVICE_CENTRE_DETAIL + "{ServiceCentreID}")
    Call<FetchServiceCentreDetailResponse> fetchServiceCentreDetail(@Path("ServiceCentreID") int serviceCentreID);


}
