package com.rovertech.utomo.app.addCar.model;

import java.util.List;

/**
 * Created by sagartahelyani on 25-03-2016.
 */
public class FetchVehicleModel {

    public List<Vehicle> Data;

    public int ResponseCode;

    public String ResponseMessage;

    @Override
    public String toString() {
        return "FetchVehicleModel{" +
                "Data=" + Data +
                ", ResponseCode=" + ResponseCode +
                ", ResponseMessage='" + ResponseMessage + '\'' +
                '}';
    }
}
