package com.rovertech.utomo.app.invoice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
    private TextView txtCustomTitle, txtTotalAmount, txtTotalPayableAmount, discountTitle;
    private LinearLayout linearServiceDetails, linearOfferDiscountsDetails, emptyLayout;
    private FamiliarRecyclerView adminOffersRecyclerView;
    private PaymentDiscountOffersAdapter discountOffersAdapter;
    private CardView serviceDetailsCardView;
    private Button btnContinuePayment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        Log.e("bookingid", getIntent().getIntExtra("bookingId", 0) + "");

        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        txtCustomTitle.setText("Service Payment Details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });

        discountTitle = (TextView) findViewById(R.id.discountTitle);
        discountTitle.setTypeface(Functions.getRegularFont(this));
        txtTotalAmount = (TextView) findViewById(R.id.txtTotalAmount);
        txtTotalPayableAmount = (TextView) findViewById(R.id.txtTotalPayableAmount);
        linearServiceDetails = (LinearLayout) findViewById(R.id.linearServiceDetails);
        adminOffersRecyclerView = (FamiliarRecyclerView) findViewById(R.id.adminOffersRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adminOffersRecyclerView.setLayoutManager(linearLayoutManager);
        adminOffersRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        linearOfferDiscountsDetails = (LinearLayout) findViewById(R.id.linearOfferDiscountsDetails);
        serviceDetailsCardView = (CardView) findViewById(R.id.serviceDetailsCardView);
        btnContinuePayment = (Button) findViewById(R.id.btnContinuePayment);
        emptyLayout = (LinearLayout) findViewById(R.id.emptyLayout);

        presenter = new InvoicePresenterImpl(InvoiceActivity.this, this);
        bookinId = getIntent().getIntExtra("bookingId", 0);
        presenter.getTransactionProcessDetails(bookinId);
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
    public void setPaymentAndOfferDetails(final PaymentProcessResponse paymentProcessResponse) {

        if(paymentProcessResponse.PaymentProcess.ResponseCode == 0) {
            serviceDetailsCardView.setVisibility(View.GONE);
            adminOffersRecyclerView.setVisibility(View.GONE);
            discountTitle.setVisibility(View.GONE);
            btnContinuePayment.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            serviceDetailsCardView.setVisibility(View.VISIBLE);
            adminOffersRecyclerView.setVisibility(View.VISIBLE);
            discountTitle.setVisibility(View.VISIBLE);
            btnContinuePayment.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);

            txtTotalAmount.setText(getString(R.string.ruppee) + " " + String.valueOf(paymentProcessResponse.PaymentProcess.Data.get(0).TotalAmount));
            txtTotalPayableAmount.setText(getString(R.string.ruppee) + " " + String.valueOf(paymentProcessResponse.PaymentProcess.Data.get(0).PayableAmount));

            //inflate service details
            final List<PaymentJobCardDetailsModel> serviceJobsList = paymentProcessResponse.PaymentProcess.Data.get(0).lstJobCardDeatils;
            final List<String> jobsList = new ArrayList<>();
            final List<PaymentDistinctDiscountModel> filteredDiscounts = new ArrayList<>();
            final List<PaymentDistinctDiscountModel> allDiscountModels = new ArrayList<>();

            linearServiceDetails.removeAllViews();

            if( !serviceJobsList.isEmpty()) { // show service details only if service details available
                for (int i = 0; i < serviceJobsList.size(); i++) {
                    View view = LayoutInflater.from(this).inflate(R.layout.payment_service_item, null);
                    TextView txtServiceName = (TextView) view.findViewById(R.id.txtServiceName);
                    TextView txtServiceAmount = (TextView) view.findViewById(R.id.txtServiceAmount);

                    txtServiceAmount.setText(getString(R.string.ruppee) + " " + String.valueOf(serviceJobsList.get(i).NetAmount));
                    txtServiceName.setText(serviceJobsList.get(i).Diagnosis);
                    jobsList.add(serviceJobsList.get(i).Diagnosis);
                    linearServiceDetails.addView(view);
                }

                // show available offers
                final List<PaymentOfferDiscountList> discountOffersList = paymentProcessResponse.PaymentProcess.Data.get(0).lstOfferDiscount;

                if( !discountOffersList.isEmpty()) {
                    for (int i = 0; i < discountOffersList.size(); i++) {
                        for (int j = 0; j < serviceJobsList.size(); j++) {
                            allDiscountModels.add(discountOffersList.get(i).lstDistinctDiscount.get(j));
                        }
                    }

                    discountOffersAdapter = new PaymentDiscountOffersAdapter(this, discountOffersList, jobsList, allDiscountModels);
                    adminOffersRecyclerView.setAdapter(discountOffersAdapter);
                    discountOffersAdapter.setOnOfferSelectedListener(new PaymentDiscountOffersAdapter.OnOfferSelectedListener() {
                        @Override
                        public void onOfferSelected(List<PaymentDistinctDiscountModel> discountOfferItems) {
                            for (int j = 0; j < discountOfferItems.size(); j++) {
                                for (int i = 0; i < jobsList.size(); i++) {
                                    if (discountOfferItems.get(j).ServiceName.equals(jobsList.get(i))) {
                                        filteredDiscounts.add(discountOffersList.get(0).lstDistinctDiscount.get(i));
                                    }
                                }
                                if (discountOfferItems.get(j).ServiceName.equals("Invoice")) {
                                    filteredDiscounts.add(discountOfferItems.get(j));
                                }
                            }
                            Log.e("avail selected offers", Functions.jsonString(filteredDiscounts));

                            long totalDiscount = 0;
                            linearOfferDiscountsDetails.removeAllViews();
                            for (int i = 0; i < filteredDiscounts.size(); i++) {
                                totalDiscount += Long.parseLong(filteredDiscounts.get(i).DiscountAmount);
                            }

                            View view = LayoutInflater.from(InvoiceActivity.this).inflate(R.layout.payment_service_item, null);
                            TextView txtServiceName = (TextView) view.findViewById(R.id.txtServiceName);
                            TextView txtServiceAmount = (TextView) view.findViewById(R.id.txtServiceAmount);

                            txtServiceAmount.setText(" - " + getString(R.string.ruppee) + " " + totalDiscount);
                            txtServiceName.setText("Discounted Amount");
                            txtServiceAmount.setTextColor(ContextCompat.getColor(InvoiceActivity.this, R.color.button_bg));
                            txtServiceName.setTextColor(ContextCompat.getColor(InvoiceActivity.this, R.color.button_bg));
                            linearOfferDiscountsDetails.addView(view);
                            txtTotalPayableAmount.setText(getString(R.string.ruppee) + " " + String.valueOf(paymentProcessResponse.PaymentProcess.Data.get(0).PayableAmount - totalDiscount));
                        }
                    });
                } else {
                    adminOffersRecyclerView.setVisibility(View.GONE);
                    discountTitle.setVisibility(View.GONE);
                }
            } else {
                serviceDetailsCardView.setVisibility(View.GONE);
                adminOffersRecyclerView.setVisibility(View.GONE);
                discountTitle.setVisibility(View.GONE);
                btnContinuePayment.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}
