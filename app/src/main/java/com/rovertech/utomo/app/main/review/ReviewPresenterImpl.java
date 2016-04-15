package com.rovertech.utomo.app.main.review;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.review.model.ReviewOutput;
import com.rovertech.utomo.app.main.review.model.ReviewRequest;
import com.rovertech.utomo.app.main.review.service.ReviewService;
import com.rovertech.utomo.app.widget.dialog.SuccessDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public class ReviewPresenterImpl implements ReviewPresenter {

    // View
    ReviewView view;
    int serviceCenterId;

    // Constructor
    public ReviewPresenterImpl(ReviewView view, int serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
        this.view = view;
    }

    @Override
    public void submitRating(final Context context, String strMessage, float rating) {

        if (rating == 0) {
            Functions.showToast(context, "Rating not to be empty");

        } else {
            ReviewRequest request = new ReviewRequest();
            request.ClientID = PrefUtils.getUserFullProfileDetails(context).UserID;
            request.FeedBackMessage = strMessage;
            request.Rating = rating;
            request.ServiceCentreID = serviceCenterId;

            Log.e("rating_request", Functions.jsonString(request));

            ReviewService service = UtomoApplication.retrofit.create(ReviewService.class);
            Call<ReviewOutput> call = service.doReview(request);
            call.enqueue(new Callback<ReviewOutput>() {
                @Override
                public void onResponse(Call<ReviewOutput> call, Response<ReviewOutput> response) {
                    if (response.body() == null) {
                        Functions.showToast(context, "Error occured.");

                    } else {

                        Log.e("rating_res", Functions.jsonString(response.body()));

                        ReviewOutput output = response.body();

                        if (output.AddReview.ResponseCode == 1) {
                            SuccessDialog dialog = new SuccessDialog(context, context.getResources().getString(R.string.review_success));
                            dialog.setOnSubmitListener(new SuccessDialog.onSubmitListener() {
                                @Override
                                public void onSubmit() {
                                    ((Activity) context).finish();
                                }
                            });
                            dialog.show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewOutput> call, Throwable t) {

                }
            });
        }

    }
    // end of class
}
