package com.rovertech.utomo.app.main.centerListing;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 15-03-2016.
 */
public class ServiceCenterPojo implements Serializable {

    public String ContactName;

    public String ContactPhoneNo1;

    public String ContactPhoneNo2;

    public String DistanceKM;

    public boolean IsBodyWash;

    public boolean IsOfferAvail;

    public boolean IsPickupDrop;

    public float Lattitude;

    public float Longitude;

    public double Rating;

    public int ReviewCounter;

    public int ServiceCentreID;

    public String ServiceCentreImage;

    public String ServiceCentreName;

    public ServiceCenterPojo() {
        this.ContactName = "";
        this.ContactPhoneNo1 = "";
        this.ContactPhoneNo2 = "";
        this.IsBodyWash = false;
        this.IsOfferAvail = false;
        this.IsPickupDrop = false;
        this.Rating = 0.0;
        this.ReviewCounter = 0;
        this.ServiceCentreID = 0;
        this.ServiceCentreImage = "";
        this.ServiceCentreName = "";
    }
}
