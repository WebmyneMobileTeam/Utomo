package com.rovertech.utomo.app.offers;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.offers.adpter.AdminOfferAdapter;
import com.rovertech.utomo.app.offers.model.OfferPojo;
import com.rovertech.utomo.app.offers.model.OfferView;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

import java.util.ArrayList;

public class AdminOffersLIstingActivity extends AppCompatActivity implements OfferView {

    private AdminOfferPresenter mAdminOfferPresenter;
    private ProgressDialog dialog;
    private AdminOfferAdapter adminOfferAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offers_listing);

        init();
    }

    public void init() {
        initToolbar();

        FamiliarRecyclerView offerFamiliarRecyclerView = (FamiliarRecyclerView) findViewById(R.id.notificationsRecyclerView);
        adminOfferAdapter = new AdminOfferAdapter(this, new ArrayList<OfferPojo>(), true);
        offerFamiliarRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        offerFamiliarRecyclerView.setAdapter(adminOfferAdapter);

        mAdminOfferPresenter = new AdminOfferPresenterImpl(this, this);

        if (!Functions.isConnected(this)) {
            Functions.showErrorAlert(this, AppConstant.NO_INTERNET_CONNECTION, true);
        } else {
            mAdminOfferPresenter.callOfferApi();
        }
    }

    @Override
    public void ShowProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setTitle("Please wait....");
        }
        dialog.show();
    }

    @Override
    public void HideProgressDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void setOfferList(ArrayList<OfferPojo> offerList) {

        adminOfferAdapter.setOfferList(offerList);
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        TextView txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        txtCustomTitle.setText("Offers");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

}
