package com.rovertech.utomo.app.tiles;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.widget.progress.ProgressLayout;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class HealthMeterTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private ProgressLayout progressLayout;
    private TextView txtTitle, txtPercent;

    public HealthMeterTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HealthMeterTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_healthmeter, this, true);

        findViewById();

        setTypeface();

    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
        txtPercent.setTypeface(Typeface.createFromAsset(context.getAssets(), "digit_font.ttf"), Typeface.BOLD);
    }

    private void findViewById() {
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        txtPercent = (TextView) parentView.findViewById(R.id.txtPercent);
        progressLayout = (ProgressLayout) parentView.findViewById(R.id.progressLayout);
        progressLayout.setCurrentProgress(40);

    }

    public void setCarHealth(String carHealth) {
        txtPercent.setText(String.format("%s %s", carHealth, "%"));

        float f = Float.parseFloat(carHealth);
        progressLayout.setCurrentProgress((int) f);
        progressLayout.setLoadedColor(Functions.getColor(context, f));
    }
}
