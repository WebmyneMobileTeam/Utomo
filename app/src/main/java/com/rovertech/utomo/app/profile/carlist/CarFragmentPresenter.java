package com.rovertech.utomo.app.profile.carlist;

import android.content.Context;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public interface CarFragmentPresenter {

    void fetchMyCars(Context context);

    void deleteVehicle(int vehicleId);
}
