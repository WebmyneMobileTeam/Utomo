package com.rovertech.utomo.app.bookings.CurrentBooking;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public class CurrentBookingPresenterImpl implements CurrentBookingPresenter {


    private CurrentBookingView currentBookingView;

    public CurrentBookingPresenterImpl(CurrentBookingView currentBookingView) {
        this.currentBookingView = currentBookingView;
    }

    @Override
    public void setUpRecyclerView() {

        CurrentBookingAdapter currentBookingAdapter = new CurrentBookingAdapter();
        currentBookingView.setUpRecyclerVIew(currentBookingAdapter);

    }

    @Override
    public void destory() {

        if (currentBookingView != null) {
            currentBookingView = null;
        }
    }
}
