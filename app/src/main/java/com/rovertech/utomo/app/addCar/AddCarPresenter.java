package com.rovertech.utomo.app.addCar;

import android.content.Context;
import android.content.Intent;

import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.io.File;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public interface AddCarPresenter {

    void selectPUCDate(Context context,String selectedDate);

    void selectInsuranceDate(Context context,String selectedDate);

    void fetchMakes(Context context);

    void fetchYears(String selectedMake, Context context);

    void fetchModels(String selectedMake, String selectedYear, Context context);

    void selectServiceDate(Context context,String selectedDate);

    void navigateDashboard(Context context);

    void getImage(Intent data, Context context, int requestCode);

    void addCar(Context context, File file, String vehicleNo, String selectedMake, String selectedYear, String selectModelYear,
                String serviceDate, String pucDate, String insuranceDate, String permitsDate, String odometerValue);
    void updateCar(Context context, File file, String vehicleNo, String selectedMake, String selectedYear, String selectModelYear,
                String serviceDate, String pucDate, String insuranceDate, String permitsDate, String odometerValue);

    void selectPermitsDate(Context context,String selectedDate);

    void setEditCarDetails(CarPojo carPojo, @AddCarActivity.CarMode int carMode);

    void selectImage();
}
