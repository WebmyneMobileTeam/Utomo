package com.rovertech.utomo.app.main.centerListing.model;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 28-03-2016.
 */
public class CentreListRequest implements Serializable{

    public int CityId;

    public String DealerShipName;

    public boolean IsBodyWash;

    public boolean IsPickupDrop;

    public int LastServiceCentreID;

    @Nullable
    public Double Lattitude = null;

    @Nullable
    public Double Longitude = null;

}

/*
{
        "CityId":2,
        "DealerShipName":"",
        "IsBodyWash":false,
        "IsPickupDrop":false,
        "LastServiceCentreID":0,
        "Lattitude":0.0,
        "Longitude":0.0
        }*/
