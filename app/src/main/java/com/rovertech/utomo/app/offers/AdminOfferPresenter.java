package com.rovertech.utomo.app.offers;

import android.support.v7.widget.RecyclerView;

import com.rovertech.utomo.app.offers.adpter.AdminOfferAdapter;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public interface AdminOfferPresenter {

    void callOfferApi();

    void callSCoffferApi(int serviceCenterId);

    AdminOfferAdapter setAdminOfferdpter(boolean adminFlag);
}
