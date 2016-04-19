package com.rovertech.utomo.app.tiles.carItem;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class CarItemTile extends LinearLayout {

    Context context;
    private View parentView;
    private CardView carCardView;

    private TextView txtCarName, txtVehicleNo, txtOdometerValue, txtDelete;
    private ImageView imgCar;

    private CarPojo carPojo;


    public CarItemTile(Context context) {
        super(context);
    }

    public CarItemTile(Context context, View view) {
        super(context);
        this.context = context;
        this.parentView = view;
        init();
    }

    private void init() {

        findViewById();

        setTypeface();

    }

    private void setTypeface() {
        txtCarName.setTypeface(Functions.getBoldFont(context));
        txtVehicleNo.setTypeface(Functions.getRegularFont(context));
        txtOdometerValue.setTypeface(Functions.getRegularFont(context));
        txtDelete.setTypeface(Functions.getBoldFont(context));
    }

    private void findViewById() {
        txtDelete = (TextView) parentView.findViewById(R.id.txtDelete);
        carCardView = (CardView) parentView.findViewById(R.id.carCardView);
        txtCarName = (TextView) parentView.findViewById(R.id.txtCarName);
        imgCar = (ImageView) parentView.findViewById(R.id.imgCar);
        txtVehicleNo = (TextView) parentView.findViewById(R.id.txtVehicleNo);
        txtOdometerValue = (TextView) parentView.findViewById(R.id.txtOdometerValue);
    }

    public void setDetails(final CarPojo carPojo) {
        this.carPojo = carPojo;
        txtCarName.setText(String.format("%s %s", carPojo.Make, carPojo.Model));
        txtVehicleNo.setText(carPojo.VehicleNo);
        txtOdometerValue.setText(String.format("%s", carPojo.TravelledKM));

        if (carPojo.CarImage == null || carPojo.CarImage.equals("")) {
            imgCar.setImageResource(R.drawable.car);
        } else {
            Glide.with(context).load(carPojo.CarImage).placeholder(R.drawable.car).centerCrop().into(imgCar);
        }

    }

}
