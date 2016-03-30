package com.rovertech.utomo.app.main.centerListing;

import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.CityOutput;
import com.rovertech.utomo.app.account.model.CityRequest;
import com.rovertech.utomo.app.account.service.FetchCityService;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centerListing.model.CentreListRequest;
import com.rovertech.utomo.app.main.centerListing.model.CentreListResponse;
import com.rovertech.utomo.app.main.centerListing.service.FetchServiceCentreListService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 15-03-2016.
 */
public class ServiceCentreListPresenterImpl implements ServiceCentreLisPresenter {

    ServiceCentreView centreView;
    List<ServiceCenterPojo> centerArrayList = new ArrayList<>();
    int lastCentreId;
    private Double userLatitude = 0.0, userLongitude = 0.0;
    private int cityId = 0;

    public ServiceCentreListPresenterImpl(ServiceCentreView centreView) {
        this.centreView = centreView;
    }

    @Override
    public void setLocation(Double userLatitude, Double userLongitude, int cityID) {
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.cityId = cityID;
    }

    @Override
    public void fetchCentreList(int centreId, Context context, int type) {

        // Call WS
        CentreListRequest request = new CentreListRequest();

        if (type == AppConstant.BY_CITY) {
            request.CityId = cityId;

        } else {
            request.CityId = 0;
            request.Lattitude = userLatitude;
            request.Longitude = userLongitude;
        }

        request.LastServiceCentreID = centreId;
        request.IsBodyWash = true;
        request.IsPickupDrop = true;

        Log.e("CentreListRequest", Functions.jsonString(request));

        FetchServiceCentreListService service = UtomoApplication.retrofit.create(FetchServiceCentreListService.class);
        Call<CentreListResponse> call = service.doFetchCentreList(request);

        call.enqueue(new Callback<CentreListResponse>() {
            @Override
            public void onResponse(Call<CentreListResponse> call, Response<CentreListResponse> response) {

                if (response.body() == null) {
                    Log.e("Error", "Error");

                } else {
                    Log.e("response", Functions.jsonString(response.body()));
                    CentreListResponse centreListResponse = response.body();

                    centerArrayList = new ArrayList<ServiceCenterPojo>();

                    if (centreListResponse.FetchServiceCentreList.Data.size() > 0) {
                        centerArrayList.addAll(centreListResponse.FetchServiceCentreList.Data);

                        ServiceCenterPojo center = centreListResponse.FetchServiceCentreList.Data.get(centreListResponse.FetchServiceCentreList.Data.size() - 1);
                        if (center != null)
                            lastCentreId = center.ServiceCentreID;

                        onSubmit(centerArrayList, lastCentreId);

                    } else {
                        onSubmit(new ArrayList<ServiceCenterPojo>(), lastCentreId);
                    }
                }
            }

            @Override
            public void onFailure(Call<CentreListResponse> call, Throwable t) {
                onSubmit(new ArrayList<ServiceCenterPojo>(), 0);
            }
        });

    }

    @Override
    public void fetchCity(final Context context, String string) {
        CityRequest request = new CityRequest();
        request.CityName = string;

        Log.e("city_req", Functions.jsonString(request));

        FetchCityService service = UtomoApplication.retrofit.create(FetchCityService.class);
        Call<CityOutput> call = service.doFetchCity(request);
        call.enqueue(new Callback<CityOutput>() {
            @Override
            public void onResponse(Call<CityOutput> call, Response<CityOutput> response) {
                if (response.body() == null) {
                    Functions.showToast(context, "Error");
                } else {
                    Log.e("json_res", Functions.jsonString(response.body()));
                    CityAdapter adapter = new CityAdapter(context, R.layout.layout_adapter_item, response.body().FetchCity.Data);
                    centreView.setCityAdapter(adapter, response.body().FetchCity.Data);
                }
            }

            @Override
            public void onFailure(Call<CityOutput> call, Throwable t) {

            }
        });
    }

    public void onSubmit(List<ServiceCenterPojo> list, int lastCentreId) {
        centreView.setListing(list, lastCentreId);
    }
}
