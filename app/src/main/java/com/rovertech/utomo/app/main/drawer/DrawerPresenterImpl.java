package com.rovertech.utomo.app.main.drawer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.model.UserProfileOutput;
import com.rovertech.utomo.app.helper.BadgeHelper;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.notification.NotificationActivity;
import com.rovertech.utomo.app.main.startup.StartupActivity;
import com.rovertech.utomo.app.widget.LocationFinder;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public class DrawerPresenterImpl implements DrawerPresenter {

    DrawerView boardView;

    public DrawerPresenterImpl(DrawerView boardView) {
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
    public void logOut(Context context) {
        UserProfileOutput profileOutput = new UserProfileOutput();
        PrefUtils.setLoggedIn(context, false);
        PrefUtils.setUserFullProfileDetails(context, profileOutput);

        Intent startupIntent = new Intent(context, StartupActivity.class);
        startupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startupIntent);
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

    @Override
    public void openCenterListing() {
        boardView.navigateCenterListActivity();
    }

    @Override
    public void rateUs(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    @Override
    public void setNotificationBadge(BadgeHelper badgeHelper) {
        // Call WS for getting notification
        badgeHelper.displayBadge(6);
    }

    @Override
    public void openNotification(Context context) {
        Functions.fireIntent(context, NotificationActivity.class);
        ((Activity) context).overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    @Override
    public void share(Context context) {
        String text = context.getResources().getString(R.string.share) + "\n";
        String appURL = "Download App on Play Store\n" + "http://play.google.com/store/apps/details?id=" + context.getPackageName();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text + appURL);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, "Share via..."));
    }


    public void gpsAlert(Context context) {
        //Functions.showErrorAlert(context, "GPS Disabled", "Could not able to fetch your location. Please enable your GPS");
        Toast.makeText(context, "Could not able to fetch your location. Please enable your GPS", Toast.LENGTH_SHORT).show();
    }
}
