package com.rovertech.utomo.app.bookings;

import android.content.Context;

import com.rovertech.utomo.app.bookings.model.BookingRequest;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public interface BookingPresenter {

    void fetchDetails();

    void selectTime(Context context);

    void selectDate(Context context);

    void book(Context context, BookingRequest bookingRequest);

    void openCarList(Context context, String dealership);

    void fetchCarList(Context context, String dealership);
}
