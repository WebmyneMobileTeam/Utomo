package com.rovertech.utomo.app.invoice.model;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 24-08-2016.
 */
public class PaymentApiRequest implements Serializable {

    public double AdminDiscount;

    public String BookingCode;

    public int BookingID;

    public int OfferID;

    public double PayableAmount;

    public int ServiceCentreId;

    public String Token;

    public double TotalAmount;

    public int UserId;

    public PaymentApiRequest() {
        AdminDiscount = 0;
        BookingCode = "";
        BookingID = 0;
        OfferID = 0;
        PayableAmount = 0;
        ServiceCentreId = 0;
        Token = "";
        TotalAmount = 0;
        UserId = 0;
    }
}
