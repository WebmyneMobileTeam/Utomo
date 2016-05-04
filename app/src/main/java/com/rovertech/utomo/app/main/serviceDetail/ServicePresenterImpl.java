package com.rovertech.utomo.app.main.serviceDetail;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.notification.model.RescheduleBookingRequest;
import com.rovertech.utomo.app.main.notification.model.RescheduleResp;
import com.rovertech.utomo.app.main.notification.service.RescheduleBookingApi;
import com.rovertech.utomo.app.main.serviceDetail.model.CancelBookingOutput;
import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingData;
import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingDetailsResponse;
import com.rovertech.utomo.app.main.serviceDetail.service.BookingDetailService;
import com.rovertech.utomo.app.main.serviceDetail.service.CancelBookingService;

import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public class ServicePresenterImpl implements ServicePresenter {

    private ServiceView serviceView;
    private Context context;
    String message;

    public ServicePresenterImpl(Context context, ServiceView serviceView) {
        this.serviceView = serviceView;
        this.context = context;
    }

    @Override
    public void fetchBookingDetails(int bookingId) {

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
    public void cancelBooking(final int bookingID) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle("Cancel Booking")
                .setMessage("Are you sure want to cancel this booking?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doCancelBooking(bookingID);
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

    @Override
    public void rescheduleBooking(final boolean b, final UserBookingData userBookingData) {
        String title, msg;

        if (b) {
            title = "Accept Reschedule Request";
            msg = "Are you sure want to accept this reschedule booking request?";
        } else {
            title = "Reject Reschedule Request";
            msg = "Are you sure want to reject this reschedule booking request?";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        doReschedule(userBookingData, b);
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

    private void doCancelBooking(int bookingID) {
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
                        ((Activity) context).finish();
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

    private void doReschedule(UserBookingData userBookingData, final boolean isAccept) {

        serviceView.showProgress();

        RescheduleBookingRequest request = new RescheduleBookingRequest(userBookingData.BookingID, userBookingData.NotificationID, isAccept);
        RescheduleBookingApi apiCall = UtomoApplication.retrofit.create(RescheduleBookingApi.class);
        Call<RescheduleResp> call = apiCall.RescheduleBooking(request);

        call.enqueue(new Callback<RescheduleResp>() {
            @Override
            public void onResponse(Call<RescheduleResp> call, Response<RescheduleResp> response) {

                serviceView.hideProgress();

                Log.e("reschedule_res", Functions.jsonString(response.body()));

                if (response.body().RescheduleBookingResponce.ResponseCode == 1) {

                    if (isAccept) {

                        message = "Your Booking Rescheduled Successfully";
                    } else {

                        message = "Your Booking Reschedule Rejected Successfully";
                    }

                } else {

                    message = "Unable To Process Your Request. Please try again later.";
                }
                serviceView.showMessage(message);
            }

            @Override
            public void onFailure(Call<RescheduleResp> call, Throwable t) {
                serviceView.hideProgress();

                message = "Unable To Process Your Request. Please try again later.";
                serviceView.showMessage(message);
            }
        });
    }
}
