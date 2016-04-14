package com.rovertech.utomo.app.addCar.model;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 25-03-2016.
 */
public class AddCarRequest implements Serializable {

    public String InsuranceDate;

    public String LastPermitsDate;

    public String Make;

    public String PUCExpiryDate;

    public String TravelledKM;

    public int ClientID;

    public int VehicleModelYearID;

    public String VehicleNo;

    public int Year;

    public String ServiceDate;

    public AddCarRequest() {
        this.InsuranceDate = "";
        this.Make = "";
        this.PUCExpiryDate = "";
        this.TravelledKM = "";
        this.ClientID = 0;
        this.VehicleModelYearID = 0;
        this.VehicleNo = "";
        this.Year = 0;
        this.ServiceDate = "";
    }
}
