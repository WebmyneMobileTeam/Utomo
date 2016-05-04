package com.rovertech.utomo.app.main.serviceDetail;

import android.content.Context;

import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingData;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public interface ServicePresenter {

    void fetchBookingDetails(int bookingId);

    void cancelBooking(int bookingID);

    void rescheduleBooking(boolean b, UserBookingData userBookingData);
}
