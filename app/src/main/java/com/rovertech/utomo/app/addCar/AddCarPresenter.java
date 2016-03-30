package com.rovertech.utomo.app.addCar;

import android.content.Context;
import android.content.Intent;

import java.io.File;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public interface AddCarPresenter {

    void selectPUCDate(Context context);

    void selectInsuranceDate(Context context);

    void fetchMakes(Context context);

    void fetchYears(String selectedMake, Context context);

    void fetchModels(String selectedMake, String selectedYear, Context context);

    void selectServiceDate(Context context);

    void navigateDashboard(Context context);

    void getImage(Intent data, Context context, int requestCode);

    void addCar(Context context, File file, String vehicleNo, String selectedMake, String selectedYear, String selectModelYear,
                String serviceDate, String pucDate, String insuranceDate, String odometerValue);
}
