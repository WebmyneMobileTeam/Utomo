package com.rovertech.utomo.app.main.dashboard;

import android.content.Context;
import android.widget.Toast;

import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.widget.LocationFinder;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public class DashboardPresenterImpl implements DashboardPresenter {

    DashboardView boardView;

    public DashboardPresenterImpl(DashboardView boardView) {
        this.boardView = boardView;
    }

    @Override
    public void openWallet() {
        if (boardView != null)
            boardView.actionWallet();
    }

    @Override
    public void openSettings() {
        if (boardView != null)
            boardView.actionSettings();
    }

    @Override
    public void openMyBookings() {
        if (boardView != null)
            boardView.actionBookings();
    }

    @Override
    public void openInvite() {
        if (boardView != null)
            boardView.actionInvite();
    }

    @Override
    public void openAboutUs() {
        if (boardView != null)
            boardView.actionAbout();
    }

    @Override
    public void logOut() {
        if (boardView != null)
            boardView.actionLogout();
    }

    @Override
    public void openDashboard() {
        if (boardView != null)
            boardView.actionHome();
    }

    @Override
    public void checkGPS(Context context) {
        LocationFinder finder = new LocationFinder(context);
        if (finder.canGetLocation()) {
            boardView.getLocation(finder);

        } else {
            gpsAlert(context);
        }
    }

    public void gpsAlert(Context context) {
        //Functions.showErrorAlert(context, "GPS Disabled", "Could not able to fetch your location. Please enable your GPS");
        Toast.makeText(context, "Could not able to fetch your location. Please enable your GPS", Toast.LENGTH_SHORT).show();
    }
}
