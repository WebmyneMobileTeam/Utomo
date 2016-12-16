package com.rovertech.utomo.app.main.notification.presenter;

import com.rovertech.utomo.app.main.notification.adapter.NotificationAdapter;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public interface NotificationView {
    void init();
    void initToolBar();
    void setUpRecyclerView(NotificationAdapter notificationAdapter);
    void onAcceptRejectCallback(String message);
    void showProgress();
    void hideProgress();
}
