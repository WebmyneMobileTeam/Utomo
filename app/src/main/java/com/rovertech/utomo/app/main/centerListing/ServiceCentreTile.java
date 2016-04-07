package com.rovertech.utomo.app.main.centerListing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centreDetail.CentreDetailsActivity;
import com.rovertech.utomo.app.tiles.centreServiceType.CentreServiceTypeTile;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class ServiceCentreTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private LinearLayout serviceTypeLayout, offerLayout;
    private TextView txtCentreName, txtCentreAddress, txtRating, txtReviews, txtOffers, txtDistance;
    private ImageView imgCenter;
    private CardView cardLayout;

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
        offerLayout = (LinearLayout) parentView.findViewById(R.id.offerLayout);
        serviceTypeLayout = (LinearLayout) parentView.findViewById(R.id.serviceTypeLayout);
        txtCentreName = (TextView) parentView.findViewById(R.id.txtCentreName);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtCentreAddress = (TextView) parentView.findViewById(R.id.txtCentreAddress);
        txtRating = (TextView) parentView.findViewById(R.id.txtRating);
        txtReviews = (TextView) parentView.findViewById(R.id.txtReviews);
        txtOffers = (TextView) parentView.findViewById(R.id.txtOffers);
        txtDistance = (TextView) parentView.findViewById(R.id.txtDistance);
        cardLayout = (CardView) parentView.findViewById(R.id.cardLayout);

    }

    public void setDetails(final ServiceCenterPojo centerPojo) {

        serviceTypeLayout.removeAllViews();
        serviceTypeLayout.invalidate();

        txtCentreName.setText(centerPojo.ServiceCentreName);
        txtCentreAddress.setText("");

        if (centerPojo.Rating != 0)
            txtRating.setText(String.format("%.1f", centerPojo.Rating));
        else
            txtRating.setVisibility(GONE);

        if (centerPojo.ReviewCounter == 0)
            txtReviews.setText("No Reviews");
        else
            txtReviews.setText(String.format("%d Reviews", centerPojo.ReviewCounter));

        if (centerPojo.IsOfferAvaill) {
            offerLayout.setVisibility(VISIBLE);
        } else {
            offerLayout.setVisibility(GONE);
        }

        if (centerPojo.IsBodyWash) {
            CentreServiceTypeTile tile = new CentreServiceTypeTile(context, "Body Wash");
            serviceTypeLayout.addView(tile);
        }

        if (centerPojo.IsPickupDrop) {
            CentreServiceTypeTile tile = new CentreServiceTypeTile(context, "Pickup-Drop");
            serviceTypeLayout.addView(tile);
        }

        if (centerPojo.DistanceKM == 0) {
            txtDistance.setVisibility(GONE);
        } else {
            txtDistance.setText(centerPojo.DistanceKM + " Km");
        }

        cardLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CentreDetailsActivity.class);
                intent.putExtra("centreId", centerPojo.ServiceCentreID);
                context.startActivity(intent);


            }
        });
    }
}
