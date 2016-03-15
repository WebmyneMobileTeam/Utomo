package com.rovertech.utomo.app.tiles.sponsoredCenter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class SponsoredCenterSet extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle, txtInfo, txtDetails;
    private ImageView imgArrow;
    private ExpandableLayout expandLayout;
    private LinearLayout expandRecommendedClick;

    public SponsoredCenterSet(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SponsoredCenterSet(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_sponsored_set, this, true);

        findViewById();

        setTypeface();

    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
        txtInfo.setTypeface(Functions.getNormalFont(context));
        txtDetails.setTypeface(Functions.getNormalFont(context));

    }

    private void findViewById() {
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgArrow = (ImageView) parentView.findViewById(R.id.imgArrow);
        txtInfo = (TextView) parentView.findViewById(R.id.txtInfo);
        txtDetails = (TextView) parentView.findViewById(R.id.txtDetails);
        expandRecommendedClick = (LinearLayout) findViewById(R.id.expandRecommendedClick);
        expandLayout = (ExpandableLayout) findViewById(R.id.expandLayout);

        expandRecommendedClick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandLayout.isExpanded()) {
                    Functions.antirotateViewClockwise(imgArrow);
                } else {
                    Functions.rotateViewClockwise(imgArrow);
                }
                expandLayout.toggle();
            }
        });

    }
}
