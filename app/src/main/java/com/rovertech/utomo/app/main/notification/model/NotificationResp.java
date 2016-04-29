package com.rovertech.utomo.app.main.notification.model;

/**
 * Created by vaibhavirana on 25-04-2016.
 */
public class NotificationResp {

    public FetchNotification FetchNotification;

    public com.rovertech.utomo.app.main.notification.model.FetchNotification getFetchNotification() {
        return FetchNotification;
    }

    public void setFetchNotification(com.rovertech.utomo.app.main.notification.model.FetchNotification fetchNotification) {
        FetchNotification = fetchNotification;
    }
}
