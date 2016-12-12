package com.rovertech.utomo.app.offers.model;

import com.rovertech.utomo.app.offers.adpter.AdminOfferAdapter;

import java.util.ArrayList;

/**
 * Created by vaibhavirana on 25-04-2016.
 */
public interface OfferView {

    void ShowProgressDialog();

    void HideProgressDialog();

    void setOfferList(ArrayList<OfferPojo> offerList);
}
