package com.rovertech.utomo.app.gcm.model;

/**
 * Created by vatsaldesai on 13-12-2016.
 */
public class NotificationModel {

    /**
     * VehicalId : 100
     * NotificationId : 42730
     * Message : BookingID : B000492 cancelled.
     * NotificationTypeId : 2
     * UserId : 61
     * FirstName : null
     * NotificationFor : Service
     * LastName : null
     */

    private int VehicalId;
    private int NotificationId;
    private String Message;
    private int NotificationTypeId;
    private int UserId;
    private String FirstName;
    private String NotificationFor;
    private String LastName;

    public int getVehicalId() {
        return VehicalId;
    }

    public void setVehicalId(int VehicalId) {
        this.VehicalId = VehicalId;
    }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int NotificationId) {
        this.NotificationId = NotificationId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getNotificationTypeId() {
        return NotificationTypeId;
    }

    public void setNotificationTypeId(int NotificationTypeId) {
        this.NotificationTypeId = NotificationTypeId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getNotificationFor() {
        return NotificationFor;
    }

    public void setNotificationFor(String NotificationFor) {
        this.NotificationFor = NotificationFor;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }
}
