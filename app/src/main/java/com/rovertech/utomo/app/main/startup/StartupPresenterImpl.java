package com.rovertech.utomo.app.main.startup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.centerListing.ServiceCenterListActivity;
import com.rovertech.utomo.app.widget.GetRxLocation;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public class StartupPresenterImpl implements StartupPresenter {

    StartupView view;
    GetRxLocation location;

    public StartupPresenterImpl(StartupView view) {
        this.view = view;
    }

    @Override
    public void skip(final Context context) {

        location = new GetRxLocation(context);
        location.setOnLocationChangeListener(new GetRxLocation.onLocationChangeListener() {
            @Override
            public void onLocationChange(Location location) {

                if (location == null) {
                   /* Location location1 = PrefUtils.getLastLocation(context);
                    Log.e("last_location", "lat: " + location1.getLatitude() + "- long: " + location1.getLongitude());*/

                    gpsAlert(context);

                } else {

               //     PrefUtils.setLastLocation(context, location);

                    Intent centreIntent = new Intent(context, ServiceCenterListActivity.class);
                    Log.e("lat-lng", location.getLatitude() + "-" + location.getLongitude());
                    centreIntent.putExtra("lat", location.getLatitude());
                    centreIntent.putExtra("lng", location.getLongitude());
                    context.startActivity(centreIntent);
                }
            }
        });

    }

    private void gpsAlert(final Context context) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("GPS Disabled");
        alert.setMessage("We can't able to trace your location. Please turn on your Location Service from Settings.");
        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
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

    @Override
    public void stopRxLocation() {
        if (location != null) {
            location.unSubScribe();
        }
    }
}
