package com.rovertech.utomo.app.main.drawer;

import com.rovertech.utomo.app.widget.LocationFinder;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public interface DrawerView {

    void actionHome();

    void actionWallet();

    void actionSettings();

    void actionBookings();

    void actionInvite();

    void actionAbout();

    void actionLogout();

    void getLocation(LocationFinder finder);

    void navigateCenterListActivity();

}
