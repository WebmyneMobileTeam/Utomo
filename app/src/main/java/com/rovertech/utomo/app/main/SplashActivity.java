package com.rovertech.utomo.app.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.AddCarActivity;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.main.startup.StartupActivity;

/**
 * Created by priyasindkar on 24-02-2016.
 */
public class SplashActivity extends AppCompatActivity {
    private TextView title;
    private ImageView car;

    private boolean isLoggedIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isLoggedIn = PrefUtils.isUserLoggedIn(this);

        if (PrefUtils.isSplash(this)) {
            navigate();
            return;
        }

        setContentView(R.layout.activity_splash);

        init();

        ObjectAnimator animX = ObjectAnimator.ofFloat(title, "scaleX", 0.5F, 1F);
        ObjectAnimator animY = ObjectAnimator.ofFloat(title, "scaleY", 0.5F, 1F);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(title, "alpha", 0.5F, 1F);
        animX.setDuration(5000);
        animY.setDuration(5000);
        alpha.setDuration(5000);

        Animator titleAnimation = ObjectAnimator.ofFloat(title, "rotationX", 0, 1080);
        Animator car2Animation = ObjectAnimator.ofFloat(car, "translationX", 0, 2000);

        titleAnimation.setInterpolator(new BounceInterpolator());
        titleAnimation.setStartDelay(2500);
        titleAnimation.setDuration(2000);
        car2Animation.setDuration(5000);
        car2Animation.setInterpolator(new AnticipateOvershootInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.playTogether(car2Animation, animX, animY, alpha, titleAnimation);

        if (!PrefUtils.isSplash(this)) {
            set.start();
        }

        car2Animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                PrefUtils.setSplash(SplashActivity.this, true);

                navigate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void navigate() {

        if (isLoggedIn) {

            if (PrefUtils.isCarAdded(this)) {
                callDrawer();

            } else {

                if (PrefUtils.getUserFullProfileDetails(this).VehicleCount == 0) {
                    callAddCar();
                } else {
                    callDrawer();
                }
            }

        } else {
            Functions.fireIntent(SplashActivity.this, StartupActivity.class);
        }

        finish();
    }

    private void callDrawer() {
        Intent intent = new Intent(SplashActivity.this, DrawerActivity.class);
        intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.HOME_FRAGMENT);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    private void callAddCar() {
        Intent intent = new Intent(SplashActivity.this, AddCarActivity.class);
        intent.putExtra(AppConstant.SKIP, true);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    private void init() {
        title = (TextView) findViewById(R.id.title);
        car = (ImageView) findViewById(R.id.car2);
    }
}
