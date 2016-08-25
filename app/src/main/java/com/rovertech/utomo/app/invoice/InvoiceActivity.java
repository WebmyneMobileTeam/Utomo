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
import com.rovertech.utomo.app.invoice.model.PaymentApiRequest;
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
    private TextView txtCustomTitle, txtTotalAmount, txtTotalPayableAmount, discountTitle, txtSCDiscountOfferLabel, txtSCDiscountOfferAmount, txtAvaildisc;
    private LinearLayout linearServiceDetails, linearOfferDiscountsDetails, emptyLayout;
    private FamiliarRecyclerView adminOffersRecyclerView;
    private PaymentDiscountOffersAdapter discountOffersAdapter;
    private CardView serviceDetailsCardView;
    private Button btnContinuePayment;
    private View scOfferDiscountItem;
    private PaymentProcessResponse paymentProcessResponse;
    long totalDiscount = 0;

    int serviceCentreId, bookingId;
    long offerId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        bookingId = getIntent().getIntExtra("bookingId", 0);
        serviceCentreId = getIntent().getIntExtra("serviceCentreId", 0);

        Log.e("bookingid", getIntent().getIntExtra("bookingId", 0) + "");

        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        txtCustomTitle.setTypeface(Functions.getRegularFont(this));
        txtCustomTitle.setText("Service Payment Details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        txtAvaildisc = (TextView) findViewById(R.id.txtAvaildisc);
        txtAvaildisc.setTypeface(Functions.getRegularFont(this));

        discountTitle = (TextView) findViewById(R.id.discountTitle);
        discountTitle.setTypeface(Functions.getRegularFont(this));

        txtTotalAmount = (TextView) findViewById(R.id.txtTotalAmount);
        txtTotalAmount.setTypeface(Functions.getRegularFont(this));

        txtTotalPayableAmount = (TextView) findViewById(R.id.txtTotalPayableAmount);
        txtTotalPayableAmount.setTypeface(Functions.getRegularFont(this));

        linearServiceDetails = (LinearLayout) findViewById(R.id.linearServiceDetails);
        adminOffersRecyclerView = (FamiliarRecyclerView) findViewById(R.id.adminOffersRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adminOffersRecyclerView.setLayoutManager(linearLayoutManager);
        adminOffersRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        linearOfferDiscountsDetails = (LinearLayout) findViewById(R.id.linearOfferDiscountsDetails);
        serviceDetailsCardView = (CardView) findViewById(R.id.serviceDetailsCardView);
        btnContinuePayment = (Button) findViewById(R.id.btnContinuePayment);
        emptyLayout = (LinearLayout) findViewById(R.id.emptyLayout);
        scOfferDiscountItem = findViewById(R.id.scOfferDiscountItem);

        txtSCDiscountOfferLabel = (TextView) scOfferDiscountItem.findViewById(R.id.txtServiceName);
        txtSCDiscountOfferLabel.setTextColor(ContextCompat.getColor(this, R.color.button_bg));
        txtSCDiscountOfferLabel.setTypeface(Functions.getRegularFont(this));

        txtSCDiscountOfferAmount = (TextView) scOfferDiscountItem.findViewById(R.id.txtServiceAmount);
        txtSCDiscountOfferAmount.setTextColor(ContextCompat.getColor(this, R.color.button_bg));
        txtSCDiscountOfferAmount.setTypeface(Functions.getRegularFont(this));

        presenter = new InvoicePresenterImpl(InvoiceActivity.this, this);
        presenter.getTransactionProcessDetails(bookingId);

        btnContinuePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.doPayment(totalDiscount, bookingId, offerId, paymentProcessResponse, serviceCentreId);

            }
        });
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
        this.paymentProcessResponse = paymentProcessResponse;
        setPaymentDetailsUI(paymentProcessResponse);
    }


    private void setPaymentDetailsUI(final PaymentProcessResponse paymentProcessResponse) {
        if (paymentProcessResponse.PaymentProcess.ResponseCode == 0) {
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

            totalDiscount = 0;
            txtTotalAmount.setText(getString(R.string.ruppee) + " " + String.valueOf(paymentProcessResponse.PaymentProcess.Data.get(0).TotalAmount));
            txtTotalPayableAmount.setText(getString(R.string.ruppee) + " " + String.valueOf(paymentProcessResponse.PaymentProcess.Data.get(0).PayableAmount));

            if (paymentProcessResponse.PaymentProcess.Data.get(0).SCOfferDiscount == 0) {
                scOfferDiscountItem.setVisibility(View.GONE);
            } else {
                scOfferDiscountItem.setVisibility(View.VISIBLE);
            }

            txtSCDiscountOfferLabel.setText("Service Center Offer Discount: ");
            txtSCDiscountOfferAmount.setText(getString(R.string.ruppee) + " " + String.valueOf(paymentProcessResponse.PaymentProcess.Data.get(0).SCOfferDiscount));

            //inflate service details
            final List<PaymentJobCardDetailsModel> serviceJobsList = paymentProcessResponse.PaymentProcess.Data.get(0).lstJobCardDeatils;
            final List<String> jobsList = new ArrayList<>();
            final List<PaymentDistinctDiscountModel> filteredDiscounts = new ArrayList<>();
            final List<PaymentDistinctDiscountModel> allDiscountModels = new ArrayList<>();

            linearServiceDetails.removeAllViews();

            if (!serviceJobsList.isEmpty()) { // show service details only if service details available
                for (int i = 0; i < serviceJobsList.size(); i++) {
                    View view = LayoutInflater.from(this).inflate(R.layout.payment_service_item, null);
                    TextView txtServiceName = (TextView) view.findViewById(R.id.txtServiceName);
                    TextView txtServiceAmount = (TextView) view.findViewById(R.id.txtServiceAmount);

                    txtServiceName.setTypeface(Functions.getRegularFont(this));
                    txtServiceAmount.setTypeface(Functions.getRegularFont(this));

                    txtServiceAmount.setText(getString(R.string.ruppee) + " " + String.valueOf(serviceJobsList.get(i).NetAmount));
                    txtServiceName.setText(serviceJobsList.get(i).Diagnosis);
                    jobsList.add(serviceJobsList.get(i).Diagnosis);
                    linearServiceDetails.addView(view);
                }

                // show available offers
                final List<PaymentOfferDiscountList> discountOffersList = paymentProcessResponse.PaymentProcess.Data.get(0).lstOfferDiscount;

                if (!discountOffersList.isEmpty()) {
                    for (int i = 0; i < discountOffersList.size(); i++) {
                        for (int k = 0; k < discountOffersList.get(i).lstDistinctDiscount.size(); k++) {
                            allDiscountModels.add(discountOffersList.get(i).lstDistinctDiscount.get(k));
                        }
                    }

                    discountOffersAdapter = new PaymentDiscountOffersAdapter(this, discountOffersList, jobsList, allDiscountModels);
                    adminOffersRecyclerView.setAdapter(discountOffersAdapter);
                    discountOffersAdapter.setOnOfferSelectedListener(new PaymentDiscountOffersAdapter.OnOfferSelectedListener() {
                        @Override
                        public void onOfferSelected(boolean isOfferSelected, List<PaymentDistinctDiscountModel> discountOfferItems) {
                            Log.e("jobsList", Functions.jsonString(jobsList));
                            Log.e("discountOfferItems", Functions.jsonString(discountOfferItems));
                            totalDiscount = 0;
                            filteredDiscounts.clear();
                            linearOfferDiscountsDetails.removeAllViews();
                            txtTotalPayableAmount.setText(getString(R.string.ruppee) + " " + String.valueOf(paymentProcessResponse.PaymentProcess.Data.get(0).PayableAmount));

                            if (isOfferSelected) {
                                for (int j = 0; j < discountOfferItems.size(); j++) {
                                    for (int i = 0; i < jobsList.size(); i++) {
                                        if (discountOfferItems.get(j).ServiceName.equals(jobsList.get(i))) {
                                            filteredDiscounts.add(discountOfferItems.get(j));
                                        }
                                    }
                                    if (discountOfferItems.get(j).ServiceName.equals("Invoice")) {
                                        filteredDiscounts.add(discountOfferItems.get(j));
                                    }
                                }
                                Log.e("avail selected offers", Functions.jsonString(filteredDiscounts));
                                offerId = filteredDiscounts.get(0).AvailOfferID;

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
                            } else {
                                offerId = 0;
                                setPaymentDetailsUI(paymentProcessResponse);
                            }

                            Log.e("offerId", offerId + " ##");
                        }
                    });
                } else {
                    txtAvaildisc.setVisibility(View.GONE);
                    adminOffersRecyclerView.setVisibility(View.GONE);
                    discountTitle.setVisibility(View.GONE);
                }
            } else {
                txtAvaildisc.setVisibility(View.GONE);
                serviceDetailsCardView.setVisibility(View.GONE);
                adminOffersRecyclerView.setVisibility(View.GONE);
                discountTitle.setVisibility(View.GONE);
                btnContinuePayment.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}
