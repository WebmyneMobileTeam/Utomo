package com.rovertech.utomo.app.tiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

    private TextView txtTitle, txtBookingDate, txtCompleteDate, txtCenterName, txtServiceStatus, txtReviews, txtRating;
    private ImageView imgCenter;
    private CardView currentCardView;

    private int bookingId;

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
        parentView = inflater.inflate(R.layout.layout_tile_current_service, this, true);

        findViewById();

        setTypeface();

        currentCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServiceDetailsActivity.class);
                intent.putExtra("bookingId", bookingId);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
    }

    private void setTypeface() {
        txtBookingDate.setTypeface(Functions.getThinFont(context));
        txtCompleteDate.setTypeface(Functions.getThinFont(context));
        txtTitle.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        txtCenterName.setTypeface(Functions.getRegularFont(context));
        txtServiceStatus.setTypeface(Functions.getLightFont(context), Typeface.BOLD);
    }

    private void findViewById() {

        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        txtBookingDate = (TextView) parentView.findViewById(R.id.txtBookingDate);
        txtCompleteDate = (TextView) parentView.findViewById(R.id.txtCompleteDate);
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
            txtTitle.setVisibility(GONE);
            txtCompleteDate.setVisibility(GONE);

        } else if (bookingViewMode == MyBookingFragment.PASTBOOKING) {

            //// TODO: 17-05-2016 From WS changes, review rating avg
            txtReviews.setVisibility(GONE);
            txtRating.setVisibility(GONE);
            txtCompleteDate.setVisibility(VISIBLE);

            txtServiceStatus.setVisibility(VISIBLE);
            txtTitle.setText(String.format("%s", "Current Car Service"));
            txtTitle.setVisibility(GONE);
            if (userBookingsPojo.IsCarDelivered) {
                txtCompleteDate.setText(String.format("Car Delivered on: %s", userBookingsPojo.IsCarDeliveredDateTime));
            } else {
                txtCompleteDate.setVisibility(GONE);
            }
        }

        bookingId = userBookingsPojo.BookingID;

        txtCenterName.setText(userBookingsPojo.SCName);
        Functions.LoadImage(imgCenter, userBookingsPojo.SCImageName, context);
        txtBookingDate.setText(String.format("Booking on: %s", Functions.displayOnlyDate(userBookingsPojo.PreferendDateTime)));
        txtServiceStatus.setText(String.format("Service Status: %s", userBookingsPojo.Status));

    }

    public void setDetails(DashboardData data, @MyBookingFragment.BookingViewMode int bookingViewMode) {

        txtTitle.setVisibility(VISIBLE);

        if (bookingViewMode == MyBookingFragment.CURRENTBOOKING) {
            txtReviews.setVisibility(GONE);
            txtRating.setVisibility(GONE);
            txtCompleteDate.setVisibility(GONE);
            txtServiceStatus.setVisibility(VISIBLE);
            txtTitle.setText(String.format("%s", "Service Running"));

        } else if (bookingViewMode == MyBookingFragment.PASTBOOKING) {
            txtReviews.setVisibility(GONE);
            txtRating.setVisibility(GONE);
            txtServiceStatus.setVisibility(GONE);
            txtCompleteDate.setVisibility(VISIBLE);
            txtTitle.setText(String.format("%s", "Past Service"));
            txtCompleteDate.setText(String.format("Car Delivered on: %s", data.ServiceCompletedDateTime));
        }

        bookingId = data.BookingID;

        Functions.LoadImage(imgCenter, data.SCImageName, context);
        txtCenterName.setText(data.ServiceCentreName);
        txtBookingDate.setText(String.format("Booked on: %s", data.CreatedDate));
        txtServiceStatus.setText(String.format("Service Status: %s", data.Status));

        if (data.ReviewCount == 0) {
            txtReviews.setText("No Reviews");
        } else {
            txtReviews.setText(String.format("%d %s", data.ReviewCount, "Reviews"));
        }
    }
}
