package com.rovertech.utomo.app.widget.tiles.performance;

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
 * Created by sagartahelyani on 10-03-2016.
 */
public class PerformanceTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle, txtInfo, txtDetails, txtServiceStatus;
    private ImageView imgCenter;

    public PerformanceTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PerformanceTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_current_service, this, true);

        findViewById();

        setTypeface();

       /* Glide.with(context).load(R.drawable.slider1).asBitmap().into(new BitmapImageViewTarget(imgCenter) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgCenter.setImageDrawable(circularBitmapDrawable);
            }
        });*/

    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
        txtInfo.setTypeface(Functions.getNormalFont(context));
        txtDetails.setTypeface(Functions.getNormalFont(context));
        txtServiceStatus.setTypeface(Functions.getNormalFont(context));
    }

    private void findViewById() {
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtInfo = (TextView) parentView.findViewById(R.id.txtInfo);
        txtDetails = (TextView) parentView.findViewById(R.id.txtDetails);
        txtServiceStatus = (TextView) parentView.findViewById(R.id.txtServiceStatus);
    }
}
