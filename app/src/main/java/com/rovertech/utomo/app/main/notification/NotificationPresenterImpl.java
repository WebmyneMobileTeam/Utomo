package com.rovertech.utomo.app.main.notification;

import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.notification.model.NotificationItem;
import com.rovertech.utomo.app.main.notification.model.NotificationResp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class NotificationPresenterImpl implements NotificationPresenter {

    private NotificationView mNotificationView;
    private ArrayList<NotificationItem> notificationItems = new ArrayList<>();
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

        for(int i=0;i<notificationItems.size();i++)
        {
            Log.d("item",notificationItems.get(i).toString());
        }
        NotificationAdapter notificationAdapter = new NotificationAdapter();
        mNotificationView.setUpRecyclerView(notificationAdapter);

    }

    @Override
    public void CallNotificationApi(int userid) {

        // Log.d("Resp","Inside Call");
        //int userID = PrefUtils.getUserID(this);

        NotificationRequestAPI api = UtomoApplication.retrofit.create(NotificationRequestAPI.class);
        Call<NotificationResp> call = api.notificationApi();

        call.enqueue(new Callback<NotificationResp>() {
            @Override
            public void onResponse(Call<NotificationResp> call, Response<NotificationResp> response) {
                try {


                    if (response.body().FetchNotification.ResponseCode == 1) {
                        Log.d("resp",response.body().FetchNotification.toString());
                        notificationItems=response.body().FetchNotification.Data;
                        setUpRecyclerView();
                    }

                } catch (Exception e) {
                    Log.e("exception", e.toString());
                }
            }

            @Override
            public void onFailure(Call<NotificationResp> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }
}
