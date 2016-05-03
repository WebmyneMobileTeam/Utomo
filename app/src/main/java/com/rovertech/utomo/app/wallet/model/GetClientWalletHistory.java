package com.rovertech.utomo.app.wallet.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sagartahelyani on 03-05-2016.
 */
public class GetClientWalletHistory implements Serializable {

    public int ResponseCode;

    public String ResponseMessage;

    public ArrayList<WalletPojo> Data;
}
