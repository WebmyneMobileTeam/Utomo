package com.rovertech.utomo.app.account;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public interface AccountView {

    void emailError();

    void nameError();

    void showProgress();

    void hideProgress();

    void navigateDashboard();

    void numberError();

    void navigateSignUp();

    void navigateLogin();

    void navigateAddCar();
}
