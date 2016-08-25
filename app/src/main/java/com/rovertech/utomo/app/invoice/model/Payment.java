package com.rovertech.utomo.app.invoice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sagartahelyani on 24-08-2016.
 */
public class Payment implements Serializable {

    public List<MainResponse> Data;

    public int ResponseCode;

    public String ResponseMessage;

}
