package com.rovertech.utomo.app.tiles;

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
public class CurrentServiceTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle, txtBookingDate, txtCenterName, txtCenterAddress, txtServiceStatus;
    private ImageView imgCenter;

    public CurrentServiceTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CurrentServiceTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_current_service, this, true);

        findViewById();

        setTypeface();
    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
        txtBookingDate.setTypeface(Functions.getNormalFont(context));
        txtCenterName.setTypeface(Functions.getNormalFont(context));
        txtCenterAddress.setTypeface(Functions.getNormalFont(context));
        txtServiceStatus.setTypeface(Functions.getBoldFont(context));
    }

    private void findViewById() {
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgCenter = (ImageView) parentView.findViewById(R.id.imgCenter);
        txtBookingDate = (TextView) parentView.findViewById(R.id.txtBookingDate);
        txtCenterName = (TextView) parentView.findViewById(R.id.txtCenterName);
        txtCenterAddress = (TextView) parentView.findViewById(R.id.txtCenterAddress);
        txtServiceStatus = (TextView) parentView.findViewById(R.id.txtServiceStatus);
    }

    public View getParentView() {
        return parentView;
    }
}
