package com.rovertech.utomo.app.main.dashboard;

import android.content.Context;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public interface DashboardPresenter {

    void openWallet();

    void openSettings();

    void openMyBookings();

    void openInvite();

    void openAboutUs();

    void logOut();

    void openDashboard();

    void checkGPS(Context context);

}
