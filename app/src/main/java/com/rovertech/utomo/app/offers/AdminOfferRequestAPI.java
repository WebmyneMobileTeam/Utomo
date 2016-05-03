package com.rovertech.utomo.app.offers;

import com.rovertech.utomo.app.bookings.model.BookingRequest;
import com.rovertech.utomo.app.bookings.model.RequestForBooking;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.centreDetail.offer.model.SCOfferResp;
import com.rovertech.utomo.app.offers.model.AdminOfferResp;
import com.rovertech.utomo.app.offers.model.FetchAdminOffer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by raghavthakkar on 13-04-2016.
 */
public interface AdminOfferRequestAPI {

    @GET(AppConstant.FETCH_ADMIN_OFFER)
    Call<AdminOfferResp> adminOfferApi();

}
