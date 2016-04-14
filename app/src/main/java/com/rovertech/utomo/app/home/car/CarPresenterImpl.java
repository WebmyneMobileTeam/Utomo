package com.rovertech.utomo.app.home.car;

import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.home.car.model.DashboardRequest;
import com.rovertech.utomo.app.home.car.model.DashboardResponse;
import com.rovertech.utomo.app.home.car.service.FetchDashboardService;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.widget.LocationFinder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 12-04-2016.
 */
public class CarPresenterImpl implements CarPresenter {

    private CarView carView;

    public CarPresenterImpl(CarView carView) {
        this.carView = carView;
    }

    @Override
    public void fetchDashboard(final Context context, CarPojo carPojo) {
        if (carView != null)
            carView.showProgress();

        DashboardRequest request = new DashboardRequest();
        request.UserID = PrefUtils.getUserID(context);
        request.VehicleID = carPojo.VehicleID;

        LocationFinder locationFinder = new LocationFinder(context);

        if (locationFinder.canGetLocation()) {
            //   getLocation(locationFinder);
            request.Lattitude = locationFinder.getLatitude();
            request.Longitude = locationFinder.getLongitude();
        }

        Log.e("dash_req", Functions.jsonString(request));

        FetchDashboardService service = UtomoApplication.retrofit.create(FetchDashboardService.class);
        Call<DashboardResponse> call = service.doFetchDashboard(request);
        call.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {

                if (carView != null)
                    carView.hideProgress();

                if (response.body() != null) {

                    DashboardResponse dashboardResponse = response.body();
                    Log.e("dash_res", Functions.jsonString(dashboardResponse));
                    if (dashboardResponse.ResponseCode == 1) {
                        carView.setDashboard(dashboardResponse.Data.get(0));
                    } else {
                        Log.e("res", dashboardResponse.ResponseMessage);
                    }
                } else {
                    Log.e("res", "Error has been occurred");
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                if (carView != null)
                    carView.hideProgress();
                Functions.showToast(context, t.toString());
            }
        });

    }

    private void getLocation(LocationFinder locationFinder) {
        Log.e("location", locationFinder.getLatitude() + " : " + locationFinder.getLongitude());
    }
}
