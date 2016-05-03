package com.rovertech.utomo.app.wallet.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.wallet.model.WalletResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sagartahelyani on 03-05-2016.
 */
public interface WalletServiceApi {

    @GET(AppConstant.WALLET_HISTORY)
    Call<WalletResponse> fetchWalletHistory(@Path("USERID") int userId);
}
