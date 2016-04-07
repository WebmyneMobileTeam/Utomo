package com.rovertech.utomo.app.main.serviceDetail.serviceHeader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public class ServiceHeaderDetails extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;
    private View parentView;

    private TextView txtCentreName, txtRating, txtReviews;
    private ImageView imgServiceCentre;

    public ServiceHeaderDetails(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ServiceHeaderDetails(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_service_header, this, true);

        txtCentreName = (TextView) parentView.findViewById(R.id.txtCentreName);
        txtRating = (TextView) parentView.findViewById(R.id.txtRating);
        txtReviews = (TextView) parentView.findViewById(R.id.txtReviews);
        imgServiceCentre = (ImageView) parentView.findViewById(R.id.imgServiceCentre);

        setTypeface();
    }

    private void setTypeface() {
        txtCentreName.setTypeface(Functions.getBoldFont(context));
        txtRating.setTypeface(Functions.getBoldFont(context));
        txtReviews.setTypeface(Functions.getBoldFont(context));
    }
}
