package com.rovertech.utomo.app.main.review.service;

import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.main.review.model.ReviewOutput;
import com.rovertech.utomo.app.main.review.model.ReviewRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sagartahelyani on 28-03-2016.
 */
public interface ReviewService {

    @POST(AppConstant.ADD_REVIEW)
    Call<ReviewOutput> doReview(@Body ReviewRequest reviewRequest);
}
