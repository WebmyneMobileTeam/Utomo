package com.rovertech.utomo.app.offers.model;

import com.rovertech.utomo.app.offers.adpter.AdminOfferAdapter;

/**
 * Created by vaibhavirana on 25-04-2016.
 */
public interface OfferView {

    void init();

    void initToolBar();

    void ShowProgressDialog();

    void HideProgressDialog();

    void setUpRecyclerView(AdminOfferAdapter adminOfferAdapter);
}
