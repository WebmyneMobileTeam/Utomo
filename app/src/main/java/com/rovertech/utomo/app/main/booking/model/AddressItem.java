package com.rovertech.utomo.app.main.booking.model;

import com.rovertech.utomo.app.offers.model.OfferPojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sagartahelyani on 11-05-2016.
 */
public class AddressItem implements Serializable {

    public ArrayList<DropPojo> DropDetails;

    public ArrayList<PickupPojo> PickUpDetails;

}
