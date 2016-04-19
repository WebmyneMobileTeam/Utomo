package com.rovertech.utomo.app.main.serviceDetail.serviceMain;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.serviceDetail.model.UserBookingData;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public class ServiceMainDetails extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;
    private View parentView;

    private TextView txtCurrentStatus, txtBookingDate, txtDeliveryDate, txtServiceDetails, txtBookingTitle, txtServiceTitle;

    public ServiceMainDetails(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ServiceMainDetails(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_service_main, this, true);

        txtCurrentStatus = (TextView) parentView.findViewById(R.id.txtCurrentStatus);
        txtBookingDate = (TextView) parentView.findViewById(R.id.txtBookingDate);
        txtDeliveryDate = (TextView) parentView.findViewById(R.id.txtDeliveryDate);
        txtServiceDetails = (TextView) parentView.findViewById(R.id.txtServiceDetails);
        txtServiceDetails = (TextView) parentView.findViewById(R.id.txtServiceDetails);
        txtBookingTitle = (TextView) parentView.findViewById(R.id.txtBookingTitle);
        txtServiceTitle = (TextView) parentView.findViewById(R.id.txtServiceTitle);

        setTypeface();
    }

    private void setTypeface() {

        txtBookingDate.setTypeface(Functions.getRegularFont(context));
        txtDeliveryDate.setTypeface(Functions.getRegularFont(context));
        txtServiceDetails.setTypeface(Functions.getRegularFont(context));
        txtBookingTitle.setTypeface(Functions.getBoldFont(context));
        txtServiceTitle.setTypeface(Functions.getBoldFont(context));
        txtCurrentStatus.setTypeface(Functions.getBoldFont(context));
    }

    public void setMainDetails(UserBookingData userBookingData) {

        txtCurrentStatus.setText(String.format("Current Status: %s", userBookingData.Status));

        txtBookingDate.setText(String.format("Booking On: %s", userBookingData.CreatedDate));

        if (userBookingData.IsCarDelivered)
            txtDeliveryDate.setText(String.format("Delivered On: %s", userBookingData.DeliveredDate));
        else
            txtDeliveryDate.setVisibility(GONE);

        if (userBookingData.Description.equals(""))
            txtServiceDetails.setText("No Description");
        else
            txtServiceDetails.setText(userBookingData.Description);
    }
}
