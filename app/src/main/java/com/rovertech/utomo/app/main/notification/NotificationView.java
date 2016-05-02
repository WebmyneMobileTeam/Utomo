package com.rovertech.utomo.app.main.notification;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public interface NotificationView {

    void init();

    void initToolBar();

    void setUpRecyclerView(NotificationAdapter notificationAdapter);

    void onMethodCallback();

    void showProgreessDialog();

    void hideProgreessDialog();
}
