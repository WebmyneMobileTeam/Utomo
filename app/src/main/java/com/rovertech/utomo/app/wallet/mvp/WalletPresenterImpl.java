package com.rovertech.utomo.app.wallet.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.wallet.WalletHistoryActivity;
import com.rovertech.utomo.app.wallet.model.WalletPojo;
import com.rovertech.utomo.app.wallet.model.WalletResponse;
import com.rovertech.utomo.app.wallet.service.WalletServiceApi;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 03-05-2016.
 */
public class WalletPresenterImpl implements WalletPresenter {

    private WalletView walletView;
    private Context context;

    public WalletPresenterImpl(WalletView walletView, Context context) {
        this.walletView = walletView;
        this.context = context;
    }

    @Override
    public void fetchWalletHistory() {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Loading wallet transactions", "Please Wait..");

        WalletServiceApi serviceApi = UtomoApplication.retrofit.create(WalletServiceApi.class);
        Call<WalletResponse> call = serviceApi.fetchWalletHistory(66);
        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {

                progressDialog.dismiss();

                Log.e("wallet_res", Functions.jsonString(response.body()));

                if (response.body() == null) {
                    Functions.showToast(context, "Error occurred.");

                } else {
                    //  Log.e("wallet_res", Functions.jsonString(response.body()));

                    WalletResponse walletResponse = response.body();

                    if (walletResponse.GetClientWallateHistory.ResponseCode == 1) {
                        ArrayList<WalletPojo> walletList = new ArrayList<>();
                        walletList.addAll(walletResponse.GetClientWallateHistory.Data);

                        PrefUtils.setWallet(context, walletResponse.GetClientWallateHistory);

                        walletView.setHistory(walletList);

                    } else {
                        Functions.showToast(context, walletResponse.GetClientWallateHistory.ResponseMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                progressDialog.dismiss();
                if (t.getCause() instanceof TimeoutException) {
                    Functions.showToast(context, AppConstant.TIMEOUTERRROR);

                } else {
                    Functions.showToast(context, t.getMessage());
                }
            }

        });
    }

    @Override
    public void openWalletHistory(ArrayList<WalletPojo> walletArrayList) {
        Intent walletIntent = new Intent(context, WalletHistoryActivity.class);
        walletIntent.putExtra("wallet", walletArrayList);
        context.startActivity(walletIntent);
    }
}
