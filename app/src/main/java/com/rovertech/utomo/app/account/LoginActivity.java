package com.rovertech.utomo.app.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.AddCarActivity;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;

public class LoginActivity extends AppCompatActivity implements AccountView, View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtCustomTitle, txtTc, txtSignUp, txtLogin, txtOr;
    private View parentView;
    private AccountPresenter presenter;
    private ImageView imgFb, imgGoogle;
    private TextView btnLogin;
    private EditText edtMobileNumber, edtName, edtEmail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_revised);

        init();

        presenter = new AccountPresenterImpl(this);
    }

    private void init() {
        initToolbar();

        txtOr = (TextView) findViewById(R.id.txtOr);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        txtTc = (TextView) findViewById(R.id.txtTc);
        parentView = findViewById(android.R.id.content);
        imgFb = (ImageView) findViewById(R.id.imgFb);
        imgGoogle = (ImageView) findViewById(R.id.imgGoogle);
        btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnLogin.setText("Login");
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtMobileNumber = (EditText) findViewById(R.id.edtMobileNumber);
        edtName = (EditText) findViewById(R.id.edtName);

        edtEmail.setVisibility(View.GONE);
        edtName.setVisibility(View.GONE);
        txtSignUp.setVisibility(View.VISIBLE);
        txtLogin.setVisibility(View.GONE);

        imgFb.setOnClickListener(this);
        imgGoogle.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);

        setTypeface();

    }

    private void setTypeface() {
        txtOr.setTypeface(Functions.getBoldFont(this));
        btnLogin.setTypeface(Functions.getBoldFont(this));
        txtSignUp.setTypeface(Functions.getNormalFont(this));
        txtLogin.setTypeface(Functions.getNormalFont(this));
        txtTc.setTypeface(Functions.getNormalFont(this));
        txtCustomTitle.setTypeface(Functions.getBoldFont(this));
    }

    private void initToolbar() {

        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_close);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Login");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFb:
                presenter.loginFb();
                break;

            case R.id.imgGoogle:
                presenter.loginGplus();
                break;

            case R.id.btnLogin:
                presenter.checkCredentials(Functions.toStr(edtMobileNumber));
                break;

            case R.id.txtSignUp:
                presenter.openSignUp();
                break;
        }
    }

    @Override
    public void emailError() {
        Functions.showSnack(parentView, "Invalid Email-id");
    }

    @Override
    public void nameError() {
        Functions.showSnack(parentView, "Please enter name");
    }


    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait..", false);
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void navigateDashboard() {
        Functions.fireIntent(LoginActivity.this, DrawerActivity.class);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void numberError() {
        Functions.showSnack(parentView, "Invalid Mobile Number");
    }

    @Override
    public void navigateSignUp() {
        finish();
        Functions.fireIntent(this, SignUpActivity.class);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void navigateLogin() {
        finish();
        Functions.fireIntent(this, LoginActivity.class);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void navigateAddCar() {
        Functions.fireIntent(this, AddCarActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
}
