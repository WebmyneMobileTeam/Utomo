package com.rovertech.utomo.app.tiles.sponsoredCenter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centreDetail.CentreDetailsActivity;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class SponsoredCenterTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle, txtInfo, txtDetails;
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
        txtTitle.setTypeface(Functions.getBoldFont(context));
        txtInfo.setTypeface(Functions.getNormalFont(context));
        txtDetails.setTypeface(Functions.getNormalFont(context));

    }

    private void findViewById() {
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtInfo = (TextView) parentView.findViewById(R.id.txtInfo);
        txtDetails = (TextView) parentView.findViewById(R.id.txtDetails);
        sponsoredCardView = (CardView) parentView.findViewById(R.id.sponsoredCardView);

    }
}
