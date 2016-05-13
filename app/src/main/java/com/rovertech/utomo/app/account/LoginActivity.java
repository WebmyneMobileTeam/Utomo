package com.rovertech.utomo.app.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.City;
import com.rovertech.utomo.app.account.model.SocialRequest;
import com.rovertech.utomo.app.addCar.AddCarActivity;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.booking.BookingActivity;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.main.drawer.DrawerActivityRevised;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements AccountView, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private Toolbar toolbar;
    private TextView txtCustomTitle, txtTc, txtSignUp, txtLogin, txtOr, txtForget;
    private View parentView;
    private AccountPresenter presenter;
    private ImageView imgFb, imgGoogle;
    private Button btnLogin;
    private MaterialEditText edtMobileNumber, edtPassword, edtName, edtEmail, edtReferralCode;
    private ProgressDialog progressDialog;
    private LinearLayout socialBar;
    private MaterialAutoCompleteTextView edtCity;

    //google
    private GoogleApiClient mGoogleApiClient;
    private int fromLogin = 0;

    private void initSocial() {
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSocial();
        setContentView(R.layout.activity_login_revised);

        fromLogin = PrefUtils.getRedirectLogin(this);

        init();

        presenter = new AccountPresenterImpl(this, LoginActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.socialMediaActivityResultHandler(requestCode, resultCode, data, mGoogleApiClient);
    }

    private String printKey() {

        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    private void init() {

        printKey(); // key hash

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope((Scopes.PLUS_ME)))
                .requestScopes(new Scope((Scopes.PROFILE)))
                .requestScopes(new Scope((Scopes.PLUS_LOGIN)))
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .requestProfile()
                .build();


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .addApi(Plus.API)
                    .build();
        }

        initToolbar();

        edtReferralCode = (MaterialEditText) findViewById(R.id.edtReferralCode);
        edtCity = (MaterialAutoCompleteTextView) findViewById(R.id.edtCity);
        edtCity.setVisibility(View.GONE);
        socialBar = (LinearLayout) findViewById(R.id.socialBar);
        txtForget = (TextView) findViewById(R.id.txtForget);
        edtEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        edtName = (MaterialEditText) findViewById(R.id.edtName);

        txtOr = (TextView) findViewById(R.id.txtOr);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        txtTc = (TextView) findViewById(R.id.txtTc);
        parentView = findViewById(android.R.id.content);
        imgFb = (ImageView) findViewById(R.id.imgFb);
        imgGoogle = (ImageView) findViewById(R.id.imgGoogle);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setText("Login");
        edtMobileNumber = (MaterialEditText) findViewById(R.id.edtMobileNumber);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);

        edtEmail.setVisibility(View.GONE);
        edtName.setVisibility(View.GONE);
        edtReferralCode.setVisibility(View.GONE);

        txtSignUp.setVisibility(View.VISIBLE);
        txtSignUp.setText(Html.fromHtml("<u>New User? Sign Up</u>"));

        txtLogin.setVisibility(View.GONE);
        txtLogin.setText(Html.fromHtml("<u>Already User? Login</u>"));

        socialBar.setVisibility(View.VISIBLE);
        txtOr.setVisibility(View.VISIBLE);

        imgFb.setOnClickListener(this);
        imgGoogle.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        txtForget.setOnClickListener(this);

        setTypeface();

    }

    private void setTypeface() {
        txtOr.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        btnLogin.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtSignUp.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtLogin.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtTc.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtForget.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
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
                Functions.hideKeyPad(LoginActivity.this, parentView);
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFb:
                presenter.loginFb(LoginActivity.this);
                break;

            case R.id.imgGoogle:
                presenter.loginGplus(LoginActivity.this, mGoogleApiClient);
                break;

            case R.id.btnLogin:
                Functions.hideKeyPad(this, this.parentView);
                presenter.checkCredentials(edtMobileNumber.getText().toString(), edtPassword.getText().toString());
                break;

            case R.id.txtSignUp:
                Functions.hideKeyPad(this, this.parentView);
                presenter.openSignUp();
                break;

            case R.id.txtForget:
                Functions.hideKeyPad(this, this.parentView);
                presenter.openForget(this, edtMobileNumber.getText().toString().trim());
                break;
        }
    }

    @Override
    public void emailError() {
        Functions.showErrorAlert(this, "Invalid Email-id", false);
    }

    @Override
    public void nameError() {
        Functions.showErrorAlert(this, "Please enter name", false);
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

        if (PrefUtils.getUserFullProfileDetails(this).VehicleCount == 0) {
            Intent addCarIntent = new Intent(this, AddCarActivity.class);
            addCarIntent.putExtra(AppConstant.SKIP, true);
            addCarIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(addCarIntent);

        } else if (fromLogin == AppConstant.FROM_START) {
            Intent intent = new Intent(this, DrawerActivityRevised.class);
            intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.HOME_FRAGMENT);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra(IntentConstant.BOOKING_PAGE, AppConstant.FROM_LOGIN);
            startActivity(intent);
            finish();
            // try
        }

        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void numberError(String msg) {
        Functions.showToast(this, msg);

    }

    @Override
    public void navigateSignUp() {
        //   Toast.makeText(this, "sd " + PrefUtils.getRedirectLogin(this), Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
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
    public void onFacebookLoginError(String error) {
        Functions.showToast(this, error);
    }

    @Override
    public void onGoogleLoginSuccess(SocialRequest socialRequest, String success) {
        Functions.jsonString(socialRequest);
        disconnectGoogle();
    }

    @Override
    public void onGoogleLoginError(String error) {
        Functions.showToast(this, error);
        disconnectGoogle();
    }

    @Override
    public void pwdError(String msg) {
        Functions.showToast(this, msg);
    }

    @Override
    public void setCityAdapter(CityAdapter adapter, ArrayList<City> cityArrayList) {

    }

    @Override
    public void cityError(String msg) {

    }

    @Override
    public void disconnectGoogle() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d("TAG", "signOut:onResult:" + status);

                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Functions.showToast(this, getString(R.string.google_connection_failed));
    }
}
