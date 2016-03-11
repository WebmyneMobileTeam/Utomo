package com.rovertech.utomo.app.main.startup;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public class StartupPresenterImpl implements StartupPresenter {

    StartupView view;

    public StartupPresenterImpl(StartupView view) {
        this.view = view;
    }

    @Override
    public void skip() {
        if (view != null)
            view.onSkip();
    }

    @Override
    public void login() {
        if (view != null)
            view.normalLogin();
    }

    @Override
    public void signUp() {
        if (view != null)
            view.signUp();
    }

    @Override
    public void loginFb() {

        if (view != null)
            view.fbLogin();
    }

    @Override
    public void loginGplus() {

        if (view != null)
            view.gplusLogin();
    }
}
