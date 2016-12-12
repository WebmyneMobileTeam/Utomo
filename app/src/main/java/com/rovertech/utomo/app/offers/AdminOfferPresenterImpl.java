package com.rovertech.utomo.app.offers;

import android.content.Context;
import android.util.Log;

import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.RetrofitErrorHelper;
import com.rovertech.utomo.app.main.centreDetail.offer.api.SCOfferRequestAPI;
import com.rovertech.utomo.app.main.centreDetail.offer.model.SCOfferResp;
import com.rovertech.utomo.app.offers.adpter.AdminOfferAdapter;
import com.rovertech.utomo.app.offers.model.AdminOfferResp;
import com.rovertech.utomo.app.offers.model.OfferPojo;
import com.rovertech.utomo.app.offers.model.OfferView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class AdminOfferPresenterImpl implements AdminOfferPresenter {

    private final Context c;
    private OfferView mAdminOfferView;
    private ArrayList<OfferPojo> itemList = new ArrayList<>();

    public AdminOfferPresenterImpl(Context c, OfferView notificationView) {
        this.c = c;
        this.mAdminOfferView = notificationView;

    }

    @Override
    public void callOfferApi() {
        mAdminOfferView.ShowProgressDialog();

        AdminOfferRequestAPI api = UtomoApplication.retrofit.create(AdminOfferRequestAPI.class);
        Call<AdminOfferResp> call = api.adminOfferApi();

        call.enqueue(new Callback<AdminOfferResp>() {
            @Override
            public void onResponse(Call<AdminOfferResp> call, Response<AdminOfferResp> response) {
                mAdminOfferView.HideProgressDialog();
                if (response.body().FetchAdminOffer.ResponseCode == 1) {

                    if (response.body().FetchAdminOffer.Data.size() > 0) {

                       /* for (int i = 0; i < response.body().FetchAdminOffer.Data.size(); i++) {
                            itemList = response.body().FetchAdminOffer.Data.get(i).getAllOffersList();
                        }*/
                        mAdminOfferView.setOfferList(response.body().FetchAdminOffer.Data.get(0).getAllOffersList());
                    }

                }
            }

            @Override
            public void onFailure(Call<AdminOfferResp> call, Throwable t) {
                mAdminOfferView.HideProgressDialog();
                Log.e("error", t.toString());
                RetrofitErrorHelper.showErrorMsg(t, c);
            }
        });

    }

    @Override
    public void callSCoffferApi(int serviceCenterId) {
        mAdminOfferView.ShowProgressDialog();
        try {
            SCOfferRequestAPI api = UtomoApplication.retrofit.create(SCOfferRequestAPI.class);
            Call<SCOfferResp> call = api.SCOfferApi(serviceCenterId);

            call.enqueue(new Callback<SCOfferResp>() {
                @Override
                public void onResponse(Call<SCOfferResp> call, Response<SCOfferResp> response) {
                    mAdminOfferView.HideProgressDialog();

                    Log.d("Resp", response.body().toString());
                    if (response.body().FetchServiceCentreOffer.ResponseCode == 1) {

                        for (int i = 0; i < response.body().FetchServiceCentreOffer.Data.size(); i++) {
                            itemList = response.body().FetchServiceCentreOffer.Data.get(i).getAllOffersList();
                        }
                        mAdminOfferView.setOfferList(itemList);
                    }
                }

                @Override
                public void onFailure(Call<SCOfferResp> call, Throwable t) {
                    mAdminOfferView.HideProgressDialog();
                    RetrofitErrorHelper.showErrorMsg(t, c);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AdminOfferAdapter setAdminOfferdpter(boolean adminflag) {
        AdminOfferAdapter adminOfferAdapter = new AdminOfferAdapter(c, itemList, adminflag);
        return adminOfferAdapter;
    }


}
