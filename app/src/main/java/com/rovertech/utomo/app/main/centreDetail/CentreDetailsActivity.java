package com.rovertech.utomo.app.main.centreDetail;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
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
    private float distance=0.0f;

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
        mainHolder = (LinearLayout) findViewById(R.id.mainHolder);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        centreDetailsPresnter = new CentreDetailsPresnterImpl(this);

        Intent intent = getIntent();
        int serviceCenterId = intent.getExtras().getInt("centreId");
        distance = intent.getFloatExtra("DistanceKM", 0.0f);
        centreDetailsPresnter.fetchServiceCenterDetails(serviceCenterId);
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
    public void setDetails(FetchServiceCentreDetailPojo centreDetailPojo) {
        centreHeaderDetails.setDetails(centreDetailPojo,distance);
        centreMainDetails.setDetails(centreDetailPojo);
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
