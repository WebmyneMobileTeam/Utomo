package com.rovertech.utomo.app.main.centreDetail.centreMain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.main.centreDetail.offer.CentreOfferActivity;
import com.rovertech.utomo.app.widget.FlowLayout;
import com.rovertech.utomo.app.widget.serviceTypeChip.ServiceChip;

/**
 * Created by sagartahelyani on 16-03-2016.
 */
public class CentreMainDetails extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;
    private View parentView;
    private TextView txtCentreDealerShip, txtCentreAddress, txtCentreInfo, txtCentreEmail, txtCentreWebsite, txtRating;
    private Button btnBook;
    private FlowLayout serviceFlowLayout;
    LinearLayout.LayoutParams params;
    private ImageView imgOffer;

    public CentreMainDetails(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CentreMainDetails(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_centre_main, this, true);

        imgOffer = (ImageView) parentView.findViewById(R.id.imgOffer);
        txtCentreDealerShip = (TextView) parentView.findViewById(R.id.txtCentreDealerShip);
        txtCentreWebsite = (TextView) parentView.findViewById(R.id.txtCentreWebsite);
        txtCentreAddress = (TextView) parentView.findViewById(R.id.txtCentreAddress);
        txtCentreInfo = (TextView) parentView.findViewById(R.id.txtCentreInfo);
        txtCentreEmail = (TextView) parentView.findViewById(R.id.txtCentreEmail);
        txtRating = (TextView) parentView.findViewById(R.id.txtRating);
        serviceFlowLayout = (FlowLayout) parentView.findViewById(R.id.serviceFlowLayout);
        serviceFlowLayout.setOrientation(HORIZONTAL);

        setTypeface();

        serviceFlowLayout.removeAllViews();
        serviceFlowLayout.invalidate();

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    private void setTypeface() {
        txtCentreAddress.setTypeface(Functions.getRegularFont(context));
        txtCentreInfo.setTypeface(Functions.getRegularFont(context));
        txtCentreEmail.setTypeface(Functions.getRegularFont(context));
        txtCentreWebsite.setTypeface(Functions.getRegularFont(context));
        txtCentreDealerShip.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        //btnBook.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);

        txtRating.setTypeface(Functions.getRegularFont(context));

    }

    public void setDetails(final FetchServiceCentreDetailPojo centreDetailPojo) {

        txtCentreAddress.setText(String.format("%s", centreDetailPojo.Address1));
        txtCentreDealerShip.setText(String.format("Dealership : %s", centreDetailPojo.Dealership));

        if (centreDetailPojo.Rating != 0)
            txtRating.setText(String.format("%.1f/5", centreDetailPojo.Rating));
        else
            txtRating.setVisibility(GONE);

        txtCentreInfo.setText(String.format("%s", centreDetailPojo.Expertise));

        if (TextUtils.isEmpty(centreDetailPojo.Expertise)) {
            txtCentreInfo.setVisibility(GONE);
        } else {
            txtCentreInfo.setVisibility(VISIBLE);
            txtCentreInfo.setText(String.format("%s", centreDetailPojo.Expertise));
        }

        if (TextUtils.isEmpty(centreDetailPojo.ContactEmail)) {
            txtCentreEmail.setVisibility(GONE);
        } else {
            txtCentreEmail.setVisibility(VISIBLE);
            txtCentreEmail.setText(String.format("%s", centreDetailPojo.ContactEmail));
        }

        if (TextUtils.isEmpty(centreDetailPojo.Website)) {
            txtCentreWebsite.setVisibility(GONE);
        } else {
            txtCentreWebsite.setVisibility(VISIBLE);
            txtCentreWebsite.setText(String.format("%s", centreDetailPojo.Website));
        }

        if (centreDetailPojo.IsBodyWash) {
            ServiceChip serviceChip = new ServiceChip(context, "Body Shop");
            serviceFlowLayout.addView(serviceChip, params);
        }

        if (centreDetailPojo.IsPickupDrop) {
            ServiceChip serviceChip = new ServiceChip(context, "Pick Drop");
            serviceFlowLayout.addView(serviceChip, params);
        }

        if (centreDetailPojo.IsOfferAvail) {
            imgOffer.setVisibility(VISIBLE);
            AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
            animation.setDuration(500);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setFillAfter(true);
            imgOffer.startAnimation(animation);
        } else {
            imgOffer.setVisibility(GONE);
        }

        imgOffer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CentreOfferActivity.class);
                intent.putExtra("centreId", centreDetailPojo.ServiceCentreID);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });

      /*  btnBook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                PrefUtils.setCenterSelected(context, centreDetailPojo);

                if (PrefUtils.isUserLoggedIn(context)) {
                    Intent intent = new Intent(context, BookingActivity.class);
                    intent.putExtra(IntentConstant.BOOKING_PAGE, AppConstant.FROM_SC_LIST);
                    Functions.fireIntent(context, intent);

                } else {
                    PrefUtils.setRedirectLogin(context, AppConstant.FROM_SKIP);
                   // Toast.makeText(context, "sd " + PrefUtils.getRedirectLogin(context), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    Functions.fireIntent(context, intent);
                }
            }
        });*/
    }

}
