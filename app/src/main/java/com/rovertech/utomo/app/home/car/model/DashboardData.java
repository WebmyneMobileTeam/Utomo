package com.rovertech.utomo.app.home.car.model;

import com.rovertech.utomo.app.main.centerListing.ServiceCenterPojo;
import com.rovertech.utomo.app.tiles.sponsoredCenter.SponsoredCenterSet;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sagartahelyani on 12-04-2016.
 */
public class DashboardData implements Serializable {

    public String BookingCode;

    public int BookingID;

    public int BookingStatusID;

    public String CarHealth;

    public String CreatedDate;

    public String Description;

    public boolean IsCurrentBooking;

    public String LastServiceDate;

    public String OdometerReading;

    public int ReviewCount;

    public String SCImageName;

    public String ServiceCompletedDateTime;

    public int ServiceCentreID;

    public String ServiceCentreName;

    public String Status;

    public int StatusDecision;

    public ArrayList<Performance> lstPerformance;

    public ArrayList<ServiceCenterPojo> lstReferTile;

}
