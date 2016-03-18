package com.rovertech.utomo.app.bookings;

import android.view.View;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public interface MyBookingView {


    void initView(View view);

    void setUpViewPagerAndTabs(MyBookingAdapter bookingAdapter);


}
