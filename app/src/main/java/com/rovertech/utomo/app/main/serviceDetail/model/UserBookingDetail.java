package com.rovertech.utomo.app.main.serviceDetail.model;

import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sagartahelyani on 15-04-2016.
 */
public class UserBookingDetail implements Serializable {

    public int ResponseCode;

    public String ResponseMessage;

    public ArrayList<UserBookingData> Data;

}