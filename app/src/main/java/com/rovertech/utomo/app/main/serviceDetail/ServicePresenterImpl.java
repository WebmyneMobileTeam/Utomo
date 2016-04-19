package com.rovertech.utomo.app.main.serviceDetail;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.serviceDetail.model.CancelBookingOutput;
import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingDetailsResponse;
import com.rovertech.utomo.app.main.serviceDetail.service.BookingDetailService;
import com.rovertech.utomo.app.main.serviceDetail.service.CancelBookingService;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public class ServicePresenterImpl implements ServicePresenter {

    ServiceView serviceView;

    public ServicePresenterImpl(ServiceView serviceView) {
        this.serviceView = serviceView;
    }

    @Override
    public void fetchBookingDetails(final Context context, int bookingId) {

        if (serviceView != null)
            serviceView.showProgress();

        BookingDetailService service = UtomoApplication.retrofit.create(BookingDetailService.class);
        Call<UserBookingDetailsResponse> call = service.fetchDetails(bookingId);
        call.enqueue(new Callback<UserBookingDetailsResponse>() {
            @Override
            public void onResponse(Call<UserBookingDetailsResponse> call, Response<UserBookingDetailsResponse> response) {
                if (serviceView != null)
                    serviceView.hideProgress();

                if (response.body() != null) {
                    UserBookingDetailsResponse bookingDetailsResponse = response.body();

                    if (bookingDetailsResponse.UserBookingDetail.ResponseCode == 1) {

                        serviceView.setBookingDetails(bookingDetailsResponse.UserBookingDetail.Data.get(0));

                    } else {

                        Functions.showToast(context, bookingDetailsResponse.UserBookingDetail.ResponseMessage);
                    }

                } else {
                    Functions.showToast(context, "Error occurred");
                }
            }

            @Override
            public void onFailure(Call<UserBookingDetailsResponse> call, Throwable t) {
                if (serviceView != null)
                    serviceView.hideProgress();
                if (t.getCause() instanceof TimeoutException)
                    Functions.showErrorAlert(context, AppConstant.TIMEOUTERRROR, false);
            }
        });
    }

    @Override
    public void cancelBooking(final Context context, final int bookingID) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle("Cancel Booking")
                .setMessage("Are you sure want to cancel this booking?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doCancelBooking(context, bookingID);
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

    private void doCancelBooking(final Context context, int bookingID) {
        CancelBookingService service = UtomoApplication.retrofit.create(CancelBookingService.class);
        Call<CancelBookingOutput> call = service.doCancel(bookingID);
        call.enqueue(new Callback<CancelBookingOutput>() {
            @Override
            public void onResponse(Call<CancelBookingOutput> call, Response<CancelBookingOutput> response) {

                if (response.body() == null) {
                    Functions.showToast(context, "Error occurred");
                } else {
                    CancelBookingOutput output = response.body();
                    if (output.CancleBooking.ResponseCode == 1) {
                        Functions.showToast(context, "Booking cancel successfully.");
                    } else {
                        Functions.showToast(context, output.CancleBooking.ResponseMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<CancelBookingOutput> call, Throwable t) {
                if (t.getCause() instanceof TimeoutException) {
                    Functions.showToast(context, AppConstant.TIMEOUTERRROR);
                } else if (t.getCause() instanceof UnknownHostException) {
                    Functions.showErrorAlert(context, AppConstant.NO_INTERNET_CONNECTION, false);
                } else {
                    Functions.showToast(context, t.toString());
                }
            }
        });
    }
}
