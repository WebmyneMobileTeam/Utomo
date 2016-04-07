package com.rovertech.utomo.app.home.presenter;

import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.widget.LocationFinder;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 30-03-2016.
 */
public interface DashboardView {

    void showProgress();

    void setCarList(ArrayList<CarPojo> data);

    void hideProgress();

    void setErrorMsg(String string);

    void navigateCenterListActivity();

    void getLocation(LocationFinder finder);
}
