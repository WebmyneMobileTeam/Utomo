package com.rovertech.utomo.app.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.invoice.model.PaymentApiResponse;
import com.rovertech.utomo.app.invoice.model.PaymentJobCardDetailsModel;
import com.rovertech.utomo.app.main.drawer.DrawerActivityRevised;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView txtCustomTitle, txtOfferLabel;
    private String jsonString;
    private PaymentApiResponse apiResponse;
    private LinearLayout linearServiceDetails, linearDiscount;
    private View linear_line;
    private TextView txtTotalLabel, txtTotalAmount, txtTotalPayableLabel, txtTotalPayableAmount, txtCenterTitle,
            txtDiscountLabel, txtDiscountAmount, txtSCDiscountOfferLabel, txtSCDiscountOfferAmount;
    private Button btnHome;
    private View scOfferDiscountItem;
    private double SCOfferDiscount;
    private String selectedOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        getIntentData();

        init();
    }

    private void getIntentData() {
        jsonString = getIntent().getStringExtra("payment");
        selectedOffer = getIntent().getStringExtra("selectedOffer");
        apiResponse = new Gson().fromJson(jsonString, PaymentApiResponse.class);

        SCOfferDiscount = getIntent().getDoubleExtra("SCOfferDiscount", 0.0);

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDashboard = new Intent(OrderDetailsActivity.this, DrawerActivityRevised.class);
                iDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(iDashboard);
                finish();
            }
        });

        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        txtCustomTitle.setText("Order Details");

        btnHome = (Button) findViewById(R.id.btnHome);
        linear_line = findViewById(R.id.linear_line);
        linearServiceDetails = (LinearLayout) findViewById(R.id.linearServiceDetails);
        linearDiscount = (LinearLayout) findViewById(R.id.linearDiscount);
        txtTotalLabel = (TextView) findViewById(R.id.txtTotalLabel);
        txtTotalAmount = (TextView) findViewById(R.id.txtTotalAmount);
        txtTotalPayableLabel = (TextView) findViewById(R.id.txtTotalPayableLabel);
        txtTotalPayableAmount = (TextView) findViewById(R.id.txtTotalPayableAmount);
        txtCenterTitle = (TextView) findViewById(R.id.txtCenterTitle);
        txtDiscountLabel = (TextView) findViewById(R.id.txtDiscountLabel);
        txtDiscountAmount = (TextView) findViewById(R.id.txtDiscountAmount);
        txtOfferLabel = (TextView) findViewById(R.id.txtOfferLabel);

        txtSCDiscountOfferLabel = (TextView) findViewById(R.id.txtServiceName);
        txtSCDiscountOfferAmount = (TextView) findViewById(R.id.txtServiceAmount);
        scOfferDiscountItem = findViewById(R.id.scOfferDiscountItem);

        txtOfferLabel.setTypeface(Functions.getRegularFont(this));
        btnHome.setTypeface(Functions.getRegularFont(this));
        txtCustomTitle.setTypeface(Functions.getRegularFont(this));
        txtTotalLabel.setTypeface(Functions.getRegularFont(this));
        txtTotalAmount.setTypeface(Functions.getRegularFont(this));
        txtTotalPayableLabel.setTypeface(Functions.getRegularFont(this));
        txtTotalPayableAmount.setTypeface(Functions.getRegularFont(this));
        txtCenterTitle.setTypeface(Functions.getRegularFont(this));
        txtDiscountLabel.setTypeface(Functions.getRegularFont(this));
        txtDiscountAmount.setTypeface(Functions.getRegularFont(this));

        setProductDetails();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDashboard = new Intent(OrderDetailsActivity.this, DrawerActivityRevised.class);
                iDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(iDashboard);
                finish();
            }
        });
    }

    private void setProductDetails() {

        txtOfferLabel.setText(String.format("(%s)", selectedOffer));

        final List<PaymentJobCardDetailsModel> serviceJobsList = apiResponse.Payment.Data.get(0).lstJobCardDeatils;

        txtTotalAmount.setText(getString(R.string.ruppee) + " " + apiResponse.Payment.Data.get(0).TotalAmount + "");
        txtTotalPayableAmount.setText(getString(R.string.ruppee) + " " + apiResponse.Payment.Data.get(0).PayableAmount + "");


        if (apiResponse.Payment.Data.get(0).AdminOfferDiscount == 0.0) {
            linearDiscount.setVisibility(View.GONE);
            linear_line.setVisibility(View.GONE);
        } else {
            linearDiscount.setVisibility(View.VISIBLE);
            linear_line.setVisibility(View.VISIBLE);

            txtDiscountAmount.setText(" - " + getString(R.string.ruppee) + " " + apiResponse.Payment.Data.get(0).AdminOfferDiscount + "");
        }

        if (SCOfferDiscount == 0.0) {
            scOfferDiscountItem.setVisibility(View.GONE);
        } else {
            scOfferDiscountItem.setVisibility(View.VISIBLE);
            txtSCDiscountOfferLabel.setText("Service Center Offer Discount: ");
            txtSCDiscountOfferAmount.setText(" - " + getString(R.string.ruppee) + " " + SCOfferDiscount);
            txtSCDiscountOfferAmount.setTextColor(ContextCompat.getColor(this, R.color.button_bg));
            txtSCDiscountOfferLabel.setTextColor(ContextCompat.getColor(this, R.color.button_bg));
            txtSCDiscountOfferAmount.setTypeface(Functions.getRegularFont(this));
            txtSCDiscountOfferLabel.setTypeface(Functions.getRegularFont(this));
        }

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
//                jobsList.add(serviceJobsList.get(i).Diagnosis);
                linearServiceDetails.addView(view);
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent iDashboard = new Intent(OrderDetailsActivity.this, DrawerActivityRevised.class);
        iDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iDashboard);
        finish();
    }
}
