package com.rovertech.utomo.app.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.api.GoogleApiClient;
import com.rovertech.utomo.app.account.model.UserProfile;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public interface AccountPresenter {

    void loginFb(Activity activity);

    void loginGplus(Activity activity, GoogleApiClient googleApiClient);

    void checkCredentials(String number, String password);

    void checkCredentials(String number, String name, String email, String pwd, int cityId);

    void openSignUp();

    void openLogin();

    void openForget(Context context, String s);

    void socialMediaActivityResultHandler(int requestCode, int resultCode, Intent data, GoogleApiClient mGoogleApiClient);

    void doLogin(LoginActivity loginActivity, UserProfile userProfile);

    void fetchCity(Context context, String string);
}
