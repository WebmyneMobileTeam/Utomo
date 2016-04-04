package com.rovertech.utomo.app.profile.carlist;

import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.profile.carlist.model.FetchVehicleRequest;
import com.rovertech.utomo.app.profile.carlist.model.VehicleListResponse;
import com.rovertech.utomo.app.profile.carlist.service.FetchVehicleListService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public class CarFragmentPresenterImpl implements CarFragmentPresenter {

    CarFragmentView carFragmentView;

    public CarFragmentPresenterImpl(CarFragmentView carFragmentView) {
        this.carFragmentView = carFragmentView;

    }

    @Override
    public void fetchMyCars(final Context context) {

        if (carFragmentView != null)
            carFragmentView.showProgress();

        FetchVehicleRequest request = new FetchVehicleRequest(context);
        Log.e("req", Functions.jsonString(request));

        FetchVehicleListService service = UtomoApplication.retrofit.create(FetchVehicleListService.class);
        Call<VehicleListResponse> responseCall = service.doFetchVehicleList(request);
        responseCall.enqueue(new Callback<VehicleListResponse>() {
            @Override
            public void onResponse(Call<VehicleListResponse> call, Response<VehicleListResponse> response) {
                if (carFragmentView != null)
                    carFragmentView.hideProgress();

                if (response.body() == null) {
                    Functions.showToast(context, "Error occurred.");
                } else {
                    Log.e("res", Functions.jsonString(response.body()));

                    VehicleListResponse vehicleListResponse = response.body();
                    if (vehicleListResponse.FetchVehicleList.ResponseCode == 1) {
                        carFragmentView.setCarList(vehicleListResponse.FetchVehicleList.Data);

                    } else {
                        carFragmentView.setEmptyView();
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleListResponse> call, Throwable t) {
                if (carFragmentView != null)
                    carFragmentView.hideProgress();
            }
        });
    }
}
