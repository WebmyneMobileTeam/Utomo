package com.rovertech.utomo.app.main.centreDetail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.LoginActivity;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.booking.BookingActivity;
import com.rovertech.utomo.app.main.centreDetail.centreHeader.CentreHeaderDetails;
import com.rovertech.utomo.app.main.centreDetail.centreMain.CentreMainDetails;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.main.centreDetail.service.CentreDetailsView;

public class CentreDetailsActivity extends AppCompatActivity implements CentreDetailsView {

    private LinearLayout mainHolder;
    private Toolbar toolbar;
    private TextView txtCustomTitle;
    private View parentView;
    private CentreDetailsPresnter centreDetailsPresnter;
    private CentreMainDetails centreMainDetails;
    private CentreHeaderDetails centreHeaderDetails;
    private ProgressBar mProgressBar;
    private String distance = "";
    private Button btnBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centre_details);

        init();
    }

    private void init() {

        // Toast.makeText(CentreDetailsActivity.this, "centreId ## " + getIntent().getExtras().getInt("centreId"), Toast.LENGTH_SHORT).show();
        parentView = findViewById(android.R.id.content);
        centreMainDetails = (CentreMainDetails) findViewById(R.id.centreMainDetails);
        centreHeaderDetails = (CentreHeaderDetails) findViewById(R.id.centreHeaderDetails);

        btnBook = (Button) findViewById(R.id.btnBook);

        mainHolder = (LinearLayout) findViewById(R.id.mainHolder);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        centreDetailsPresnter = new CentreDetailsPresnterImpl(this);

        Intent intent = getIntent();
        int serviceCenterId = intent.getExtras().getInt("centreId");
        distance = intent.getStringExtra("DistanceKM");
        centreDetailsPresnter.fetchServiceCenterDetails(serviceCenterId,this);

        initToolbar();
    }

    @Override
    public void initToolbar() {
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Service Centre Details");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });
    }

    @Override
    public void setDetails(final FetchServiceCentreDetailPojo centreDetailPojo) {

        txtCustomTitle.setText(String.format("%s", centreDetailPojo.ServiceCentreName));
        centreHeaderDetails.setDetails(centreDetailPojo, distance);
        centreMainDetails.setDetails(centreDetailPojo);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.setCenterSelected(CentreDetailsActivity.this, centreDetailPojo);

                if (PrefUtils.isUserLoggedIn(CentreDetailsActivity.this)) {
                    Intent intent = new Intent(CentreDetailsActivity.this, BookingActivity.class);
                    intent.putExtra(IntentConstant.BOOKING_PAGE, AppConstant.FROM_SC_LIST);
                    Functions.fireIntent(CentreDetailsActivity.this, intent);

                } else {
                    PrefUtils.setRedirectLogin(CentreDetailsActivity.this, AppConstant.FROM_SKIP);
                    // Toast.makeText(context, "sd " + PrefUtils.getRedirectLogin(context), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CentreDetailsActivity.this, LoginActivity.class);
                    Functions.fireIntent(CentreDetailsActivity.this, intent);
                }
            }
        });
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void showMainLayoutHolder() {
        mainHolder.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideMainLayoutHolder() {
        mainHolder.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        centreDetailsPresnter.destory();
    }
}
