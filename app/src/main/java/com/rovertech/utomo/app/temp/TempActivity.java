package com.rovertech.utomo.app.temp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.home.car.model.DashboardRequest;
import com.rovertech.utomo.app.home.car.model.DashboardResponse;
import com.rovertech.utomo.app.profile.carlist.model.FetchVehicleRequest;
import com.rovertech.utomo.app.profile.carlist.model.VehicleListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);


        TempFetchDashboard tempFetchDashboard = UtomoApplication.retrofit.create(TempFetchDashboard.class);

        String s = String.format("{\"Lattitude\":0.0,\"Longitude\":0.0,\"MatrixID\":0,\"Mode\":0,\"OdometerReading\":0.0,\"ServiceDate\":\"\",\"UserID\":4,\"VehicleID\":20}");
        DashboardRequest dashboardRequest = UtomoApplication.getInstance().getGson().fromJson(s, DashboardRequest.class);
        Observable<DashboardResponse> dashboardResponseObservable = tempFetchDashboard.doFetchDashboard(dashboardRequest);
        dashboardResponseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<DashboardResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DashboardResponse dashboardResponse) {

            }
        });


    }


    interface TempFetchDashboard {
        @POST(AppConstant.FETCH_DASHBOARD)
        Observable<DashboardResponse> doFetchDashboard(@Body DashboardRequest request);
    }

    interface TempFetchVehicleList {

        @POST(AppConstant.FETCH_VEHICLE_LIST)
        Observable<VehicleListResponse> doFetchVehicleList(@Body FetchVehicleRequest request);
    }
}
