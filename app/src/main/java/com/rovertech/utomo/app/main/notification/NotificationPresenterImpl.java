package com.rovertech.utomo.app.main.notification;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class NotificationPresenterImpl implements NotificationPresenter {

    private NotificationView mNotificationView;

    public NotificationPresenterImpl(NotificationView notificationView) {
        this.mNotificationView = notificationView;
    }

    @Override
    public void init() {

        mNotificationView.init();
        mNotificationView.initToolBar();
    }

    @Override
    public void destroy() {


    }

    @Override
    public void setUpRecyclerView() {

        NotificationAdapter notificationAdapter = new NotificationAdapter();
        mNotificationView.setUpRecyclerView(notificationAdapter);

    }
}
