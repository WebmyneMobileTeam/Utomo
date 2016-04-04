package com.rovertech.utomo.app.home.presenter;

import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.profile.carlist.model.FetchVehicleRequest;
import com.rovertech.utomo.app.profile.carlist.model.VehicleListResponse;
import com.rovertech.utomo.app.profile.carlist.service.FetchVehicleListService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 30-03-2016.
 */
public class DashboardPresenterImpl implements DashboardPresenter {

    private DashboardView view;

    public DashboardPresenterImpl(DashboardView view) {
        this.view = view;
    }

    @Override
    public void fetchMyCars(final Context context) {

        if (view != null)
            view.showProgress();

        FetchVehicleRequest request = new FetchVehicleRequest(context);
        Log.e("req", Functions.jsonString(request));

        FetchVehicleListService service = UtomoApplication.retrofit.create(FetchVehicleListService.class);
        Call<VehicleListResponse> responseCall = service.doFetchVehicleList(request);
        responseCall.enqueue(new Callback<VehicleListResponse>() {
            @Override
            public void onResponse(Call<VehicleListResponse> call, Response<VehicleListResponse> response) {

                if (view != null)
                    view.hideProgress();

                if (response.body() == null) {
                    Functions.showToast(context, "Error occurred.");
                } else {
                    Log.e("res", Functions.jsonString(response.body()));

                    VehicleListResponse vehicleListResponse = response.body();
                    if (vehicleListResponse.FetchVehicleList.ResponseCode == 1) {

                        if (vehicleListResponse.FetchVehicleList.Data.size() == 0)
                            view.setErrorMsg(context.getResources().getString(R.string.no_car));
                        else
                            view.setCarList(vehicleListResponse.FetchVehicleList.Data);

                    } else {
                        view.setErrorMsg(vehicleListResponse.FetchVehicleList.ResponseMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleListResponse> call, Throwable t) {
                if (view != null)
                    view.hideProgress();
                view.setErrorMsg(t.toString());
            }
        });
    }
}
