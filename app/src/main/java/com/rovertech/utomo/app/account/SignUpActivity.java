package com.rovertech.utomo.app.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.City;
import com.rovertech.utomo.app.account.model.SocialRequest;
import com.rovertech.utomo.app.addCar.AddCarActivity;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.drawer.DrawerActivityRevised;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements AccountView, View.OnClickListener {

    private TextView txtCustomTitle, txtTc, txtSignUp, txtLogin, txtOr, txtForget;
    private View parentView;
    private AccountPresenter presenter;
    private Button btnLogin;
    private MaterialEditText edtMobileNumber, edtName, edtEmail, edtPassword, edtReferralCode;
    private MaterialAutoCompleteTextView edtCity;
    private ProgressDialog progressDialog;
    private int cityId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_revised);

        int fromLogin = PrefUtils.getRedirectLogin(this);

        init();

        presenter = new AccountPresenterImpl(this, SignUpActivity.this);
    }

    private void init() {

        initToolbar();

        edtReferralCode = (MaterialEditText) findViewById(R.id.edtReferralCode);
        edtCity = (MaterialAutoCompleteTextView) findViewById(R.id.edtCity);
        LinearLayout cityLayout = (LinearLayout) findViewById(R.id.cityLayout);
        LinearLayout socialBar = (LinearLayout) findViewById(R.id.socialBar);
        txtForget = (TextView) findViewById(R.id.txtForget);
        edtMobileNumber = (MaterialEditText) findViewById(R.id.edtMobileNumber);
        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);

        txtOr = (TextView) findViewById(R.id.txtOr);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        txtTc = (TextView) findViewById(R.id.txtTc);
        parentView = findViewById(android.R.id.content);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setText("Sign Up");

        edtEmail.setVisibility(View.VISIBLE);
        edtName.setVisibility(View.VISIBLE);
        edtCity.setVisibility(View.VISIBLE);
        edtCity.setVisibility(View.VISIBLE);

        txtSignUp.setVisibility(View.GONE);
        txtSignUp.setText(Html.fromHtml("<u>New User? Sign Up</u>"));

        txtLogin.setVisibility(View.VISIBLE);
        txtLogin.setText(Html.fromHtml("<u>Already User? Login</u>"));

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
        txtOr.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        btnLogin.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtSignUp.setTypeface(Functions.getRegularFont(this));
        edtReferralCode.setTypeface(Functions.getRegularFont(this));
        edtName.setTypeface(Functions.getRegularFont(this));
        edtEmail.setTypeface(Functions.getRegularFont(this));
        edtMobileNumber.setTypeface(Functions.getRegularFont(this));
        edtPassword.setTypeface(Functions.getRegularFont(this));
        edtCity.setTypeface(Functions.getRegularFont(this));
        txtLogin.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtTc.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtForget.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
    }

    private void initToolbar() {
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_close);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Sign Up");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.hideKeyPad(SignUpActivity.this, parentView);
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });
    }

    @Override
    public void onClick(View v) {

        Functions.hideKeyPad(this, parentView);

        switch (v.getId()) {
            case R.id.btnLogin:
                presenter.checkCredentials(edtMobileNumber.getText().toString().trim(), edtName.getText().toString().trim(),
                        edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim(), cityId, edtReferralCode.getText().toString().trim());
                break;

            case R.id.txtLogin:
                presenter.openLogin();
                break;

        }
    }

    @Override
    public void emailError() {
        Functions.showToast(this, "Please Enter Valid Email-Id");
    }

    @Override
    public void nameError() {
        Functions.showToast(this, "Please Enter Name");
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
        Intent intent = new Intent(this, DrawerActivityRevised.class);
        intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.HOME_FRAGMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void numberError(String msg) {
        Functions.showToast(this, msg);
    }

    @Override
    public void navigateSignUp() {
        // method for login activity
    }

    @Override
    public void navigateLogin() {
        finish();
        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void navigateAddCar() {

        PrefUtils.setSettingsOffer(this, true);
        PrefUtils.setSettingsBooking(this, true);

        if (PrefUtils.getRedirectLogin(this) == AppConstant.FROM_SKIP) {
            Intent addCarIntent = new Intent(this, AddCarActivity.class);
            addCarIntent.putExtra(AppConstant.SKIP, true);
            startActivity(addCarIntent);
            finish();

        } else {
            PrefUtils.setRedirectLogin(this, AppConstant.FROM_START);
            Intent addCarIntent = new Intent(this, AddCarActivity.class);
            addCarIntent.putExtra(AppConstant.SKIP, true);
            addCarIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(addCarIntent);
            finish();
        }
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
    public void pwdError(String msg) {
        Functions.showToast(this, msg);
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
    public void cityError(String msg) {
        Functions.showToast(this, msg);
    }

    @Override
    public void disconnectGoogle() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
}
