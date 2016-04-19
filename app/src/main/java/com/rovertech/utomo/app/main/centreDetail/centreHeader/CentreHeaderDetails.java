package com.rovertech.utomo.app.main.centreDetail.centreHeader;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centreDetail.ImageAdapter;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagartahelyani on 16-03-2016.
 */
public class CentreHeaderDetails extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;
    private View parentView;
    private ViewPager viewPager;
    private ImageAdapter adapter;
    private List<String> images = new ArrayList<>();
    private TextView txtReviews, txtDistance, txtCentreName;
    private ImageView imgLeft, imgRight, imgOffer, imgCall, imgStar, imgLocation;

    public CentreHeaderDetails(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CentreHeaderDetails(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_centre_header, this, true);

        viewPager = (ViewPager) parentView.findViewById(R.id.viewPager);
        initPager();

        txtReviews = (TextView) parentView.findViewById(R.id.txtReviews);
        txtDistance = (TextView) parentView.findViewById(R.id.txtDistance);
        txtCentreName = (TextView) parentView.findViewById(R.id.txtCentreName);
        imgLeft = (ImageView) parentView.findViewById(R.id.imgLeft);
        imgRight = (ImageView) parentView.findViewById(R.id.imgRight);
        imgOffer = (ImageView) parentView.findViewById(R.id.imgOffer);
        imgCall = (ImageView) parentView.findViewById(R.id.imgCall);
        imgStar = (ImageView) parentView.findViewById(R.id.imgStar);
        imgLocation = (ImageView) parentView.findViewById(R.id.imgLocation);

        setTypeface();

        imgLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        imgRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
    }

    private void setTypeface() {
        txtReviews.setTypeface(Functions.getRegularFont(context));
        txtDistance.setTypeface(Functions.getBoldFont(context));
        txtCentreName.setTypeface(Functions.getBoldFont(context));

    }

    private void initPager() {
        adapter = new ImageAdapter(context, images);
        viewPager.setAdapter(adapter);
    }

    public void setDetails(final FetchServiceCentreDetailPojo centreDetailPojo) {


        if (centreDetailPojo.lstServiceCentreImage.size() > 0) {

            for (int i = 0; i < centreDetailPojo.lstServiceCentreImage.size(); i++) {
                images.add(centreDetailPojo.lstServiceCentreImage.get(i).ImageName);
            }
            adapter.notifyDataSetChanged();
        } else {

            imgLeft.setVisibility(GONE);
            imgRight.setVisibility(GONE);
        }

        imgOffer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgStar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imgLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (centreDetailPojo.Lattitude != 0.0 && centreDetailPojo.Longitude != 0.0) {

                    Functions.openInMap(context, centreDetailPojo.Lattitude, centreDetailPojo.Longitude, centreDetailPojo.ServiceCentreName);
                } else {
                    Functions.showDialog(context, "Currently Location unavailable", null);
                }

            }
        });


    }
}
