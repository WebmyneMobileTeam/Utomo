package com.rovertech.utomo.app.profile.carlist.model;

import android.content.Context;

import com.rovertech.utomo.app.helper.PrefUtils;

/**
 * Created by sagartahelyani on 30-03-2016.
 */
public class FetchVehicleRequest {

    public int UserID;

    public String VehicleID;

    public FetchVehicleRequest(Context context) {
        UserID = PrefUtils.getUserID(context);
        VehicleID = "";
    }
}
