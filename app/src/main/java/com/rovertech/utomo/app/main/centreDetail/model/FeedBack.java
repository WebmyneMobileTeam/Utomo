package com.rovertech.utomo.app.main.centreDetail.model;

import java.io.Serializable;

/**
 * Created by raghavthakkar on 30-03-2016.
 */
public class FeedBack  implements Serializable {

    public int ClientID;
    public String ClientName;
    public String FeedBackDate;
    public int FeedBackID;
    public String FeedBackMessage;
    public boolean IsHide;
    public float Rating;
    public String ReplyDate;
    public String ReplyToClient;
    public int ServiceCentreID;
}
