package com.rovertech.utomo.app.bookings.PastBooking;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public class PastBookingPresenterImpl implements PastBookingPresenter {


    private PastBookingView pastBookingView;
    private PastBookingAdapter pastBookingAdapter;
    private boolean isLoadMore = false;

    public PastBookingPresenterImpl(PastBookingView pastBookingView) {
        this.pastBookingView = pastBookingView;
    }

    @Override
    public void setUpRecyclerView() {

        pastBookingAdapter = new PastBookingAdapter();
        pastBookingView.setUpRecyclerVIew(pastBookingAdapter);

    }

    @Override
    public void addMoreData(List<String> strings) {

        if (isLoadMore) {
            return;
        }
        isLoadMore = true;
        pastBookingView.showFooterView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pastBookingAdapter.addMoreData(getData());
                pastBookingView.hideFooterView();
                isLoadMore = false;
            }
        }, 2000);
    }

    @Override
    public void destory() {

        if (pastBookingView != null) {
            pastBookingView = null;
        }
    }

    @Override
    public List<String> getData() {

        List<String> strings = new ArrayList<>();
        int count = 10;
        for (int i = 0; i < count; i++) {

            strings.add(String.valueOf(i));
        }

        return strings;
    }

}
