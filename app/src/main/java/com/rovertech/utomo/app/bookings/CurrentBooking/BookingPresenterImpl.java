package com.rovertech.utomo.app.bookings.CurrentBooking;

import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.bookings.CurrentBooking.model.UserBookingsPojo;
import com.rovertech.utomo.app.bookings.CurrentBooking.model.UserBookingsResponse;
import com.rovertech.utomo.app.bookings.CurrentBooking.service.BookingActivitiesAPI;
import com.rovertech.utomo.app.bookings.MyBookingFragment;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.helper.RetrofitErrorHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public class BookingPresenterImpl implements BookingPresenter {


    private BookingView bookingView;

    public BookingPresenterImpl(BookingView bookingView) {
        this.bookingView = bookingView;
    }

    @Override
    public void setUpRecyclerView(final Context context, @MyBookingFragment.BookingViewMode int viewMode) {

        bookingView.showProgressBar();
        int userID = PrefUtils.getUserID(context);
        final List<UserBookingsPojo> userBookingsPojos = new ArrayList<>();
        final CurrentBookingAdapter currentBookingAdapter = new CurrentBookingAdapter(userBookingsPojos, viewMode);
        bookingView.setUpRecyclerVIew(currentBookingAdapter);
        BookingActivitiesAPI bookingActivitiesAPI = UtomoApplication.retrofit.create(BookingActivitiesAPI.class);
        Call<UserBookingsResponse> userBookingsResponseCall = bookingActivitiesAPI.fetchUserBooking(userID, viewMode);
        userBookingsResponseCall.enqueue(new Callback<UserBookingsResponse>() {
            @Override
            public void onResponse(Call<UserBookingsResponse> call, Response<UserBookingsResponse> response) {

                if (response.isSuccess()) {

                    Log.e("response_past", Functions.jsonString(response.body()));

                    UserBookingsResponse userBookingsResponse = response.body();
                    currentBookingAdapter.setUserBookingsPojos(userBookingsResponse.userBookings.Data);
                    bookingView.hideProgressBar();
                }

            }

            @Override
            public void onFailure(Call<UserBookingsResponse> call, Throwable t) {
                bookingView.hideProgressBar();
                RetrofitErrorHelper.showErrorMsg(t, context);
            }
        });


    }

    @Override
    public void destory() {

        if (bookingView != null) {
            bookingView = null;
        }
    }
}
