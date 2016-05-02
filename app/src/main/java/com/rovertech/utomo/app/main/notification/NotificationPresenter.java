package com.rovertech.utomo.app.main.notification;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public interface NotificationPresenter {

    void init();

    void destroy();

    void setUpRecyclerView();

    void CallNotificationApi(int uid,int type);

    void NotiFyAdpter();


}
