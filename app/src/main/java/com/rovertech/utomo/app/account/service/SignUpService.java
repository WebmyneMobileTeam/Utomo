package com.rovertech.utomo.app.account.service;

import com.rovertech.utomo.app.account.model.BasicLoginRequest;
import com.rovertech.utomo.app.account.model.ManiBasicLoginSignUp;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public interface SignUpService {

    @POST(AppConstant.BASIC_LOGIN_SIGNUP)
    Call<ManiBasicLoginSignUp> doUserLogin(@Body BasicLoginRequest basicLoginRequest);
}
