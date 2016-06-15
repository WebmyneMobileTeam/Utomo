package com.rovertech.utomo.app.main.centreDetail.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by raghavthakkar on 30-03-2016.
 */
public class FetchServiceCentreDetailPojo implements Serializable {

    public String Address1;

    public String CityName;

    public String ContactEmail;

    public String ContactName;

    public String ContactPhoneNo1;

    public String ContactPhoneNo2;

    public String CountryName;

    public String Dealership;

    public String Expertise;

    public boolean IsOfferAvail;

    public boolean IsBodyWash;

    public boolean IsPickupDrop;

    public float Lattitude;

    public float Longitude;

    public float Rating;

    public int ServiceCentreID;

    public String ServiceCentreName;

    public String StateName;

    public String Website;

    public String Zipcode;

    public List<FeedBack> lstFeedBack;

    public List<ServiceCentreImage> lstServiceCentreImage;

}
