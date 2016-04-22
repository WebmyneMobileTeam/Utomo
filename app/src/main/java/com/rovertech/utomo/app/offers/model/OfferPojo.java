package com.rovertech.utomo.app.offers.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sagartahelyani on 22-04-2016.
 */
public class OfferPojo implements Serializable {

    public ArrayList<OfferCategory> lstAvailOffersCategory;

    public int AvailOfferId;

    public String Description;

    public String OfferCode;

    public String OfferImage;

    public String OfferName;

    public String ValidFrom;

    public String ValidTo;

}
