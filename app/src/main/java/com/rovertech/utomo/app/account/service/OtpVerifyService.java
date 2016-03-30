package com.rovertech.utomo.app.account.service;

import com.rovertech.utomo.app.account.model.ManiBasicLoginSignUp;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public interface OtpVerifyService {

    @GET(AppConstant.OTP_VERIFY)
    Call<ManiBasicLoginSignUp> verifyOTP(@Path("MOBILENUMBER") String number, @Path("OTP") String otp);
}
