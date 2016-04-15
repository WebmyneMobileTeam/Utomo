package com.rovertech.utomo.app.main.serviceDetail;

import android.content.Context;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingDetailsResponse;

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
                    Functions.showErrorAlert(context, AppConstant.TIMEOUTERRROR);
            }
        });
    }
}
