package com.rovertech.utomo.app.main.startup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.rovertech.utomo.app.main.centerListing.ServiceCenterListActivity;
import com.rovertech.utomo.app.widget.LocationFinder;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public class StartupPresenterImpl implements StartupPresenter {

    StartupView view;

    public StartupPresenterImpl(StartupView view) {
        this.view = view;
    }

    @Override
    public void skip(Context context) {

        LocationFinder finder = new LocationFinder(context);

        if (!finder.canGetLocation()) {
            gpsAlert(context);

        } else {
            Intent centreIntent = new Intent(context, ServiceCenterListActivity.class);
            Log.e("lat-lng", finder.getLatitude() + "-" + finder.getLongitude());
            centreIntent.putExtra("lat", finder.getLatitude());
            centreIntent.putExtra("lng", finder.getLongitude());
            context.startActivity(centreIntent);
        }
    }

    private void gpsAlert(final Context context) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("GPS Disabled");
        alert.setMessage("We can't able to fetch your location. Please Turn on your GPS from Settings.");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public void login() {
        if (view != null)
            view.normalLogin();
    }

    @Override
    public void signUp() {
        if (view != null)
            view.signUp();
    }

    @Override
    public void loginFb() {

        if (view != null)
            view.fbLogin();
    }

    @Override
    public void loginGplus() {

        if (view != null)
            view.gplusLogin();
    }
}
