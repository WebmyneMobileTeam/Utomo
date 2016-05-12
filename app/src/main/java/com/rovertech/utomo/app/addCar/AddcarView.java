package com.rovertech.utomo.app.addCar;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import com.rovertech.utomo.app.addCar.adapter.CustomSpinnerAdapter;
import com.rovertech.utomo.app.addCar.adapter.VehicleAdapter;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.io.File;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public interface AddcarView {

    void setInsuranceDate(String date);

    void setPUCDate(String convertedDate);

    void setMakeAdapter(CustomSpinnerAdapter adapter);

    void setYearAdapter(CustomSpinnerAdapter adapter);

    void setModelAdapter(VehicleAdapter adapter);

    void setServiceDate(String convertedDate);

    void showProgress();

    void hideProgress();

    void navigateToDashboard();

    void setImage(Bitmap thumbnail, File finalFile);

    void success();

    void fail(String responseMessage);

    void setVehicleError();

    void setPermitsDate(String date);

    void getDataFromIntent();

    void setCarDetails(CarPojo carPojo);

    void setMakeSpinner(CarPojo carPojo);

    void setYearSpinner(CarPojo carPojo);

    void setModelSpinner(CarPojo carPojo);

    void setRxImage(File file);
}
