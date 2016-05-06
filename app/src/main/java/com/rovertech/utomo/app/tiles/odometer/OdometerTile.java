package com.rovertech.utomo.app.tiles.odometer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Typeface;
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
    private TextView txtReset, txtDone, txtCancel;
    private boolean isChange = false;
    private String originalOdometer;

    Animator fadeIn, fadeOutDone, fadeOutCancel, reverseFadeIn, reverseFadeOutDone, reverseFadeOutCancel;
    AnimatorSet loginSet = new AnimatorSet();
    AnimatorSet reverseLoginSet = new AnimatorSet();

    OdometerPresenter presenter;

    private onOdometerChangeListener onOdometerChangeListener;

    public void setOnOdometerChangeListener(OdometerTile.onOdometerChangeListener onOdometerChangeListener) {
        this.onOdometerChangeListener = onOdometerChangeListener;
    }

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
        fadeOutDone = ObjectAnimator.ofFloat(txtDone, "translationY", -80, 0);
        fadeOutCancel = ObjectAnimator.ofFloat(txtCancel, "translationY", -80, 0);

        fadeIn.setDuration(900);
        fadeOutDone.setDuration(600);
        fadeOutCancel.setDuration(600);

        loginSet.playTogether(fadeIn, fadeOutDone, fadeOutCancel);

        loginSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                txtDone.setVisibility(VISIBLE);
                txtCancel.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                txtReset.setVisibility(INVISIBLE);

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
        reverseFadeOutDone = ObjectAnimator.ofFloat(txtDone, "translationY", 0, -150);
        reverseFadeOutCancel = ObjectAnimator.ofFloat(txtCancel, "translationY", 0, -150);

        reverseFadeIn.setDuration(600);
        reverseFadeOutDone.setDuration(800);
        reverseFadeOutCancel.setDuration(800);

        reverseLoginSet.playTogether(reverseFadeIn, reverseFadeOutDone, reverseFadeOutCancel);

        reverseLoginSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                txtReset.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                txtDone.setVisibility(INVISIBLE);
                txtCancel.setVisibility(INVISIBLE);
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
        txtTitle.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
    }

    private void findViewById() {
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        txtReset = (TextView) parentView.findViewById(R.id.txtReset);
        txtDone = (TextView) parentView.findViewById(R.id.txtDone);
        txtCancel = (TextView) parentView.findViewById(R.id.txtCancel);
        odometer = (Odometer) parentView.findViewById(R.id.odometer);

        txtReset.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        txtDone.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        txtCancel.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);

        txtReset.setOnClickListener(this);
        txtDone.setOnClickListener(this);
        txtCancel.setOnClickListener(this);

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

            case R.id.txtCancel:
                odometer.setValue(originalOdometer);
                isChange = false;
                reverseLoginSet.start();
                break;
        }
    }

    @Override
    public void resetOdometer() {
        Toast.makeText(context, "Reset", Toast.LENGTH_SHORT).show();
        odometer.reset();
        if (onOdometerChangeListener != null)
            onOdometerChangeListener.onChange("000000");
    }

    @Override
    public void setReading() {
        Toast.makeText(context, "Change Reading", Toast.LENGTH_SHORT).show();
        isChange = false;
        reverseLoginSet.start();
        if (onOdometerChangeListener != null)
            onOdometerChangeListener.onChange(odometer.getValue());

    }

    public void setOdometerReading(String odometerReading) {
        originalOdometer = odometerReading;
        odometer.setValue(odometerReading);
    }

    public interface onOdometerChangeListener {
        public void onChange(String odometer);
    }
}
