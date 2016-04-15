package com.rovertech.utomo.app.tiles;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.bookings.CurrentBooking.model.UserBookingsPojo;
import com.rovertech.utomo.app.bookings.MyBookingFragment;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.home.car.model.DashboardData;
import com.rovertech.utomo.app.main.serviceDetail.ServiceDetailsActivity;


public class CurrentServiceTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtBookingId, txtBookingDate, txtCenterName, txtServiceStatus, txtReviews, txtRating;
    private ImageView imgCenter;
    private CardView currentCardView;

    public CurrentServiceTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CurrentServiceTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_current_service_revised, this, true);

        findViewById();

        setTypeface();

        currentCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(context, ServiceDetailsActivity.class);
            }
        });
    }

    private void setTypeface() {
        txtBookingId.setTypeface(Functions.getBoldFont(context));
        txtBookingDate.setTypeface(Functions.getNormalFont(context));
        txtCenterName.setTypeface(Functions.getBoldFont(context));
        txtServiceStatus.setTypeface(Functions.getBoldFont(context));
    }

    private void findViewById() {
        txtBookingId = (TextView) parentView.findViewById(R.id.txtBookingId);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtBookingDate = (TextView) parentView.findViewById(R.id.txtBookingDate);
        txtCenterName = (TextView) parentView.findViewById(R.id.txtCenterName);
        txtServiceStatus = (TextView) parentView.findViewById(R.id.txtServiceStatus);

        txtReviews = (TextView) parentView.findViewById(R.id.txtReviews);
        txtRating = (TextView) parentView.findViewById(R.id.txtRating);

        currentCardView = (CardView) parentView.findViewById(R.id.currentCardView);
    }

    public View getParentView() {
        return parentView;
    }

    public void setCurrentServiceDetails(UserBookingsPojo userBookingsPojo, @MyBookingFragment.BookingViewMode int bookingViewMode) {

        if (bookingViewMode == MyBookingFragment.CURRENTBOOKING) {
            txtReviews.setVisibility(GONE);
            txtRating.setVisibility(GONE);
            txtServiceStatus.setVisibility(VISIBLE);

        } else if (bookingViewMode == MyBookingFragment.PASTBOOKING) {
            txtReviews.setVisibility(VISIBLE);
            txtRating.setVisibility(VISIBLE);
            txtServiceStatus.setVisibility(GONE);
        }

        txtBookingId.setText(String.format("%s : %s", "Booking Id", userBookingsPojo.BookingID));
        txtCenterName.setText(userBookingsPojo.SCName);
        Functions.LoadImage(imgCenter, userBookingsPojo.SCImageName, context);
        txtBookingDate.setText(Functions.displayOnlyDate(userBookingsPojo.CreatedDate));
        txtServiceStatus.setText(userBookingsPojo.Status);

    }

    public void setDetails(DashboardData data, @MyBookingFragment.BookingViewMode int bookingViewMode) {

        if (bookingViewMode == MyBookingFragment.CURRENTBOOKING) {
            txtReviews.setVisibility(GONE);
            txtRating.setVisibility(GONE);
            txtServiceStatus.setVisibility(VISIBLE);

        } else if (bookingViewMode == MyBookingFragment.PASTBOOKING) {
            txtReviews.setVisibility(VISIBLE);
            txtRating.setVisibility(VISIBLE);
            txtServiceStatus.setVisibility(GONE);
        }

        txtBookingId.setText(String.format("%s : %d", "Booking ID", data.BookingID));
        Functions.LoadImage(imgCenter, data.SCImageName, context);
        txtCenterName.setText(data.ServiceCentreName);
        txtBookingDate.setText(data.CreatedDate);
        txtServiceStatus.setText(data.Status);
        txtReviews.setText(String.format("%d %s", data.ReviewCount, "Reviews"));
    }
}
