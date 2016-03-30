package com.rovertech.utomo.app.addCar.model;

import java.util.List;

/**
 * Created by sagartahelyani on 25-03-2016.
 */
public class FetchMakeModel {

    public List<Data> Data;

    public int ResponseCode;

    public String ResponseMessage;

    @Override
    public String toString() {
        return "FetchMakeModel{" +
                "Data=" + Data +
                ", ResponseCode=" + ResponseCode +
                ", ResponseMessage='" + ResponseMessage + '\'' +
                '}';
    }
}
