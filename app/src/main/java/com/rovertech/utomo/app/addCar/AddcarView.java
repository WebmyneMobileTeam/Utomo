package com.rovertech.utomo.app.addCar;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import com.rovertech.utomo.app.addCar.adapter.VehicleAdapter;
import com.rovertech.utomo.app.addCar.model.Data;
import com.rovertech.utomo.app.addCar.model.Vehicle;

import java.io.File;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public interface AddcarView {

    void setInsuranceDate(String date);

    void setPUCDate(String convertedDate);

    void setMakeAdapter(ArrayAdapter<String> adapter);

    void showMakeProgress();

    void hideMakeProgress();

    void showYearProgress();

    void hideYearProgress();

    void showModelProgress();

    void hideModelProgress();

    void setYearAdapter(ArrayAdapter<String> adapter);

    void setModelAdapter(VehicleAdapter adapter);

    void setServiceDate(String convertedDate);

    void showProgress();

    void hideProgress();

    void navigateToDashboard();

    void setImage(Bitmap thumbnail, File finalFile);

    void success();

    void fail(String responseMessage);

    void setVehicleError();
}
