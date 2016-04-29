package com.rovertech.utomo.app.invoice.presenter;

import com.rovertech.utomo.app.invoice.model.PaymentProcessResponse;

/**
 * Created by priyasindkar on 29-04-2016.
 */
public interface InvoiceView {
    void showProgress();
    void hideProgress();
    void setPaymentAndOfferDetails(PaymentProcessResponse paymentProcessResponse);
}
