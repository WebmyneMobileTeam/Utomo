package com.rovertech.utomo.app.bookings.CurrentBooking;

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

    public boolean IsPickupDrop;

    public boolean IsService;

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
        IsPickupDrop = false;
        IsService = false;
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
