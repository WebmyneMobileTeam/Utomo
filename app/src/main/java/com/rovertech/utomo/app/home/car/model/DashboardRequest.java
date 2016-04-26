package com.rovertech.utomo.app.home.car.model;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 12-04-2016.
 */
public class DashboardRequest implements Serializable {

    public int MatrixID;

    public int Mode;

    public float OdometerReading;

    public String ServiceDate;

    public int UserID;

    public int VehicleID;

    public double Lattitude;

    public double Longitude;

    public String GeneralTypeDate;
}
