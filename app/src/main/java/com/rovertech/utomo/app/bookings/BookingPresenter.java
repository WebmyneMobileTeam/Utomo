package com.rovertech.utomo.app.bookings;

import android.content.Context;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public interface BookingPresenter {

    void fetchDetails();

    void selectTime(Context context);

    void selectDate(Context context);

    void book(Context context);
}
