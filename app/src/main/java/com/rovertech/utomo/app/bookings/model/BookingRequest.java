package com.rovertech.utomo.app.bookings.model;

/**
 * Created by raghavthakkar on 06-04-2016.
 */
public class BookingRequest {

    public String Description;

    public String DropAddress;

    public String DropArea;

    public String DropCity;

    public String DropZipCode;

    public boolean IsBodyShop;



    public boolean IsService;

    public boolean IsDrop;

    public boolean IsPickup;

    public String PickAddress;

    public String PickArea;

    public String PickCity;

    public String PickZipCode;

    public String PreferredDateTime;

    public int ServiceCentreID;

    public int UserID;

    public int VehicleID;

    public BookingRequest() {
        Description = "";
        DropAddress = "";
        DropArea = "";
        DropCity = "";
        DropZipCode = "";
        IsBodyShop = false;
        IsService = false;
        IsPickup = false;
        IsDrop = false;
        PickAddress = "";
        PickArea = "";
        PickCity = "";
        PickZipCode = "";
        PreferredDateTime = "";
        ServiceCentreID = 0;
        UserID = 0;
        VehicleID = 0;
    }
}
