package com.rovertech.utomo.app.profile.carlist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.profile.carlist.model.FetchVehicleRequest;
import com.rovertech.utomo.app.profile.carlist.model.VehicleListResponse;
import com.rovertech.utomo.app.profile.carlist.service.FetchVehicleListService;
import com.rovertech.utomo.app.tiles.carItem.model.DeleteVehicleResponse;
import com.rovertech.utomo.app.tiles.carItem.service.DeleteCarService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public class CarFragmentPresenterImpl implements CarFragmentPresenter {

    CarFragmentView carFragmentView;
    Context context;

    public CarFragmentPresenterImpl(CarFragmentView carFragmentView, Context context) {
        this.carFragmentView = carFragmentView;
        this.context = context;

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

    @Override
    public void deleteVehicle(final String vehicleId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle("Delete Car")
                .setMessage("Are you sure want to delete this car?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doDeleteCar(vehicleId);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void doDeleteCar(String vehicleId) {
        
        DeleteCarService service = UtomoApplication.retrofit.create(DeleteCarService.class);
        Call<DeleteVehicleResponse> call = service.deletCar(PrefUtils.getUserID(context), vehicleId);

        call.enqueue(new Callback<DeleteVehicleResponse>() {
            @Override
            public void onResponse(Call<DeleteVehicleResponse> call, Response<DeleteVehicleResponse> response) {
                if (response.body() != null) {
                    DeleteVehicleResponse deleteResponse = response.body();
                    if (deleteResponse.DeleteVehicleDetails.ResponseCode == 1) {
                        Functions.showToast(context, "Delete car successfully");
                        fetchMyCars(context);

                    } else {
                        Functions.showToast(context, deleteResponse.DeleteVehicleDetails.ResponseMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteVehicleResponse> call, Throwable t) {
                Functions.showToast(context, t.toString());
            }
        });
    }
}
