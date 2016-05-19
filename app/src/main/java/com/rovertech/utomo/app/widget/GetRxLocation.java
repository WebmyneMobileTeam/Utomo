package com.rovertech.utomo.app.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by sagartahelyani on 18-05-2016.
 */
public class GetRxLocation {

    private Context context;

    private final String TAG = getClass().getSimpleName();
    private static final int REQUEST_CHECK_SETTINGS = 0, REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 101;
    private List<String> permissions = new ArrayList<>();
    private ReactiveLocationProvider locationProvider;
    private Observable<Location> lastKnownLocationObservable;
    private Observable<Location> locationUpdatesObservable;

    private Subscription lastKnownLocationSubscription;
    private Subscription updatableLocationSubscription;

    private onLocationChangeListener onLocationChangeListener;

    public void setOnLocationChangeListener(GetRxLocation.onLocationChangeListener onLocationChangeListener) {
        this.onLocationChangeListener = onLocationChangeListener;
    }

    public GetRxLocation(Context context) {
        this.context = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                ((Activity) context).requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
            } else {
                observableForLocation();
                subscribeForGetAndUpdateLocation();
            }
        } else {
            observableForLocation();
            subscribeForGetAndUpdateLocation();
        }
    }

    private void subscribeForGetAndUpdateLocation() {
        lastKnownLocationSubscription = lastKnownLocationObservable
                .map(new LocationToStringFunc())
                .subscribe(new DisplayTextOnViewAction(), new ErrorHandler());

        updatableLocationSubscription = locationUpdatesObservable
                .map(new LocationToStringFunc())
                .map(new Func1<String, String>() {
                    int count = 0;

                    @Override
                    public String call(String s) {
                        return s /*+ " " + count++*/;
                    }
                })
                .subscribe(new DisplayTextOnViewAction(), new ErrorHandler());
    }

    public class DisplayTextOnViewAction implements Action1<String> {

        public DisplayTextOnViewAction() {

        }

        @Override
        public void call(String s) {
            Log.e("location", s);
//            target.setText(s);
        }
    }

    private void observableForLocation() {
        locationProvider = new ReactiveLocationProvider(context);
        lastKnownLocationObservable = locationProvider.getLastKnownLocation();

        final LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(100);

        locationUpdatesObservable = locationProvider
                .checkLocationSettings(
                        new LocationSettingsRequest.Builder()
                                .addLocationRequest(locationRequest)
                                .setAlwaysShow(true)  //Reference: http://stackoverflow.com/questions/29824408/google-play-services-locationservices-api-new-option-never
                                .build()
                )
                .doOnNext(new Action1<LocationSettingsResult>() {
                    @Override
                    public void call(LocationSettingsResult locationSettingsResult) {
                        Status status = locationSettingsResult.getStatus();
                        if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                            try {
                                status.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException th) {
                                Log.e("MainActivity", "Error opening settings activity.", th);
                            }
                        }
                    }
                })
                .flatMap(new Func1<LocationSettingsResult, Observable<Location>>() {
                    @Override
                    public Observable<Location> call(LocationSettingsResult locationSettingsResult) {
                        return locationProvider.getUpdatedLocation(locationRequest);
                    }
                });

    }

    public class LocationToStringFunc implements Func1<Location, String> {
        @Override
        public String call(Location location) {
            if (location != null) {
                if (onLocationChangeListener != null) {
                    onLocationChangeListener.onLocationChange(location);
                }
                unSubScribe();
                return location.getLatitude() + " " + location.getLongitude();
            } else {
                return "no location available";
            }
        }
    }

    private class ErrorHandler implements Action1<Throwable> {
        @Override
        public void call(Throwable throwable) {
            Toast.makeText(context, "Error occurred. " + throwable.toString(), Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error occurred. " + throwable.toString(), throwable);
        }
    }

    public interface onLocationChangeListener {
        void onLocationChange(Location location);
    }

    public void unSubScribe() {
        if (lastKnownLocationObservable != null)
            lastKnownLocationSubscription.unsubscribe();
        if (updatableLocationSubscription != null)
            updatableLocationSubscription.unsubscribe();
    }
}
