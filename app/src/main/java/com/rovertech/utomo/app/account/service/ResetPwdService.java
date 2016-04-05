package com.rovertech.utomo.app.account.service;

import com.rovertech.utomo.app.account.model.ResendOutput;
import com.rovertech.utomo.app.account.model.ResetPassword;
import com.rovertech.utomo.app.account.model.ResetPasswordOutput;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public interface ResetPwdService {

    @GET(AppConstant.RESET_PASSWORD)
    Call<ResetPasswordOutput> resetPassword(@Path("MOBILENO") String number, @Path("NEWPASSWORD") String password);
}
