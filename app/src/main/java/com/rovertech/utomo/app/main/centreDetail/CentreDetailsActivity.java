package com.rovertech.utomo.app.main.centreDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.main.centreDetail.service.CentreDetailsView;

public class CentreDetailsActivity extends AppCompatActivity implements CentreDetailsView {

    private Toolbar toolbar;
    private TextView txtCustomTitle;
    private View parentView;
    private CentreDetailsPresnter centreDetailsPresnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centre_details);

        init();
    }

    private void init() {


        parentView = findViewById(android.R.id.content);
        centreDetailsPresnter = new CentreDetailsPresnterImpl(this);

        //Todo sagar Replace static id 2 with intent data
        centreDetailsPresnter.fetchServiceCenterDetails(2);
    }

    @Override
    public void initToolbar() {
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Service Centre Details");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this));

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


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        centreDetailsPresnter.destory();
    }
}
