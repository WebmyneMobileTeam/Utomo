package com.rovertech.utomo.app.invoice.model;

import java.util.List;

/**
 * Created by priyasindkar on 29-04-2016.
 */
public class PaymentDetailsDataModelItem {
    public double  TotalAmount, SCOfferDiscount;
    public double PayableAmount;
    public List<PaymentJobCardDetailsModel> lstJobCardDeatils;
    public List<PaymentOfferDiscountList> lstOfferDiscount;

}
