package com.rovertech.utomo.app.main.notification.presenter;

/**
 * Created by priyasindkar on 02-05-2016.
 */
public interface NotificationAdapterPresenter {
    void callRescheduleBookingApi(String bookingID, int notificationID, final boolean isAccept);
}
