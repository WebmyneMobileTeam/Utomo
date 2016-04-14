package com.rovertech.utomo.app.tiles.sponsoredCenter;

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
import com.rovertech.utomo.app.home.car.model.SponsoredCenter;
import com.rovertech.utomo.app.main.centreDetail.CentreDetailsActivity;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class SponsoredCenterTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtCenterName, txtExpertise;
    private ImageView imgCenter;
    private CardView sponsoredCardView;

    public SponsoredCenterTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SponsoredCenterTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_sponsored, this, true);

        findViewById();

        setTypeface();

        sponsoredCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(context, CentreDetailsActivity.class);
            }
        });

    }

    private void setTypeface() {
        txtCenterName.setTypeface(Functions.getBoldFont(context));
        txtExpertise.setTypeface(Functions.getNormalFont(context));
    }

    private void findViewById() {
        txtCenterName = (TextView) parentView.findViewById(R.id.txtCenterName);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtExpertise = (TextView) parentView.findViewById(R.id.txtExpertise);
        sponsoredCardView = (CardView) parentView.findViewById(R.id.sponsoredCardView);

    }


    public void setDetails(SponsoredCenter sponsoredCenter) {
        txtCenterName.setText(sponsoredCenter.ServiceCentreName);

        if (!sponsoredCenter.Expertise.equals(""))
            txtExpertise.setText(String.format("Expert in %s", sponsoredCenter.Expertise));
        else
            txtExpertise.setVisibility(GONE);

        if (sponsoredCenter.ImageName != null && sponsoredCenter.ImageName.length() > 0)
            Glide.with(context).load(sponsoredCenter.ImageName).into(imgCenter);
    }
}
