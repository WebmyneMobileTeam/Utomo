package com.rovertech.utomo.app.profile.personal;

import android.graphics.Bitmap;

import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.City;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sagartahelyani on 31-03-2016.
 */
public interface PersonalProfileView {

    void onUpdateSuccess();

    void setDOB(String convertedDate);

    void setCityAdapter(CityAdapter adapter, ArrayList<City> data);

    void setImage(Bitmap thumbnail, File finalFile);
}
