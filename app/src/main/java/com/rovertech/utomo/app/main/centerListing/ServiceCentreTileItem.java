package com.rovertech.utomo.app.main.centerListing;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centreDetail.CentreDetailsActivity;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class ServiceCentreTileItem extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtCentreName, txtCentreAddress, txtRating, txtReviews, txtOffers, txtDistance;
    private ImageView imgCenter;
    private CardView cardLayout;

    public ServiceCentreTileItem(Context context, View view) {
        super(context);
        this.context = context;
        this.parentView = view;
        init();
    }

    private void init() {

       /* inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_centre_item, this, false);*/

        findViewById();

        setTypeface();

        cardLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(context, CentreDetailsActivity.class);
            }
        });
    }

    private void setTypeface() {
        txtCentreName.setTypeface(Functions.getBoldFont(context));
        txtCentreAddress.setTypeface(Functions.getRegularFont(context));
        txtRating.setTypeface(Functions.getRegularFont(context));
        txtReviews.setTypeface(Functions.getRegularFont(context));
        txtOffers.setTypeface(Functions.getRegularFont(context));
        txtDistance.setTypeface(Functions.getRegularFont(context));

    }

    private void findViewById() {
        txtCentreName = (TextView) parentView.findViewById(R.id.txtCentreName);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtCentreAddress = (TextView) parentView.findViewById(R.id.txtCentreAddress);
        txtRating = (TextView) parentView.findViewById(R.id.txtRating);
        txtReviews = (TextView) parentView.findViewById(R.id.txtReviews);
        txtOffers = (TextView) parentView.findViewById(R.id.txtOffers);
        txtDistance = (TextView) parentView.findViewById(R.id.txtDistance);
        cardLayout = (CardView) parentView.findViewById(R.id.cardLayout);

    }

    public void setDetails(ServiceCenterPojo centerPojo) {
        txtCentreName.setText(centerPojo.ServiceCentreName);
        //txtCentreAddress.setText(centerPojo.centreInfo);
        txtRating.setText(centerPojo.ServiceCentreID + "");
        //txtReviews.setText(centerPojo.centreReviewCount + " Reviews");

        if (centerPojo.IsOfferAvaill) {
            txtOffers.setVisibility(VISIBLE);
        } else {
            txtOffers.setVisibility(GONE);
        }
    }
}
