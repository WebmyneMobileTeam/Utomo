package com.rovertech.utomo.app.tiles.carItem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.AddCarActivity;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class CarItemTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;
    private CardView carCardView;

    private TextView txtCarName, txtVehicleNo, txtOdometerValue;
    private ImageView imgCar;

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
        txtVehicleNo.setTypeface(Functions.getNormalFont(context));
        txtOdometerValue.setTypeface(Functions.getNormalFont(context));

    }

    private void findViewById() {
        carCardView = (CardView) parentView.findViewById(R.id.carCardView);
        txtCarName = (TextView) parentView.findViewById(R.id.txtCarName);
        imgCar = (ImageView) parentView.findViewById(R.id.imgCar);
        txtVehicleNo = (TextView) parentView.findViewById(R.id.txtVehicleNo);
        txtOdometerValue = (TextView) parentView.findViewById(R.id.txtOdometerValue);
    }

    public void setDetails(final CarPojo carPojo) {
        txtCarName.setText(String.format("%s %s", carPojo.Make, carPojo.Model));
        txtVehicleNo.setText(carPojo.VehicleNo);
        txtOdometerValue.setText(String.format("%s", carPojo.TravelledKM));

        if (carPojo.CarImage == null || carPojo.CarImage.equals("")) {
            imgCar.setImageResource(R.drawable.car);
        } else {
            Glide.with(context).load(carPojo.CarImage).placeholder(R.drawable.car).centerCrop().into(imgCar);
        }

        carCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.showToast(context, "To be continued..");
                /*Intent addCarIntent = new Intent(context, AddCarActivity.class);
                addCarIntent.putExtra(AppConstant.SKIP, false);
                addCarIntent.putExtra(AppConstant.VEHICLE_ID, carPojo.VehicleID);
                context.startActivity(addCarIntent);*/

            }
        });
    }
}
