package com.rovertech.utomo.app.main.drawer;

import android.content.Context;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public interface DrawerPresenter {

    void openWallet();

    void openSettings();

    void openMyBookings();

    void openInvite();

    void openAboutUs();

    void logOut();

    void openDashboard();

    void checkGPS(Context context);

    void openCenterListing();

    void rateUs(Context context);
}
