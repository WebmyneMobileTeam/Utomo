package com.rovertech.utomo.app.main.drawer;

import android.content.Context;
import android.view.MenuItem;

import com.rovertech.utomo.app.helper.BadgeHelper;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public interface DrawerPresenter {

    void openWallet();

    void openSettings();

    void openMyBookings();

    void openInvite();

    void openAboutUs();

    void logOut(Context context);

    void openDashboard();

    void checkGPS(Context context);

    void openCenterListing();

    void rateUs(Context context);

    void setNotificationBadge(BadgeHelper badgeHelper, int notificationSize);

    void openNotification(Context context);

    void share(Context context);

    void openOffersPage(Context context);


}
