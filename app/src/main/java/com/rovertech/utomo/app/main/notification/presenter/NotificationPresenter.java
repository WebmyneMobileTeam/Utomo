package com.rovertech.utomo.app.main.notification.presenter;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public interface NotificationPresenter {

    void init();

    void destroy();

    void setUpRecyclerView();

    void callNotificationApi(int uid, int type);
}
