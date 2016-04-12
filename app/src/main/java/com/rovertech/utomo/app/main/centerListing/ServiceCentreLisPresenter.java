package com.rovertech.utomo.app.main.centerListing;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;

/**
 * Created by sagartahelyani on 15-03-2016.
 */
public interface ServiceCentreLisPresenter {

    void fetchCentreList(int lastCentreId, Context context, int type, boolean isBodyShop, boolean isPickup);

    void fetchCity(Context context, String string);

    void setLocation(Double userLatitude, Double userLongitude, int cityID);

    void showListView();

    void showMapView(GoogleMap googleMap, List<ServiceCenterPojo> centerList,Context context);

    void destory();
}
