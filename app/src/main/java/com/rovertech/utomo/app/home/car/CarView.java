package com.rovertech.utomo.app.home.car;

import com.rovertech.utomo.app.home.car.model.DashboardData;
import com.rovertech.utomo.app.home.car.model.DashboardResponse;

/**
 * Created by sagartahelyani on 12-04-2016.
 */
public interface CarView {

    void showProgress();

    void hideProgress();

    void setDashboard(DashboardData dashboardResponse);

    void navigateCenterListActivity();
}
