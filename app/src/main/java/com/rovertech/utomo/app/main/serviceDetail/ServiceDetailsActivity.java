package com.rovertech.utomo.app.main.serviceDetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.invoice.InvoiceActivity;
import com.rovertech.utomo.app.main.review.ReviewActivity;
import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingData;
import com.rovertech.utomo.app.main.serviceDetail.serviceMain.ServiceMainDetails;

public class ServiceDetailsActivity extends AppCompatActivity implements ServiceView, View.OnClickListener {

    private Toolbar toolbar;
    private View parentView;
    private LinearLayout bottomCall, bottomDirection, bottomReview, bottomCancelReq, bottomAccept, bottomReject;
    private ServicePresenter presenter;
    private CoordinatorLayout main_content;

    private Button btnInvoice;

    private int bookingId;
    private ProgressDialog progressDialog;

    private ServiceMainDetails mainDetails;
    private UserBookingData userBookingData;

    private boolean isBottomSheetExpanded = false;

    private TextView txtCancel, txtReviews, txtMap, txtCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        bookingId = getIntent().getIntExtra("bookingId", 0);

        init();

        presenter = new ServicePresenterImpl(this, this);

        presenter.fetchBookingDetails(bookingId);
    }

    private void init() {

        initToolbar();

        btnInvoice = (Button) findViewById(R.id.btnInvoice);
        txtCancel = (TextView) findViewById(R.id.txtCancel);
        txtCall = (TextView) findViewById(R.id.txtCall);
        txtMap = (TextView) findViewById(R.id.txtMap);
        txtReviews = (TextView) findViewById(R.id.txtReviews);

        main_content = (CoordinatorLayout) findViewById(R.id.main_content);

        mainDetails = (ServiceMainDetails) findViewById(R.id.mainDetails);

        parentView = findViewById(android.R.id.content);
        bottomCall = (LinearLayout) findViewById(R.id.bottomCall);
        bottomDirection = (LinearLayout) findViewById(R.id.bottomDirection);
        bottomCancelReq = (LinearLayout) findViewById(R.id.bottomCancelReq);
        bottomAccept = (LinearLayout) findViewById(R.id.bottomAccept);
        bottomReject = (LinearLayout) findViewById(R.id.bottomReject);
        bottomReview = (LinearLayout) findViewById(R.id.bottomReview);

        clickListener();

        setTypeface();
    }

    private void setTypeface() {
        txtCancel.setTypeface(Functions.getRegularFont(this));
        txtReviews.setTypeface(Functions.getRegularFont(this));
        txtMap.setTypeface(Functions.getRegularFont(this));
        txtCall.setTypeface(Functions.getRegularFont(this));
    }

    private void clickListener() {
        bottomCall.setOnClickListener(this);
        bottomDirection.setOnClickListener(this);
        bottomReview.setOnClickListener(this);
        bottomCancelReq.setOnClickListener(this);
        bottomAccept.setOnClickListener(this);
        bottomReject.setOnClickListener(this);
        btnInvoice.setOnClickListener(this);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bottomCall:
                Functions.makePhoneCall(this, userBookingData.ContactNo);
                break;

            case R.id.bottomDirection:
                Functions.openInMap(ServiceDetailsActivity.this, userBookingData.Lattitude, userBookingData.Longitude, userBookingData.ServiceCentreName);
                break;

            case R.id.bottomReview:
                Intent reviewIntent = new Intent(ServiceDetailsActivity.this, ReviewActivity.class);
                reviewIntent.putExtra("serviceCenterId", userBookingData.ServiceCentreID);
                startActivity(reviewIntent);
                break;

            case R.id.bottomCancelReq:
                presenter.cancelBooking(userBookingData.BookingID);
                break;

            case R.id.bottomAccept:
                presenter.rescheduleBooking(true, userBookingData);
                break;

            case R.id.bottomReject:
                presenter.rescheduleBooking(false, userBookingData);
                break;

            case R.id.btnInvoice:
                Intent invoiceIntent = new Intent(ServiceDetailsActivity.this, InvoiceActivity.class);
                invoiceIntent.putExtra("bookingId", bookingId);
                startActivity(invoiceIntent);
                break;

        }
    }

    @Override
    public void showProgress() {
        main_content.setVisibility(View.GONE);
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait", false);
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void setBookingDetails(final UserBookingData userBookingData) {

        this.userBookingData = userBookingData;

        int statusID = userBookingData.BookingStatusID;

        mainDetails.setMainDetails(userBookingData);
        toolbar.setTitle(userBookingData.ServiceCentreName);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        toolbar.setSubtitle(String.format("Status : %s", userBookingData.Status));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.color70));

        main_content.setVisibility(View.VISIBLE);

        bottomCall.setVisibility(View.VISIBLE);
        bottomDirection.setVisibility(View.VISIBLE);

        if (statusID == AppConstant.SCHEDULE) {
            bottomAccept.setVisibility(View.VISIBLE);
            bottomReject.setVisibility(View.VISIBLE);
        } else {
            bottomAccept.setVisibility(View.GONE);
            bottomReject.setVisibility(View.GONE);
        }

        if (statusID == AppConstant.INVOICED) {
            btnInvoice.setVisibility(View.VISIBLE);
        } else {
            btnInvoice.setVisibility(View.GONE);
        }

        if (statusID == AppConstant.PENDING) {
            bottomCancelReq.setVisibility(View.VISIBLE);
            bottomReview.setVisibility(View.GONE);

        } else if (statusID == AppConstant.SCHEDULE) {
            bottomCancelReq.setVisibility(View.GONE);
            bottomReview.setVisibility(View.GONE);

        } else if (statusID == AppConstant.ACCEPTED) {
            bottomCancelReq.setVisibility(View.VISIBLE);
            bottomReview.setVisibility(View.GONE);

        } else if (statusID == AppConstant.PICKED) {
            bottomCancelReq.setVisibility(View.GONE);
            bottomReview.setVisibility(View.GONE);

        } else if (statusID == AppConstant.JOB_CARD_DONE) {
            bottomCancelReq.setVisibility(View.GONE);
            bottomReview.setVisibility(View.GONE);

        } else if (statusID == AppConstant.SERVICE_COMPLETED) {
            bottomCancelReq.setVisibility(View.GONE);
            bottomReview.setVisibility(View.GONE);

        } else if (statusID == AppConstant.INVOICED) {
            bottomCancelReq.setVisibility(View.GONE);
            bottomReview.setVisibility(View.GONE);

        } else if (statusID == AppConstant.PAYMENT_DONE) {
            bottomCancelReq.setVisibility(View.GONE);
            bottomReview.setVisibility(View.GONE);

        } else if (statusID == AppConstant.CAR_DELIVERED) {
            bottomCancelReq.setVisibility(View.GONE);
            bottomReview.setVisibility(View.VISIBLE);

        } else if (statusID == AppConstant.CANCELLED) {
            bottomCancelReq.setVisibility(View.GONE);
            bottomReview.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String message) {
        Functions.showToast(this, message);
        presenter.fetchBookingDetails(bookingId);
    }

    @Override
    public void cancelDone() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("Done")
                .setMessage("Your Booking has been cancelled successfully.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                        PrefUtils.setRefreshDashboard(ServiceDetailsActivity.this, true);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
