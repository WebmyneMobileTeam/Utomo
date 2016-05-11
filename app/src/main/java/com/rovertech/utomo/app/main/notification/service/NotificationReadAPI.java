package com.rovertech.utomo.app.main.notification.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.notification.model.NotificationResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by raghavthakkar on 13-04-2016.
 */
public interface NotificationReadAPI {

    String USERID = "UserID";

    @GET(AppConstant.READ_NOTIFICATION + "{" + USERID + "}")
    Call<NotificationResp> notificationReadApi(@Path(USERID) int userID);

}
