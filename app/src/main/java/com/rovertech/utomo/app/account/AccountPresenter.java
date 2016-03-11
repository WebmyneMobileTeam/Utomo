package com.rovertech.utomo.app.account;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public interface AccountPresenter {

    void loginFb();

    void loginGplus();

    void checkCredentials(String number);

    void checkCredentials(String number, String name, String email);

    void openSignUp();

    void openLogin();
}
