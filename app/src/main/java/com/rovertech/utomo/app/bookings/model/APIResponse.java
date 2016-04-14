package com.rovertech.utomo.app.bookings.model;

import java.util.List;

/**
 * Created by raghavthakkar on 13-04-2016.
 */
public class APIResponse<T> {

    public List<T> Data;
    public int ResponseCode;
    public String ResponseMessage;


}
