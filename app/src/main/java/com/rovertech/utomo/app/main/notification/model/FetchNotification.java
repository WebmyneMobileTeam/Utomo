package com.rovertech.utomo.app.main.notification.model;

import java.util.ArrayList;

/**
 * Created by vaibhavirana on 28-04-2016.
 */
public class FetchNotification {
    public int ResponseCode;
    public String ResponseMessage;
    public ArrayList<NotificationItem> Data;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        ResponseMessage = responseMessage;
    }

    public ArrayList<NotificationItem> getData() {
        return Data;
    }

    public void setData(ArrayList<NotificationItem> data) {
        Data = data;
    }
}
