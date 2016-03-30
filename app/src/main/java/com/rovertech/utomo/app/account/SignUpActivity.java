package com.rovertech.utomo.app.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.City;
import com.rovertech.utomo.app.account.model.SocialRequest;
import com.rovertech.utomo.app.account.model.UserProfile;
import com.rovertech.utomo.app.addCar.AddCarActivity;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements AccountView, View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtCustomTitle, txtTc, txtSignUp, txtLogin, txtOr, txtForget;
    private View parentView;
    private AccountPresenter presenter;
    private TextView btnLogin;
    private EditText edtMobileNumber, edtName, edtEmail, edtPassword;
    private AutoCompleteTextView edtCity;
    private ProgressDialog progressDialog;
    private LinearLayout socialBar;
    private int cityId = 0;

    private CardView cardMobile, cardEmail, cardName, cardPassword, cardCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_revised);

        init();

        presenter = new AccountPresenterImpl(this, SignUpActivity.this);
    }

    private void init() {

        initToolbar();

        edtCity = (AutoCompleteTextView) findViewById(R.id.edtCity);
        cardCity = (CardView) findViewById(R.id.cardCity);
        socialBar = (LinearLayout) findViewById(R.id.socialBar);
        txtForget = (TextView) findViewById(R.id.txtForget);
        cardMobile = (CardView) findViewById(R.id.cardMobile);
        cardEmail = (CardView) findViewById(R.id.cardEmail);
        cardName = (CardView) findViewById(R.id.cardName);
        cardPassword = (CardView) findViewById(R.id.cardPassword);

        txtOr = (TextView) findViewById(R.id.txtOr);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        txtTc = (TextView) findViewById(R.id.txtTc);
        parentView = findViewById(android.R.id.content);
        btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnLogin.setText("Sign Up");
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtMobileNumber = (EditText) findViewById(R.id.edtMobileNumber);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        cardEmail.setVisibility(View.VISIBLE);
        cardName.setVisibility(View.VISIBLE);
        cardCity.setVisibility(View.VISIBLE);
        txtLogin.setVisibility(View.VISIBLE);
        edtCity.setVisibility(View.VISIBLE);
        txtSignUp.setVisibility(View.GONE);
        txtForget.setVisibility(View.GONE);
        socialBar.setVisibility(View.GONE);
        txtOr.setVisibility(View.GONE);

        btnLogin.setOnClickListener(this);
        txtLogin.setOnClickListener(this);

        edtCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0)
                    presenter.fetchCity(SignUpActivity.this, s.toString());
            }
        });

        setTypeface();
    }

    private void setTypeface() {
        txtOr.setTypeface(Functions.getBoldFont(this));
        btnLogin.setTypeface(Functions.getBoldFont(this));
        txtSignUp.setTypeface(Functions.getNormalFont(this));
        //edtCity.setTypeface(Functions.getNormalFont(this));
        txtLogin.setTypeface(Functions.getBoldFont(this));
        txtTc.setTypeface(Functions.getBoldFont(this));
        txtCustomTitle.setTypeface(Functions.getBoldFont(this));
        txtForget.setTypeface(Functions.getBoldFont(this));
    }

    private void initToolbar() {
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_close);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Sign Up");

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
            case R.id.btnLogin:
                presenter.checkCredentials(Functions.toStr(edtMobileNumber), Functions.toStr(edtName), Functions.toStr(edtEmail), Functions.toStr(edtPassword), cityId);
                break;

            case R.id.txtLogin:
                presenter.openLogin();
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
        Intent intent = new Intent(this, DrawerActivity.class);
        intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.HOME_FRAGMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void numberError() {
        Functions.showSnack(parentView, "Invalid Mobile Number");
    }

    @Override
    public void navigateSignUp() {

    }

    @Override
    public void navigateLogin() {
        finish();
        Functions.fireIntent(this, LoginActivity.class);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void navigateAddCar() {
        Intent addCarIntent = new Intent(this, AddCarActivity.class);
        addCarIntent.putExtra(AppConstant.SKIP, true);
        startActivity(addCarIntent);
    }

    @Override
    public void onFacebookLoginSuccess(SocialRequest socialRequest, String success) {

    }

    @Override
    public void onFacebookLoginError(String error) {

    }

    @Override
    public void onGoogleLoginSuccess(SocialRequest socialRequest, String success) {

    }

    @Override
    public void onGoogleLoginError(String error) {

    }

    @Override
    public void pwdError() {
        Functions.showSnack(parentView, "Password Must Be Of Minimum 8 Characters.");
    }

    @Override
    public void setCityAdapter(CityAdapter adapter, final ArrayList<City> cityArrayList) {
        edtCity.setAdapter(adapter);
        edtCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtCity.getWindowToken(), 0);
                City city = cityArrayList.get(position);
                cityId = city.CityID;
            }
        });
    }

    @Override
    public void cityError() {
        Functions.showSnack(parentView, "City Cannot Be Empty");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
}
