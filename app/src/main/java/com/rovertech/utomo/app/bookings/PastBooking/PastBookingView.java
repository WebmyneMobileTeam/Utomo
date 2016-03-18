package com.rovertech.utomo.app.bookings.PastBooking;

import android.view.View;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public interface PastBookingView {

    void initView(View view);
    void setUpRecyclerVIew(PastBookingAdapter pastBookingAdapter);
    void hideFooterView();
    void showFooterView();
}
