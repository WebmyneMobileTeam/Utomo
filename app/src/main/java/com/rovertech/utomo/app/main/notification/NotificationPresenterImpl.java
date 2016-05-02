package com.rovertech.utomo.app.main.notification;

import android.content.Context;
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

    private final Context c;
    private NotificationView mNotificationView;

    private ArrayList<NotificationItem> notificationItems ;
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
        mNotificationView.hideProgreessDialog();
    }


    @Override
    public void CallNotificationApi(int userid, final int type) {

        // Log.d("Resp","Inside Call");
        //int userID = PrefUtils.getUserID(this);
        mNotificationView.showProgreessDialog();

        NotificationRequestAPI api = UtomoApplication.retrofit.create(NotificationRequestAPI.class);
        Call<NotificationResp> call = api.notificationApi(userid);

        call.enqueue(new Callback<NotificationResp>() {
            @Override
            public void onResponse(Call<NotificationResp> call, Response<NotificationResp> response) {
                try {
                    notificationItems=new ArrayList<>();
                    if (response.body().FetchNotification.ResponseCode == 1) {
                        //  Log.d("resp",response.body().FetchNotification.toString());
                        notificationItems = response.body().FetchNotification.Data;
                        if (type == 0) {
                            setUpRecyclerView();
                        } else {

                            NotiFyAdpter();
                        }
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
    public void NotiFyAdpter() {
        notificationAdapter.clear();
        // ...the data has come back, add new items to your adapter...
        notificationAdapter.addAll(notificationItems);


       /* notificationAdapter.upDateEntries(notificationItems);
        notificationAdapter.notifyDataSetChanged();*/
    }
}
