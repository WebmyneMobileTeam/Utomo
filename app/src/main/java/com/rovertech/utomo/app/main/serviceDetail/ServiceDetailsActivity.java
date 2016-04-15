package com.rovertech.utomo.app.main.serviceDetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.review.ReviewActivity;
import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingData;
import com.rovertech.utomo.app.main.serviceDetail.serviceHeader.ServiceHeaderDetails;
import com.rovertech.utomo.app.main.serviceDetail.serviceMain.ServiceMainDetails;

public class ServiceDetailsActivity extends AppCompatActivity implements ServiceView, View.OnClickListener {

    private Toolbar toolbar;
    private View parentView, bottomSheet;
    private BottomSheetBehavior behavior;
    private FloatingActionButton fab;
    private LinearLayout bottomCall, bottomDirection, bottomReview;
    private ServicePresenter presenter;
    private CoordinatorLayout main_content;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    private int bookingId;
    private ProgressDialog progressDialog;

    private ServiceHeaderDetails headerDetails;
    private ServiceMainDetails mainDetails;
    private UserBookingData userBookingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        bookingId = getIntent().getIntExtra("bookingId", 0);

        init();

        presenter = new ServicePresenterImpl(this);

        presenter.fetchBookingDetails(ServiceDetailsActivity.this, bookingId);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });
    }

    private void init() {

        initToolbar();

        main_content = (CoordinatorLayout) findViewById(R.id.main_content);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(Functions.getBoldFont(this));

        headerDetails = (ServiceHeaderDetails) findViewById(R.id.headerDetails);
        mainDetails = (ServiceMainDetails) findViewById(R.id.mainDetails);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Maruti Service Centre");
                    isShow = true;

                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

        parentView = findViewById(android.R.id.content);
        bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        bottomCall = (LinearLayout) findViewById(R.id.bottomCall);
        bottomDirection = (LinearLayout) findViewById(R.id.bottomDirection);
        bottomReview = (LinearLayout) findViewById(R.id.bottomReview);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        clickListener();
    }

    private void clickListener() {
        bottomCall.setOnClickListener(this);
        bottomDirection.setOnClickListener(this);
        bottomReview.setOnClickListener(this);
        bottomSheet.setOnClickListener(this);
        fab.setOnClickListener(this);
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
                Intent dialIntent = new Intent();
                dialIntent.setAction(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + userBookingData.ContactNo));
                startActivity(dialIntent);
                break;

            case R.id.bottomDirection:
                Functions.openInMap(ServiceDetailsActivity.this, userBookingData.Lattitude, userBookingData.Longitude, userBookingData.ServiceCentreName);
                break;

            case R.id.bottomReview:
                Intent reviewIntent  = new Intent(ServiceDetailsActivity.this, ReviewActivity.class);
                reviewIntent.putExtra("serviceCenterId", userBookingData.ServiceCentreID);
                startActivity(reviewIntent);
                break;

            case R.id.bottom_sheet:
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;

            case R.id.fab:
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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

        headerDetails.setHeaderDetails(userBookingData);
        mainDetails.setMainDetails(userBookingData);

        main_content.setVisibility(View.VISIBLE);

    }
}
