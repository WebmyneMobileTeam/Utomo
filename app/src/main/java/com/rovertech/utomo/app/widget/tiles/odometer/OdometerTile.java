package com.rovertech.utomo.app.widget.tiles.odometer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.widget.Odometer;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class OdometerTile extends LinearLayout implements OdometerView, View.OnClickListener {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle;
    private Odometer odometer;
    private ImageView imgReset, imgDone;
    private boolean isChange = false;

    Animator fadeIn, fadeOut;
    AnimatorSet loginSet = new AnimatorSet();

    OdometerPresenter presenter;

    public OdometerTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OdometerTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_odometer, this, true);

        presenter = new OdometerPresenterImpl(this);

        findViewById();

        setTypeface();

        initAnimation();

    }

    private void initAnimation() {

        fadeIn = ObjectAnimator.ofFloat(imgReset, "translationY", 0, 150);
        fadeOut = ObjectAnimator.ofFloat(imgDone, "translationY", -70, 0);

        fadeIn.setDuration(700);
        fadeOut.setDuration(500);

        loginSet.playTogether(fadeIn, fadeOut);

        loginSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imgDone.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imgReset.setVisibility(GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
    }

    private void findViewById() {
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgReset = (ImageView) parentView.findViewById(R.id.imgReset);
        imgDone = (ImageView) parentView.findViewById(R.id.imgDone);
        odometer = (Odometer) parentView.findViewById(R.id.odometer);

        imgReset.setOnClickListener(this);
        imgDone.setOnClickListener(this);

        odometer.setOnOdometerChangeListener(new Odometer.onOdometerChangeListener() {
            @Override
            public void readingChange() {

                if (!isChange)
                    loginSet.start();
                isChange = true;

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgReset:
                presenter.reset(context);
                break;

            case R.id.imgDone:
                presenter.done(context);
                break;
        }
    }

    @Override
    public void resetOdometer() {
        Toast.makeText(context, "Reset", Toast.LENGTH_SHORT).show();
        odometer.reset();
    }

    @Override
    public void setReading() {
        Toast.makeText(context, "Change Reading", Toast.LENGTH_SHORT).show();
    }
}
