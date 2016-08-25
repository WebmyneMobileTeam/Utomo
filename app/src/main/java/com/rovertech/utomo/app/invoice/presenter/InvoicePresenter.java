package com.rovertech.utomo.app.invoice.presenter;

import com.rovertech.utomo.app.invoice.model.PaymentApiRequest;
import com.rovertech.utomo.app.invoice.model.PaymentProcessResponse;

/**
 * Created by priyasindkar on 29-04-2016.
 */
public interface InvoicePresenter {

    void getTransactionProcessDetails(int bookingId);

    void doPayment(long totalDiscount, int bookingId, long offerId, PaymentProcessResponse paymentProcessResponse, int serviceCentreId);
}
