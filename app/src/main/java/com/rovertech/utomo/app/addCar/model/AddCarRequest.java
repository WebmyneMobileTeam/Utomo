package com.rovertech.utomo.app.addCar.model;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 25-03-2016.
 */
public class AddCarRequest implements Serializable {

    public String InsuranceDate;

    public String Make;

    public String PUCExpiryDate;

    public int TravelledKM;

    public int UserID;

    public int VehicleModelYearID;

    public String VehicleNo;

    public String Year;

    public AddCarRequest() {
        this.InsuranceDate = "";
        this.Make = "";
        this.PUCExpiryDate = "";
        this.TravelledKM = 0;
        this.UserID = 0;
        this.VehicleModelYearID = 0;
        this.VehicleNo = "";
        this.Year = "";
    }
}
