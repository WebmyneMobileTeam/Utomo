package com.rovertech.utomo.app.tiles.odometer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
    private TextView txtReset, txtDone;
    private boolean isChange = false;

    Animator fadeIn, fadeOut, reverseFadeIn, reverseFadeOut;
    AnimatorSet loginSet = new AnimatorSet();
    AnimatorSet reverseLoginSet = new AnimatorSet();

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

        // Regular Animation
        fadeIn = ObjectAnimator.ofFloat(txtReset, "translationY", 0, 150);
        fadeOut = ObjectAnimator.ofFloat(txtDone, "translationY", -80, 0);

        fadeIn.setDuration(900);
        fadeOut.setDuration(600);

        loginSet.playTogether(fadeIn, fadeOut);

        loginSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                txtDone.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                txtReset.setVisibility(GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // Reverse Animation
        reverseFadeIn = ObjectAnimator.ofFloat(txtReset, "translationY", 100, 0);
        reverseFadeOut = ObjectAnimator.ofFloat(txtDone, "translationY", 0, -150);

        reverseFadeIn.setDuration(600);
        reverseFadeOut.setDuration(800);

        reverseLoginSet.playTogether(reverseFadeIn, reverseFadeOut);

        reverseLoginSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                txtReset.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                txtDone.setVisibility(GONE);
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
        txtReset = (TextView) parentView.findViewById(R.id.txtReset);
        txtDone = (TextView) parentView.findViewById(R.id.txtDone);
        odometer = (Odometer) parentView.findViewById(R.id.odometer);

        txtReset.setTypeface(Functions.getBoldFont(context));
        txtDone.setTypeface(Functions.getBoldFont(context));

        txtReset.setOnClickListener(this);
        txtDone.setOnClickListener(this);

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
            case R.id.txtReset:
                presenter.reset(context);
                break;

            case R.id.txtDone:
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
        isChange = false;
        reverseLoginSet.start();

    }
}
