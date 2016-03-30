package com.rovertech.utomo.app.main.review.model;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 28-03-2016.
 */
public class ReviewRequest implements Serializable {

    public int ClientID;

    public String FeedBackMessage;

    public boolean IsHide;

    public float Rating;

    public int ServiceCentreID;

    public ReviewRequest() {
        ClientID = 0;
        FeedBackMessage = "";
        IsHide = false;
        Rating = 0;
        ServiceCentreID = 0;
    }
}
