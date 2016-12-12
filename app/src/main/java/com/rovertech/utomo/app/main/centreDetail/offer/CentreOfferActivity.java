package com.rovertech.utomo.app.main.centreDetail.offer;

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
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.RetrofitErrorHelper;
import com.rovertech.utomo.app.main.centreDetail.offer.api.SCOfferRequestAPI;
import com.rovertech.utomo.app.main.centreDetail.offer.model.SCOfferResp;
import com.rovertech.utomo.app.offers.AdminOfferPresenter;
import com.rovertech.utomo.app.offers.AdminOfferPresenterImpl;
import com.rovertech.utomo.app.offers.adpter.AdminOfferAdapter;
import com.rovertech.utomo.app.offers.model.OfferPojo;
import com.rovertech.utomo.app.offers.model.OfferView;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CentreOfferActivity extends AppCompatActivity implements OfferView {

    private int serviceCenterId;
    private AdminOfferPresenter mAdminOfferPresenter;
    private ProgressDialog dialog;
    private AdminOfferAdapter adminOfferAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centre_offer);
        serviceCenterId = getIntent().getIntExtra("centreId", 0);

        init();

    }

    public void init() {

        initToolbar();

        TextView emptyNotifications = (TextView) findViewById(R.id.emptyNotifications);
        emptyNotifications.setTypeface(Functions.getRegularFont(this));

        FamiliarRecyclerView offerFamiliarRecyclerView = (FamiliarRecyclerView) findViewById(R.id.notificationsRecyclerView);
        adminOfferAdapter = new AdminOfferAdapter(this, new ArrayList<OfferPojo>(), false);
        offerFamiliarRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        offerFamiliarRecyclerView.setAdapter(adminOfferAdapter);

        mAdminOfferPresenter = new AdminOfferPresenterImpl(this, this);

        if (!Functions.isConnected(this)) {
            Functions.showErrorAlert(this, AppConstant.NO_INTERNET_CONNECTION, true);

        } else {
            mAdminOfferPresenter.callSCoffferApi(serviceCenterId);
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
        txtCustomTitle.setText("Service Center Offers");
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


    public void callSCoffferApi(int serviceCenterId) {
        try {
            SCOfferRequestAPI api = UtomoApplication.retrofit.create(SCOfferRequestAPI.class);
            Call<SCOfferResp> call = api.SCOfferApi(serviceCenterId);

            call.enqueue(new Callback<SCOfferResp>() {
                @Override
                public void onResponse(Call<SCOfferResp> call, Response<SCOfferResp> response) {

                    Log.d("Resp", response.body().toString());
                    if (response.body().FetchServiceCentreOffer.ResponseCode == 1) {
                        Log.d("Resp", response.body().FetchServiceCentreOffer.Data.toString());
                    /*    for (int i = 0; i < response.body().FetchServiceCentreOffer.Data.size(); i++) {
                            itemList = response.body().FetchServiceCentreOffer.Data.get(i).getAllOffersList();
                            mAdminOfferView.HideProgressDialog();
                            mAdminOfferView.setUpRecyclerView(setAdminOfferdpter());
                        }*/
                    }
                }

                @Override
                public void onFailure(Call<SCOfferResp> call, Throwable t) {
                    RetrofitErrorHelper.showErrorMsg(t, CentreOfferActivity.this);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
