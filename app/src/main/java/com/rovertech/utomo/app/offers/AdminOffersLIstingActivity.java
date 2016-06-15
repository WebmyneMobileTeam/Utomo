package com.rovertech.utomo.app.offers;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.VerticalSpaceItemDecoration;
import com.rovertech.utomo.app.offers.adpter.AdminOfferAdapter;
import com.rovertech.utomo.app.offers.model.AdminOfferResp;
import com.rovertech.utomo.app.offers.model.OfferPojo;
import com.rovertech.utomo.app.offers.model.OfferView;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOffersLIstingActivity extends AppCompatActivity implements OfferView {

    private Toolbar toolbar;
    private TextView txtCustomTitle;

    private AdminOfferPresenter mAdminOfferPresenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offers_listing);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait....");
        mAdminOfferPresenter = new AdminOfferPresenterImpl(this,this);
        mAdminOfferPresenter.init();

    }

    @Override
    public void init() {

        ShowProgressDialog();
        if (!Functions.isConnected(this)) {
            Functions.showErrorAlert(this, AppConstant.NO_INTERNET_CONNECTION, true);

        } else {
            mAdminOfferPresenter.callOfferApi();

        }

    }

    @Override
    public void initToolBar() {
        initToolbar();
    }

    @Override
    public void ShowProgressDialog() {
        dialog.show();
    }

    @Override
    public void HideProgressDialog() {
        dialog.dismiss();
    }

    @Override
    public void setUpRecyclerView(AdminOfferAdapter adminOfferAdapter) {
        FamiliarRecyclerView OfferFamiliarRecyclerView = (FamiliarRecyclerView) findViewById(R.id.notificationsRecyclerView);
        // OfferFamiliarRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        OfferFamiliarRecyclerView.setLayoutManager(linearLayoutManager);
        //Log.d("Itemlist", "Size= " + itemList.size() + "|| " + itemList.toString());
        OfferFamiliarRecyclerView.setAdapter(adminOfferAdapter);
        OfferFamiliarRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
    }


    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mNotificationPresenter.destroy();
    }




}
