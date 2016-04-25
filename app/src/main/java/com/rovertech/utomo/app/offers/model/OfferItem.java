package com.rovertech.utomo.app.offers.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sagartahelyani on 22-04-2016.
 */
public class OfferItem implements Serializable{

    public ArrayList<OfferPojo> allOffersList;

    public ArrayList<OfferPojo> getAllOffersList() {
        return allOffersList;
    }

    public void setAllOffersList(ArrayList<OfferPojo> allOffersList) {
        this.allOffersList = allOffersList;
    }
}
