package com.rovertech.utomo.app.invoice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.VerticalSpaceItemDecoration;
import com.rovertech.utomo.app.invoice.adapter.PaymentDiscountOffersAdapter;
import com.rovertech.utomo.app.invoice.model.PaymentDistinctDiscountModel;
import com.rovertech.utomo.app.invoice.model.PaymentJobCardDetailsModel;
import com.rovertech.utomo.app.invoice.model.PaymentOfferDiscountList;
import com.rovertech.utomo.app.invoice.model.PaymentProcessResponse;
import com.rovertech.utomo.app.invoice.presenter.InvoicePresenter;
import com.rovertech.utomo.app.invoice.presenter.InvoicePresenterImpl;
import com.rovertech.utomo.app.invoice.presenter.InvoiceView;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priyasindkar on 29-04-2016.
 */
public class InvoiceActivity extends AppCompatActivity implements InvoiceView {
    private InvoicePresenter presenter;
    private ProgressDialog progressDialog;
    private int bookinId;
    private TextView txtTotalAmount, txtTotalPayableAmount, discountTitle;
    private LinearLayout linearServiceDetails;
    private FamiliarRecyclerView adminOffersRecyclerView;
    private PaymentDiscountOffersAdapter discountOffersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        Log.e("bookingid", getIntent().getIntExtra("bookingId", 0) + "");

        init();
    }

    private void init() {
        discountTitle = (TextView) findViewById(R.id.discountTitle);
        discountTitle.setTypeface(Functions.getRegularFont(this));
        txtTotalAmount = (TextView) findViewById(R.id.txtTotalAmount);
        txtTotalPayableAmount = (TextView) findViewById(R.id.txtTotalPayableAmount);
        linearServiceDetails = (LinearLayout) findViewById(R.id.linearServiceDetails);
        adminOffersRecyclerView = (FamiliarRecyclerView) findViewById(R.id.adminOffersRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adminOffersRecyclerView.setLayoutManager(linearLayoutManager);
        adminOffersRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        presenter = new InvoicePresenterImpl(InvoiceActivity.this, this);
        //todo
        presenter.getTransactionProcessDetails(9/*getIntent().getIntExtra("bookingId", 0)*/);
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait..", false);
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void setPaymentAndOfferDetails(PaymentProcessResponse paymentProcessResponse) {
        txtTotalAmount.setText(String.valueOf(paymentProcessResponse.PaymentProcess.Data.get(0).TotalAmount));
        txtTotalPayableAmount.setText(String.valueOf(paymentProcessResponse.PaymentProcess.Data.get(0).PayableAmount));

        //inflate service details
        List<PaymentJobCardDetailsModel> serviceJobsList = paymentProcessResponse.PaymentProcess.Data.get(0).lstJobCardDeatils;
        List<String> jobsList  = new ArrayList<>();
        List<PaymentDistinctDiscountModel> filteredDiscounts  = new ArrayList<>();

        linearServiceDetails.removeAllViews();

        for (int i = 0; i < serviceJobsList.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.payment_service_item, null);
            TextView txtServiceName = (TextView) view.findViewById(R.id.txtServiceName);
            TextView txtServiceAmount = (TextView) view.findViewById(R.id.txtServiceAmount);

            txtServiceAmount.setText(String.valueOf(serviceJobsList.get(i).NetAmount));
            txtServiceName.setText(serviceJobsList.get(i).Diagnosis);
            jobsList.add(serviceJobsList.get(i).Diagnosis);
            linearServiceDetails.addView(view);
        }

        // show available offers
        List<PaymentOfferDiscountList> discountOffersList = paymentProcessResponse.PaymentProcess.Data.get(0).lstOfferDiscount;

        for(int i=0 ; i < discountOffersList.size(); i++) {
            for(int j=0; j< serviceJobsList.size(); j++) {
                if(discountOffersList.get(i).lstDistinctDiscount.get(i).ServiceName.equals(serviceJobsList.get(j))){
                    filteredDiscounts.add(discountOffersList.get(i).lstDistinctDiscount.get(i));
                }
            }
        }

        discountOffersAdapter = new PaymentDiscountOffersAdapter(this, discountOffersList, jobsList, filteredDiscounts);
        adminOffersRecyclerView.setAdapter(discountOffersAdapter);
    }
}
