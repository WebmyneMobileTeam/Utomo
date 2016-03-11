package com.rovertech.utomo.app.main;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.Prefs;
import com.rovertech.utomo.app.main.startup.StartupActivity;

public class SplashActivity extends AppCompatActivity {

    private boolean isFirstTime;
    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        isFirstTime = Prefs.with(this).getBoolean(AppConstant.FIRST_TIME, false);
        isLoggedIn = Prefs.with(this).getBoolean(AppConstant.LOGGED_IN, false);

        if (isFirstTime) {
            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Prefs.with(SplashActivity.this).save(AppConstant.FIRST_TIME, true);
                    Functions.fireIntent(SplashActivity.this, StartupActivity.class);
                    finish();
                }
            }.start();

        } else {

            if (isLoggedIn) {

            } else {
                Functions.fireIntent(SplashActivity.this, StartupActivity.class);
                finish();
            }

        }
    }
}
