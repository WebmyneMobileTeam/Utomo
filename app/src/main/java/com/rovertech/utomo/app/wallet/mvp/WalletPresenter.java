package com.rovertech.utomo.app.wallet.mvp;

import com.rovertech.utomo.app.wallet.model.WalletPojo;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 03-05-2016.
 */
public interface WalletPresenter {

    void fetchWalletHistory();

    void openWalletHistory(ArrayList<WalletPojo> walletArrayList);
}
