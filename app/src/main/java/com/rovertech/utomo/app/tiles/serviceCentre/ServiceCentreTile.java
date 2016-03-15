package com.rovertech.utomo.app.tiles.serviceCentre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centerListing.ServiceCenterPojo;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class ServiceCentreTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtCentreName, txtCentreAddress, txtRating, txtReviews, txtOffers, txtDistance;
    private ImageView imgCenter;

    public ServiceCentreTile(Context context, View view) {
        super(context);
        this.context = context;
        this.parentView = view;
        init();
    }

    private void init() {

        findViewById();

        setTypeface();

    }

    private void setTypeface() {
        txtCentreName.setTypeface(Functions.getBoldFont(context));
        txtCentreAddress.setTypeface(Functions.getNormalFont(context));
        txtRating.setTypeface(Functions.getNormalFont(context));
        txtReviews.setTypeface(Functions.getNormalFont(context));
        txtOffers.setTypeface(Functions.getNormalFont(context));
        txtDistance.setTypeface(Functions.getNormalFont(context));

    }

    private void findViewById() {
        txtCentreName = (TextView) parentView.findViewById(R.id.txtCentreName);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtCentreAddress = (TextView) parentView.findViewById(R.id.txtCentreAddress);
        txtRating = (TextView) parentView.findViewById(R.id.txtRating);
        txtReviews = (TextView) parentView.findViewById(R.id.txtReviews);
        txtOffers = (TextView) parentView.findViewById(R.id.txtOffers);
        txtDistance = (TextView) parentView.findViewById(R.id.txtDistance);

    }

    public void setDetails(ServiceCenterPojo centerPojo) {
        txtCentreName.setText(centerPojo.centreName);
        txtCentreAddress.setText(centerPojo.centreInfo);
        txtRating.setText(centerPojo.centreRating + "");
        txtReviews.setText(centerPojo.centreReviewCount + " Reviews");

        if (centerPojo.isOffer) {
            txtOffers.setVisibility(VISIBLE);
        } else {
            txtOffers.setVisibility(GONE);
        }
    }
}
