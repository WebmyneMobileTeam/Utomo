package com.rovertech.utomo.app.main.notification;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.notification.model.NotificationResp;
import com.rovertech.utomo.app.offers.model.AdminOfferResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by raghavthakkar on 13-04-2016.
 */
public interface NotificationRequestAPI {

    /*String USERID = "UserID";

    @GET(AppConstant.FETCH_NOTIFICATION + "{" + USERID + "}")
    Call<NotificationResp> notificationApi(@Path(USERID) int userID);*/

    @GET(AppConstant.FETCH_NOTIFICATION)
    Call<NotificationResp> notificationApi();

}
