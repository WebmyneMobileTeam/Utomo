package com.rovertech.utomo.app.home.car.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sagartahelyani on 12-04-2016.
 */
public class DashboardResponse implements Serializable{

    public ArrayList<DashboardData> Data;

    public int ResponseCode;

    public String ResponseMessage;
}
