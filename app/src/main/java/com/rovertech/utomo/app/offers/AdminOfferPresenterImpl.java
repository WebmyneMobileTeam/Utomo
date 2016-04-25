package com.rovertech.utomo.app.offers;

import android.util.Log;

import com.rovertech.utomo.app.main.notification.NotificationAdapter;
import com.rovertech.utomo.app.main.notification.NotificationView;
import com.rovertech.utomo.app.offers.adpter.AdminOfferAdapter;
import com.rovertech.utomo.app.offers.model.OfferItem;
import com.rovertech.utomo.app.offers.model.OfferPojo;
import com.rovertech.utomo.app.offers.model.OfferView;

import java.util.ArrayList;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class AdminOfferPresenterImpl implements AdminOfferPresenter {

    private OfferView mAdminOfferView;

    public AdminOfferPresenterImpl(OfferView notificationView) {
        this.mAdminOfferView = notificationView;
    }

    @Override
    public void init() {

        mAdminOfferView.init();
        mAdminOfferView.initToolBar();
    }

    @Override
    public void destroy() {


    }


}
