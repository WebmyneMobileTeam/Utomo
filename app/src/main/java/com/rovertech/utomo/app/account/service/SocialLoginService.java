package com.rovertech.utomo.app.account.service;

import com.rovertech.utomo.app.account.model.ManiBasicLoginSignUp;
import com.rovertech.utomo.app.account.model.SocialRequest;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public interface SocialLoginService {

    @POST(AppConstant.SOCIAL_LOGIN_SIGNUP)
    Call<ManiBasicLoginSignUp> doSocialLogin(@Body SocialRequest request);
}
