package com.rovertech.utomo.app.tiles.carItem;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.AddCarActivity;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class CarItemTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;
    private CardView carCardView;

    private TextView txtCarName, txtAcceleration, txtGear, txtFuel;
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

        carCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(context, AddCarActivity.class);
            }
        });

    }

    private void setTypeface() {
        txtCarName.setTypeface(Functions.getBoldFont(context));
        txtAcceleration.setTypeface(Functions.getNormalFont(context));
        txtGear.setTypeface(Functions.getNormalFont(context));
        txtFuel.setTypeface(Functions.getNormalFont(context));

    }

    private void findViewById() {
        carCardView = (CardView) parentView.findViewById(R.id.carCardView);
        txtCarName = (TextView) parentView.findViewById(R.id.txtCarName);
        imgCar = (ImageView) parentView.findViewById(R.id.imgCar);
        txtAcceleration = (TextView) parentView.findViewById(R.id.txtAcceleration);
        txtGear = (TextView) parentView.findViewById(R.id.txtGear);
        txtFuel = (TextView) parentView.findViewById(R.id.txtFuel);

    }

    public void setDetails() {

    }
}
