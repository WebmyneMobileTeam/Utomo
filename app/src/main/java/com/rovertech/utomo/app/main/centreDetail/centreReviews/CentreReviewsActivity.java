package com.rovertech.utomo.app.main.centreDetail.centreReviews;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

public class CentreReviewsActivity extends AppCompatActivity implements CentreReviewsView {


    private CentrePresenter mCentrePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centre_reviews);
        mCentrePresenter = new CentrePresenterImpl(this);
        mCentrePresenter.initView();
    }

    @Override
    public void init() {
        Toolbar toolbar;
        TextView txtCustomTitle;
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Reviews");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });

        mCentrePresenter.setUpRecyclerView(this);

    }

    @Override
    public void setUpRecyclerView(CentreReviewAdapter centreReviewAdapter) {
        FamiliarRecyclerView centreFamiliarRecyclerView = (FamiliarRecyclerView) findViewById(R.id.centreFamiliarRecyclerView);
        LinearLayoutManager centerReviewLinearLayoutManager = new LinearLayoutManager(this);
        centreFamiliarRecyclerView.setLayoutManager(centerReviewLinearLayoutManager);
        centreFamiliarRecyclerView.setAdapter(centreReviewAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCentrePresenter.destroy();
    }
}
