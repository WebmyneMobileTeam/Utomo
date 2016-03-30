package com.rovertech.utomo.app.main.centreDetail.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by raghavthakkar on 30-03-2016.
 */
public class FetchServiceCentreDetail {

    @SerializedName("Data")
    public List<FetchServiceCentreDetailPojo> fetchServiceCentreDetailPojo;
    public int ResponseCode;
    public String ResponseMessage;
}
