package com.rovertech.utomo.app.tiles;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.home.car.model.DashboardData;
import com.rovertech.utomo.app.main.serviceDetail.ServiceDetailsActivity;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class PastServiceTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle, txtBookingDate, txtCenterName, txtRating, txtReviews, txtRepeat;
    private ImageView imgCenter;
    private CardView pastCardView;

    public PastServiceTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PastServiceTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public View getParentView() {
        return parentView;
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_past_service_revised, this, true);

        findViewById();

        setTypeface();

        pastCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(context, ServiceDetailsActivity.class);
            }
        });

    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
        txtBookingDate.setTypeface(Functions.getNormalFont(context));
        txtCenterName.setTypeface(Functions.getBoldFont(context));
        txtRating.setTypeface(Functions.getNormalFont(context));
        txtRepeat.setTypeface(Functions.getBoldFont(context));
        txtReviews.setTypeface(Functions.getNormalFont(context));
    }

    private void findViewById() {
        pastCardView = (CardView) parentView.findViewById(R.id.pastCardView);
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtBookingDate = (TextView) parentView.findViewById(R.id.txtBookingDate);
        txtCenterName = (TextView) parentView.findViewById(R.id.txtCenterName);
        txtRating = (TextView) parentView.findViewById(R.id.txtRating);
        txtReviews = (TextView) parentView.findViewById(R.id.txtReviews);
        txtRepeat = (TextView) parentView.findViewById(R.id.txtRepeat);

        txtReviews.setText(Html.fromHtml("<u>1 Review</u>"));
    }

    public void setDetails(DashboardData data) {

    }
}
