package com.rovertech.utomo.app.account;

import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public class AccountPresenterImpl implements AccountPresenter {

    AccountView accountView;

    public AccountPresenterImpl(AccountView accountView) {
        this.accountView = accountView;
    }

    @Override
    public void loginFb() {

    }

    @Override
    public void loginGplus() {

    }

    @Override
    public void checkCredentials(String number) {

        setProgressBar();

        boolean isError = false;

        if (number.length() != 10) {
            isError = true;
            setNumberError();
        }

        if (!isError) {
            loginSuccess();
        }

    }

    private void setNumberError() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.numberError();
        }
    }

    private void loginSuccess() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.navigateDashboard();
        }
    }

    private void setEmailError() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.emailError();
        }
    }

    private void setProgressBar() {
        if (accountView != null)
            accountView.showProgress();
    }

    @Override
    public void checkCredentials(String number, String name, String email) {
        setProgressBar();

        boolean isError = false;

        if (number.length() != 10) {
            isError = true;
            setNumberError();

        } else if (name.length() == 0) {
            isError = true;
            setNameError();

        } else if (email.length() != 0) {
            if (!Functions.emailValidation(email)) {
                isError = true;
                setEmailError();
            }
        }

        if (!isError) {
            signUpSuccess();
        }
    }

    private void signUpSuccess() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.navigateAddCar();
        }
    }

    @Override
    public void openSignUp() {
        if (accountView != null) {
            accountView.navigateSignUp();
        }
    }

    @Override
    public void openLogin() {
        if (accountView != null) {
            accountView.navigateLogin();
        }
    }

    private void setNameError() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.nameError();
        }
    }
}
