package com.rovertech.utomo.app.main.centreDetail.centreHeader;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

/**
 * Created by sagartahelyani on 16-03-2016.
 */
public class CentreHeaderDetails extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;
    private View parentView;
    private ViewPager viewPager;
    private ImageAdapter adapter;
    private int[] images = new int[]{R.drawable.slider1, R.drawable.slider2, R.drawable.slider3,
            R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
    private TextView txtReviews, txtDistance, txtCentreName;
    private ImageView imgLeft, imgRight;

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
        txtReviews.setTypeface(Functions.getNormalFont(context));
        txtDistance.setTypeface(Functions.getBoldFont(context));
        txtCentreName.setTypeface(Functions.getBoldFont(context));

    }

    private void initPager() {
        adapter = new ImageAdapter(context, images);
        viewPager.setAdapter(adapter);
    }
}
