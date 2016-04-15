package com.rovertech.utomo.app.main.centreDetail.centreMain;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.main.booking.BookingActivity;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.widget.FlowLayout;
import com.rovertech.utomo.app.widget.serviceTypeChip.ServiceChip;

/**
 * Created by sagartahelyani on 16-03-2016.
 */
public class    CentreMainDetails extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;
    private View parentView;
    private TextView txtCentreName, txtCentreAddress, txtCentreInfo, txtStartPrice;
    private Button btnBook;
    private RatingBar ratingBar;
    private FlowLayout serviceFlowLayout;
    LinearLayout.LayoutParams params;

    public CentreMainDetails(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CentreMainDetails(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_centre_main, this, true);

        txtCentreName = (TextView) parentView.findViewById(R.id.txtCentreName);
        txtCentreAddress = (TextView) parentView.findViewById(R.id.txtCentreAddress);
        txtCentreInfo = (TextView) parentView.findViewById(R.id.txtCentreInfo);
        txtStartPrice = (TextView) parentView.findViewById(R.id.txtStartPrice);
        btnBook = (Button) parentView.findViewById(R.id.btnBook);
        ratingBar = (RatingBar) parentView.findViewById(R.id.ratingBar);
        serviceFlowLayout = (FlowLayout) parentView.findViewById(R.id.serviceFlowLayout);
        serviceFlowLayout.setOrientation(HORIZONTAL);

        setTypeface();

        txtStartPrice.setText(String.format("From %s200", context.getResources().getString(R.string.Rs)));

        serviceFlowLayout.removeAllViews();
        serviceFlowLayout.invalidate();

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

       /* for (int i = 0; i < 4; i++) {
            ServiceChip serviceChip = new ServiceChip(context);
            serviceFlowLayout.addView(serviceChip, params);
        }*/


    }

    private void setTypeface() {
        txtCentreAddress.setTypeface(Functions.getNormalFont(context));
        txtCentreInfo.setTypeface(Functions.getNormalFont(context));
        txtStartPrice.setTypeface(Functions.getBoldFont(context));
        txtCentreName.setTypeface(Functions.getBoldFont(context));
        btnBook.setTypeface(Functions.getBoldFont(context));

        LayerDrawable ratingDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
        ratingDrawable.getDrawable(2).setColorFilter(ContextCompat.getColor(context, R.color.yellow), PorterDuff.Mode.SRC_ATOP);
    }

    public void setDetails(final FetchServiceCentreDetailPojo centreDetailPojo) {

        txtCentreAddress.setText(String.format("%s", centreDetailPojo.Address1));
        txtCentreName.setText(String.format("%s", centreDetailPojo.ServiceCentreName));
        ratingBar.setRating(centreDetailPojo.Rating);
        txtCentreInfo.setText(String.format("%s", centreDetailPojo.Expertise));
        if (centreDetailPojo.IsBodyWash) {
            ServiceChip serviceChip = new ServiceChip(context, "Body Shop");
            serviceFlowLayout.addView(serviceChip, params);
        }

        if (centreDetailPojo.IsPickupDrop) {
            ServiceChip serviceChip = new ServiceChip(context, "Pickup-Drop off");
            serviceFlowLayout.addView(serviceChip, params);
        }

        btnBook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra(IntentConstant.FetchServiceCentreDetailPojo, centreDetailPojo);
                Functions.fireIntent(context, intent);
            }
        });
    }

}
