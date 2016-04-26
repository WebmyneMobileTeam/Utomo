package com.rovertech.utomo.app.home.car;

import android.content.Context;

import com.rovertech.utomo.app.profile.carlist.CarPojo;

/**
 * Created by sagartahelyani on 12-04-2016.
 */
public interface CarPresenter {

    void fetchDashboard(Context context, CarPojo carPojo, String date, int mode, String odometer, int matricesID);
}
