package com.rovertech.utomo.app.main.centerListing;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagartahelyani on 15-03-2016.
 */
public interface ServiceCentreLisPresenter {

    void fetchCentreList(int lastCentreId, Context context, int type);

    void fetchCity(Context context, String string);

    void setLocation(Double userLatitude, Double userLongitude, int cityID);
}
