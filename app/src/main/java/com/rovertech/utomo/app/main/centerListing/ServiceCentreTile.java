package com.rovertech.utomo.app.main.centerListing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centreDetail.CentreDetailsActivity;
import com.rovertech.utomo.app.widget.FlowLayout;
import com.rovertech.utomo.app.widget.labelView.LabelImageView;
import com.rovertech.utomo.app.widget.serviceTypeChip.ServiceChip;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class ServiceCentreTile extends LinearLayout {

    Context context;
    private View parentView;
    private FlowLayout serviceTypeLayout;
    private TextView txtCentreName, txtRating, txtReviews, txtDistance;
    private ImageView imgCenter;
    private CardView cardLayout;
    LinearLayout.LayoutParams params;
    private LabelImageView offerAvailable;
    private LinearLayout distanceLayout;

    public ServiceCentreTile(Context context, View view) {
        super(context);
        this.context = context;
        this.parentView = view;
        init();
    }

    private void init() {

        findViewById();

        setTypeface();

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    private void setTypeface() {
        txtCentreName.setTypeface(Functions.getBoldFont(context));
        txtRating.setTypeface(Functions.getRegularFont(context));
        txtReviews.setTypeface(Functions.getRegularFont(context));
        txtDistance.setTypeface(Functions.getRegularFont(context));

    }

    private void findViewById() {
        distanceLayout = (LinearLayout) parentView.findViewById(R.id.distanceLayout);
        serviceTypeLayout = (FlowLayout) parentView.findViewById(R.id.serviceTypeLayout);
        serviceTypeLayout.setOrientation(HORIZONTAL);
        txtCentreName = (TextView) parentView.findViewById(R.id.txtCentreName);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtRating = (TextView) parentView.findViewById(R.id.txtRating);
        txtReviews = (TextView) parentView.findViewById(R.id.txtReviews);
        txtDistance = (TextView) parentView.findViewById(R.id.txtDistance);
        cardLayout = (CardView) parentView.findViewById(R.id.cardLayout);
        offerAvailable = (LabelImageView) parentView.findViewById(R.id.offerAvailable);
    }

    public void setDetails(final ServiceCenterPojo centerPojo) {

        Functions.LoadImage(imgCenter, centerPojo.ServiceCentreImage, context);

        serviceTypeLayout.removeAllViews();
        serviceTypeLayout.invalidate();

        txtCentreName.setText(centerPojo.ServiceCentreName);

        if (centerPojo.Rating != 0)
            txtRating.setText(String.format("%.1f/5", centerPojo.Rating));
        else
            txtRating.setVisibility(GONE);

        if (centerPojo.ReviewCounter == 0) {
            txtReviews.setVisibility(GONE);
        } else {
            txtReviews.setVisibility(VISIBLE);
            txtReviews.setText(String.format("%d Reviews", centerPojo.ReviewCounter));
        }

        if (centerPojo.IsOfferAvaill) {
            offerAvailable.setVisibility(VISIBLE);
        } else {
            offerAvailable.setVisibility(GONE);
        }

        if (centerPojo.IsBodyWash) {
            ServiceChip serviceChip = new ServiceChip(context, "Body Shop");
            serviceTypeLayout.addView(serviceChip, params);
        }

        if (centerPojo.IsPickupDrop) {
            ServiceChip serviceChip = new ServiceChip(context, "Pickup-Drop off");
            serviceTypeLayout.addView(serviceChip, params);
        }

        if (centerPojo.DistanceKM == null || centerPojo.DistanceKM.equals("")) {
            distanceLayout.setVisibility(GONE);

        } else {
            distanceLayout.setVisibility(VISIBLE);
            txtDistance.setText(String.format("%s", centerPojo.DistanceKM));
        }

        cardLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CentreDetailsActivity.class);
                intent.putExtra("centreId", centerPojo.ServiceCentreID);
                intent.putExtra("DistanceKM", centerPojo.DistanceKM);
                context.startActivity(intent);
            }
        });
    }
}
