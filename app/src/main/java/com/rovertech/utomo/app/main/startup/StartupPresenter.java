package com.rovertech.utomo.app.main.startup;

import android.content.Context;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public interface StartupPresenter {

    void skip(Context context);

    void login();

    void signUp();

    void loginFb();

    void loginGplus();

    void stopRxLocation();
}
