package com.rovertech.utomo.app.main.notification.model;

/**
 * Created by vaibhavirana on 28-04-2016.
 */
public class NotificationItem {

    public String BookingID;
    public String Description,NotificationDate;
    public int NotificationID;
    public String PreferedDateTime;
    public String ReferenceCode;
    public int ReferenceID;
    public String RescheduleDatetime;
    public String ServicecentreName;
    public boolean Type;

    public String getDescription() {
        return Description;
    }

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getNotificationID() {
        return NotificationID;
    }

    public void setNotificationID(int notificationID) {
        NotificationID = notificationID;
    }

    public String getPreferedDateTime() {
        return PreferedDateTime;
    }

    public void setPreferedDateTime(String preferedDateTime) {
        PreferedDateTime = preferedDateTime;
    }

    public String getReferenceCode() {
        return ReferenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        ReferenceCode = referenceCode;
    }

    public int getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(int referenceID) {
        ReferenceID = referenceID;
    }

    public String getRescheduleDatetime() {
        return RescheduleDatetime;
    }

    public void setRescheduleDatetime(String rescheduleDatetime) {
        RescheduleDatetime = rescheduleDatetime;
    }

    public String getServicecentreName() {
        return ServicecentreName;
    }

    public void setServicecentreName(String servicecentreName) {
        ServicecentreName = servicecentreName;
    }

    public boolean isType() {
        return Type;
    }

    public void setType(boolean type) {
        Type = type;
    }
}
