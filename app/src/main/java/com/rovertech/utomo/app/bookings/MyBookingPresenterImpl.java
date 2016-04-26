package com.rovertech.utomo.app.bookings;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.rovertech.utomo.app.bookings.CurrentBooking.BookingFragment;
import com.rovertech.utomo.app.bookings.PastBooking.PastBookingFragment;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public class MyBookingPresenterImpl implements MyBookingPresenter {


    private MyBookingView myBookingFragment;
    private FragmentManager fragmentManager;
    private Context context;

    public MyBookingPresenterImpl(FragmentManager fragmentManager, MyBookingView myBookingFragment, Context context) {
        this.myBookingFragment = myBookingFragment;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @Override
    public void setUpViewPagerAndTabs() {
        MyBookingAdapter myBookingAdapter = new MyBookingAdapter(fragmentManager, context);
        myBookingAdapter.add(BookingFragment.newInstance(), "Current Booking");
        myBookingAdapter.add(PastBookingFragment.newInstance(), "Past Booking");
        myBookingFragment.setUpViewPagerAndTabs(myBookingAdapter);
    }

    @Override
    public void destroy() {

        if (myBookingFragment != null) {
            myBookingFragment = null;
        }


    }
}
