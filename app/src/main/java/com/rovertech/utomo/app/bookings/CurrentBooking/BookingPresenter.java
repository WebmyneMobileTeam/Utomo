package com.rovertech.utomo.app.bookings.CurrentBooking;

import android.content.Context;

import com.rovertech.utomo.app.bookings.MyBookingFragment;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public interface BookingPresenter {

    void setUpRecyclerView(Context context,@MyBookingFragment.BookingViewMode int ViewID);
    void destory();
}
