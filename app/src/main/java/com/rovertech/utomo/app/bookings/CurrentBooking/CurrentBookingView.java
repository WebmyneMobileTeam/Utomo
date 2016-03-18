package com.rovertech.utomo.app.bookings.CurrentBooking;

import android.view.View;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public interface CurrentBookingView {

    void initView(View view);
    void setUpRecyclerVIew(CurrentBookingAdapter currentBookingAdapter);
}
