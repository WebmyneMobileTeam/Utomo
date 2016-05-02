package com.rovertech.utomo.app.main.notification.presenter;

import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.notification.service.RescheduleBookingApi;
import com.rovertech.utomo.app.main.notification.model.RescheduleBookingRequest;
import com.rovertech.utomo.app.main.notification.model.RescheduleResp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyasindkar on 02-05-2016.
 */
public class NotificationAdapterPresenterImpl implements NotificationAdapterPresenter {
    private Context mContext;
    private NotificationAdapterView view;
    private String message = "";

    public NotificationAdapterPresenterImpl(Context mContext, NotificationAdapterView view) {
        this.mContext = mContext;
        this.view = view;
    }

    @Override
    public void callRescheduleBookingApi(String bookingID, int notificationID, final boolean isAccept) {
        try {
            view.showProgressDialog();
            final RescheduleBookingRequest request = new RescheduleBookingRequest(Integer.parseInt(bookingID), notificationID, isAccept);
            RescheduleBookingApi apiCall = UtomoApplication.retrofit.create(RescheduleBookingApi.class);
            Call<RescheduleResp> call = apiCall.RescheduleBooking(request);
            call.enqueue(new Callback<RescheduleResp>() {
                @Override
                public void onResponse(Call<RescheduleResp> call, Response<RescheduleResp> response) {
                    view.hideProgressDialog();
                    Log.e("callReschleBookingApi", Functions.jsonString(response.body()));
                    if (response.body().RescheduleBookingResponce.ResponseCode == 1) {
                        if (isAccept) {
                            message = "Your Booking Rescheduled Successfully";
                        } else {
                            message = "Your Booking Reschedule Rejected Successfully";
                        }
                    } else {
                        message = "Unable To Process Your Request. Please try again later.";
                    }
                    view.onAcceptReject(true, message);
                }

                @Override
                public void onFailure(Call<RescheduleResp> call, Throwable t) {
                    view.hideProgressDialog();
                    message = "Unable To Process Your Request. Please try again later.";
                    view.onAcceptReject(true, message);
                }
            });
        } catch (Exception e) {
            Log.e("EXP", e.toString());
        }
    }
}
