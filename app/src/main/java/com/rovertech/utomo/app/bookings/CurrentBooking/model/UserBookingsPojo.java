package com.rovertech.utomo.app.bookings.CurrentBooking.model;

/**
 * Created by raghavthakkar on 13-04-2016.
 */
public class UserBookingsPojo {

    public String BookingCode;

    public int BookingID;

    public int ClientID;

    public String CreatedDate;

    public String Description;

    public String SCImageName;

    public String SCName;

    public String Status;

    public UserBookingsPojo() {
        BookingCode = "";
        BookingID = 0;
        ClientID = 0;
        CreatedDate = "";
        Description = "";
        this.SCImageName = "";
        this.SCName = "";
        Status = "";
    }
}
