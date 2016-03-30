package com.rovertech.utomo.app.main.serviceDetail.serviceMain;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public class ServiceMainDetails extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;
    private View parentView;

    private TextView txtBookingDate, txtDeliveryDate, txtStatusDetails, txtServiceDetails, txtStatusTitle, txtBookingTitle, txtServiceTitle;

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

        txtBookingDate = (TextView) parentView.findViewById(R.id.txtBookingDate);
        txtDeliveryDate = (TextView) parentView.findViewById(R.id.txtDeliveryDate);
        txtServiceDetails = (TextView) parentView.findViewById(R.id.txtServiceDetails);
        txtStatusDetails = (TextView) parentView.findViewById(R.id.txtStatusDetails);
        txtServiceDetails = (TextView) parentView.findViewById(R.id.txtServiceDetails);
        txtStatusTitle = (TextView) parentView.findViewById(R.id.txtStatusTitle);
        txtBookingTitle = (TextView) parentView.findViewById(R.id.txtBookingTitle);
        txtServiceTitle = (TextView) parentView.findViewById(R.id.txtServiceTitle);

        setTypeface();
    }

    private void setTypeface() {
        txtBookingDate.setTypeface(Functions.getNormalFont(context));
        txtDeliveryDate.setTypeface(Functions.getNormalFont(context));
        txtStatusDetails.setTypeface(Functions.getNormalFont(context));
        txtServiceDetails.setTypeface(Functions.getNormalFont(context));
        txtStatusTitle.setTypeface(Functions.getBoldFont(context));
        txtBookingTitle.setTypeface(Functions.getBoldFont(context));
        txtServiceTitle.setTypeface(Functions.getBoldFont(context));
    }
}
