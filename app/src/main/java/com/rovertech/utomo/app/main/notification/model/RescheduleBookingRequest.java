package com.rovertech.utomo.app.main.notification.model;

/**
 * Created by vaibhavirana on 29-04-2016.
 */
public class RescheduleBookingRequest {


    public int BookingID,NotificationID;
    public boolean Response;

    public RescheduleBookingRequest(int BookingID, int NotificationID, boolean response)
    {
        this.BookingID=BookingID;
        this.NotificationID=NotificationID;
        Response=response;
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        BookingID = bookingID;
    }

    public boolean isResponse() {
        return Response;
    }

    public void setResponse(boolean response) {
        Response = response;
    }

    public int getNotificationID() {
        return NotificationID;
    }

    public void setNotificationID(int notificationID) {
        NotificationID = notificationID;
    }
}
