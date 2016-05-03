package com.rovertech.utomo.app.bookings;

import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public interface BookingView {

    void setDetails();

    void setDate(String convertedDate);

    void setTime(String strTime);


    void setSelectedCar(CarPojo carPojo);

    void setCarList(ArrayList<CarPojo> carList);
}
