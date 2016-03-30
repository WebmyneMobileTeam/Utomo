package com.rovertech.utomo.app.main.centerListing.service;

import com.rovertech.utomo.app.account.model.BasicLoginRequest;
import com.rovertech.utomo.app.account.model.ManiBasicLoginSignUp;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.centerListing.model.CentreListRequest;
import com.rovertech.utomo.app.main.centerListing.model.CentreListResponse;
import com.rovertech.utomo.app.main.centerListing.model.FetchServiceCentreList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sagartahelyani on 28-03-2016.
 */
public interface FetchServiceCentreListService {

    @POST(AppConstant.FETCH_SERVICE_CENTRELIST)
    Call<CentreListResponse> doFetchCentreList(@Body CentreListRequest centreListRequest);
}
