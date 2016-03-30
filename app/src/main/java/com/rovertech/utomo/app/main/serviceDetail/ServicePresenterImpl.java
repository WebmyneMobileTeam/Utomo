package com.rovertech.utomo.app.main.serviceDetail;

import android.content.Context;
import android.widget.Toast;

import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.review.ReviewActivity;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public class ServicePresenterImpl implements ServicePresenter {

    ServiceView serviceView;

    public ServicePresenterImpl(ServiceView serviceView) {
        this.serviceView = serviceView;
    }

    @Override
    public void onCall(Context context) {
        Toast.makeText(context, "Call", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDirection(Context context) {
        Toast.makeText(context, "Direction", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReview(Context context) {
        Functions.fireIntent(context, ReviewActivity.class);
    }
}
