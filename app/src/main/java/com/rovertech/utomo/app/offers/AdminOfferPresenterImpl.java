package com.rovertech.utomo.app.offers;

import com.rovertech.utomo.app.offers.model.OfferView;

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
