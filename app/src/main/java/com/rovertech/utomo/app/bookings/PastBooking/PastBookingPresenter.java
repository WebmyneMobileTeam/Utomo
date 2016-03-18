package com.rovertech.utomo.app.bookings.PastBooking;

import java.util.List;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public interface PastBookingPresenter {

    void setUpRecyclerView();

    void addMoreData(List<String> strings);


    void destory();

    List<String> getData();

}
