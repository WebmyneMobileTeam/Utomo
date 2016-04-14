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
import com.rovertech.utomo.app.home.car.model.SponsoredCenter;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class SponsoredCenterSet extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle;
    private ImageView imgArrow;
    private ExpandableLayout expandLayout;
    private LinearLayout expandRecommendedClick, sponsoredLinearLayout;

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
    }

    private void findViewById() {
        sponsoredLinearLayout = (LinearLayout) parentView.findViewById(R.id.sponsoredLinearLayout);
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgArrow = (ImageView) parentView.findViewById(R.id.imgArrow);
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

    public void setCenterList(ArrayList<SponsoredCenter> lstReferTile) {

        for (int i = 0; i < lstReferTile.size(); i++) {
            SponsoredCenterTile item = new SponsoredCenterTile(context);
            item.setDetails(lstReferTile.get(i));
            sponsoredLinearLayout.addView(item);
        }
    }
}
