package com.rovertech.utomo.app.main.centreDetail;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailResponse;
import com.rovertech.utomo.app.main.centreDetail.service.CentreDetailsView;
import com.rovertech.utomo.app.main.centreDetail.service.FetchServiceCentreDetailAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 30-03-2016.
 */
public class CentreDetailsPresnterImpl implements CentreDetailsPresnter {


    private CentreDetailsView mCentreDetailsView;
    private FetchServiceCentreDetailResponse fetchServiceCentreDetailResponse;

    public CentreDetailsPresnterImpl(CentreDetailsView mCentreDetailsView) {
        this.mCentreDetailsView = mCentreDetailsView;
        init();
    }

    @Override
    public void init() {

        mCentreDetailsView.initToolbar();
    }

    @Override
    public void fetchServiceCenterDetails(int serviceCentreID) {

        FetchServiceCentreDetailAPI fetchServiceCentreDetailAPI = UtomoApplication.retrofit.create(FetchServiceCentreDetailAPI.class);

        retrofit2.Call<FetchServiceCentreDetailResponse> fetchServiceCentreDetailCall = fetchServiceCentreDetailAPI.fetchServiceCentreDetail(2);
        fetchServiceCentreDetailCall.enqueue(new Callback<FetchServiceCentreDetailResponse>() {
            @Override
            public void onResponse(Call<FetchServiceCentreDetailResponse> call, Response<FetchServiceCentreDetailResponse> response) {

                if (response.isSuccess()) {

                    fetchServiceCentreDetailResponse = response.body();
                    if (fetchServiceCentreDetailResponse.fetchServiceCentreDetail != null && fetchServiceCentreDetailResponse.fetchServiceCentreDetail.fetchServiceCentreDetailPojo.size() > 0) {
                        mCentreDetailsView.setDetails(fetchServiceCentreDetailResponse.fetchServiceCentreDetail.fetchServiceCentreDetailPojo.get(0));
                    }
                }

            }

            @Override
            public void onFailure(Call<FetchServiceCentreDetailResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void destory() {
        if (mCentreDetailsView != null) {
            mCentreDetailsView = null;
        }
    }
}
