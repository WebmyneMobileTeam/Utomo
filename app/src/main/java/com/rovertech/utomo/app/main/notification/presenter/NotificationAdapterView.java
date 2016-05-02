package com.rovertech.utomo.app.main.notification.presenter;

/**
 * Created by priyasindkar on 02-05-2016.
 */
public interface NotificationAdapterView {
    void showProgressDialog();
    void hideProgressDialog();
    void onAcceptReject(boolean isSuccess, String message);
}
