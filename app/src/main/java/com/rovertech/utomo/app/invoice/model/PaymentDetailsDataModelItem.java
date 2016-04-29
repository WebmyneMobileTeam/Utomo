package com.rovertech.utomo.app.invoice.model;

import java.util.List;

/**
 * Created by priyasindkar on 29-04-2016.
 */
public class PaymentDetailsDataModelItem {
    public long PayableAmount, TotalAmount;
    public List<PaymentJobCardDetailsModel> lstJobCardDeatils;
    public List<PaymentOfferDiscountList> lstOfferDiscount;

}
