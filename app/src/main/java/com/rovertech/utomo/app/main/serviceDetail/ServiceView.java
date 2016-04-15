package com.rovertech.utomo.app.main.serviceDetail;

import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingData;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public interface ServiceView {

    void showProgress();

    void hideProgress();

    void setBookingDetails(UserBookingData userBookingData);
}
