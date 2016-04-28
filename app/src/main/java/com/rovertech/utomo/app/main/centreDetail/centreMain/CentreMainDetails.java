package com.rovertech.utomo.app.main.centreDetail.centreMain;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.LoginActivity;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.booking.BookingActivity;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.widget.FlowLayout;
import com.rovertech.utomo.app.widget.serviceTypeChip.ServiceChip;

/**
 * Created by sagartahelyani on 16-03-2016.
 */
public class CentreMainDetails extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;
    private View parentView;
    private TextView txtCentreName, txtCentreAddress, txtCentreInfo;
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
        //btnBook = (Button) parentView.findViewById(R.id.btnBook);
        ratingBar = (RatingBar) parentView.findViewById(R.id.ratingBar);
        serviceFlowLayout = (FlowLayout) parentView.findViewById(R.id.serviceFlowLayout);
        serviceFlowLayout.setOrientation(HORIZONTAL);

        setTypeface();

        serviceFlowLayout.removeAllViews();
        serviceFlowLayout.invalidate();

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    private void setTypeface() {
        txtCentreAddress.setTypeface(Functions.getRegularFont(context));
        txtCentreInfo.setTypeface(Functions.getRegularFont(context));
        txtCentreName.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        //btnBook.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);

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

      /*  btnBook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                PrefUtils.setCenterSelected(context, centreDetailPojo);

                if (PrefUtils.isUserLoggedIn(context)) {
                    Intent intent = new Intent(context, BookingActivity.class);
                    intent.putExtra(IntentConstant.BOOKING_PAGE, AppConstant.FROM_SC_LIST);
                    Functions.fireIntent(context, intent);

                } else {
                    PrefUtils.setRedirectLogin(context, AppConstant.FROM_SC);
                   // Toast.makeText(context, "sd " + PrefUtils.getRedirectLogin(context), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    Functions.fireIntent(context, intent);
                }
            }
        });*/
    }

}
