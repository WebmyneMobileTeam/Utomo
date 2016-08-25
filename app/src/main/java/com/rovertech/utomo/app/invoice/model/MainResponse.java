package com.rovertech.utomo.app.invoice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sagartahelyani on 24-08-2016.
 */
public class MainResponse implements Serializable {

    public List<PaymentJobCardDetailsModel> lstJobCardDeatils;

    public double  TotalAmount, AdminOfferDiscount;

    public double PayableAmount;
}
