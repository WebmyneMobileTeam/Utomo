package com.rovertech.utomo.app.tiles;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.home.car.model.DashboardData;
import com.rovertech.utomo.app.main.serviceDetail.ServiceDetailsActivity;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class CurrentServiceTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtBookingId, txtBookingDate, txtCenterName, txtServiceStatus;
    private ImageView imgCenter;
    private CardView currentCardView;

    public CurrentServiceTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CurrentServiceTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_current_service_revised, this, true);

        findViewById();

        setTypeface();

        currentCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(context, ServiceDetailsActivity.class);
            }
        });
    }

    private void setTypeface() {
        txtBookingId.setTypeface(Functions.getBoldFont(context));
        txtBookingDate.setTypeface(Functions.getNormalFont(context));
        txtCenterName.setTypeface(Functions.getBoldFont(context));
        txtServiceStatus.setTypeface(Functions.getBoldFont(context));
    }

    private void findViewById() {
        txtBookingId = (TextView) parentView.findViewById(R.id.txtBookingId);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtBookingDate = (TextView) parentView.findViewById(R.id.txtBookingDate);
        txtCenterName = (TextView) parentView.findViewById(R.id.txtCenterName);
        txtServiceStatus = (TextView) parentView.findViewById(R.id.txtServiceStatus);
        currentCardView = (CardView) parentView.findViewById(R.id.currentCardView);
    }

    public View getParentView() {
        return parentView;
    }

    public void setDetails(DashboardData data) {
        txtBookingId.setText(String.format("Booking ID: %d", data.BookingID));
        Glide.with(context).load(data.SCImageName).into(imgCenter);
        txtCenterName.setText(data.ServiceCentreName);
        txtBookingDate.setText(data.CreatedDate);
        txtServiceStatus.setText(data.Status);
    }
}
