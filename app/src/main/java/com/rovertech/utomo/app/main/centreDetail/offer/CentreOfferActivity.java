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
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.helper.VerticalSpaceItemDecoration;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.main.centreDetail.offer.api.SCOfferRequestAPI;
import com.rovertech.utomo.app.main.centreDetail.offer.model.SCOfferResp;
import com.rovertech.utomo.app.offers.AdminOfferPresenter;
import com.rovertech.utomo.app.offers.AdminOfferPresenterImpl;
import com.rovertech.utomo.app.offers.adpter.AdminOfferAdapter;
import com.rovertech.utomo.app.offers.model.OfferView;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CentreOfferActivity extends AppCompatActivity implements OfferView {

    private Toolbar toolbar;
    private TextView txtCustomTitle;
    private int serviceCenterId;
    private AdminOfferPresenter mAdminOfferPresenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centre_offer);
        serviceCenterId=getIntent().getIntExtra("centreId",0);
        Log.e("selected center", getIntent().getIntExtra("centreId",0)+"");
        //Log.d("selected center", PrefUtils.getCurrentCenter(this).toString());

        mAdminOfferPresenter = new AdminOfferPresenterImpl(this, this);
        mAdminOfferPresenter.init();
    }

    @Override
    public void init() {

        //ShowProgressDialog();
        if (!Functions.isConnected(this)) {
            Functions.showErrorAlert(this, AppConstant.NO_INTERNET_CONNECTION, true);

        } else {
            mAdminOfferPresenter.callSCoffferApi(serviceCenterId);
            //callSCoffferApi(serviceCenterId);
        }

    }

    @Override
    public void initToolBar() {
        initToolbar();
    }

    @Override
    public void ShowProgressDialog() {
        //dialog.show();
    }

    @Override
    public void HideProgressDialog() {
        //dialog.dismiss();
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
        try{
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
                public void onFailure (Call < SCOfferResp > call, Throwable t){
                    Log.e("error", t.toString());
                }

            });}
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
