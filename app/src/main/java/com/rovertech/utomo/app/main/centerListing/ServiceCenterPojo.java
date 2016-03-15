package com.rovertech.utomo.app.main.centerListing;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 15-03-2016.
 */
public class ServiceCenterPojo implements Serializable {

    public String centreImage;

    public String centreName;

    public String centreInfo;

    public double centreRating;

    public int centreReviewCount;

    public boolean isOffer;

    public ServiceCenterPojo() {
        this.centreImage = "";
        this.centreName = "";
        this.centreInfo = "";
        this.centreRating = 0;
        this.centreReviewCount = 0;
        this.isOffer = false;
    }
}
