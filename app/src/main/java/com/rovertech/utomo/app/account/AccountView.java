package com.rovertech.utomo.app.account;

import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.City;
import com.rovertech.utomo.app.account.model.SocialRequest;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public interface AccountView {

    void emailError();

    void nameError();

    void showProgress();

    void hideProgress();

    void navigateDashboard();

    void numberError(String msg);

    void navigateSignUp();

    void navigateLogin();

    void navigateAddCar();

    void onFacebookLoginError(String error);

    void onGoogleLoginSuccess(SocialRequest socialRequest, String success);

    void onGoogleLoginError(String error);

    void pwdError();

    void setCityAdapter(CityAdapter adapter, ArrayList<City> cityArrayList);

    void cityError();

    void disconnectGoogle();
}
