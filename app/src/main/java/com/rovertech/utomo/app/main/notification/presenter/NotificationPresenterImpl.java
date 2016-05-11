package com.rovertech.utomo.app.main.notification.presenter;

import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.notification.adapter.NotificationAdapter;
import com.rovertech.utomo.app.main.notification.service.NotificationReadAPI;
import com.rovertech.utomo.app.main.notification.service.NotificationRequestAPI;
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

    private final Context c;
    private NotificationView mNotificationView;
    private ArrayList<NotificationItem> notificationItems = new ArrayList<>();
    private NotificationAdapter notificationAdapter;

    public NotificationPresenterImpl(Context c, NotificationView notificationView) {
        this.c = c;
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
        notificationAdapter = new NotificationAdapter(c, mNotificationView, notificationItems);
        mNotificationView.setUpRecyclerView(notificationAdapter);

    }


    @Override
    public void callNotificationApi(int userid, final int type) {
        NotificationRequestAPI api = UtomoApplication.retrofit.create(NotificationRequestAPI.class);
        Call<NotificationResp> call = api.notificationApi(userid);

        call.enqueue(new Callback<NotificationResp>() {
            @Override
            public void onResponse(Call<NotificationResp> call, Response<NotificationResp> response) {

                try {
                    notificationItems = new ArrayList<>();
                    Log.e("onResponse", Functions.jsonString(response.body()));
                    if (response.body().FetchNotification.ResponseCode == 1) {
                        notificationItems = response.body().FetchNotification.Data;
                        notifyAdapter();
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

    @Override
    public void callNotificationReadApi(Context context) {

        NotificationReadAPI api = UtomoApplication.retrofit.create(NotificationReadAPI.class);
        Call<NotificationResp> call = api.notificationReadApi(PrefUtils.getUserID(context));

        call.enqueue(new Callback<NotificationResp>() {
            @Override
            public void onResponse(Call<NotificationResp> call, Response<NotificationResp> response) {
                try {
                    Log.e("onResponse", Functions.jsonString(response.body()));
                    if (response.body().FetchNotification.ResponseCode == 1) {
                        Log.e("read_notification", "read");
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

    public void notifyAdapter() {
        notificationAdapter.clear();
        // ...the data has come back, add new items to your adapter...
        notificationAdapter.addAll(notificationItems);


       /* notificationAdapter.upDateEntries(notificationItems);
        notificationAdapter.notifyDataSetChanged();*/
    }
}
