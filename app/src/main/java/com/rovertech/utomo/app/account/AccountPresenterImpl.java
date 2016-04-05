package com.rovertech.utomo.app.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.BasicLoginRequest;
import com.rovertech.utomo.app.account.model.CityOutput;
import com.rovertech.utomo.app.account.model.CityRequest;
import com.rovertech.utomo.app.account.model.ManiBasicLoginSignUp;
import com.rovertech.utomo.app.account.model.ResendOutput;
import com.rovertech.utomo.app.account.model.SocialRequest;
import com.rovertech.utomo.app.account.model.UserProfile;
import com.rovertech.utomo.app.account.service.FetchCityService;
import com.rovertech.utomo.app.account.service.OtpVerifyService;
import com.rovertech.utomo.app.account.service.ResendOtpService;
import com.rovertech.utomo.app.account.service.SignUpService;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.widget.dialog.OTPDialog;

import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public class AccountPresenterImpl implements AccountPresenter {

    private AccountView accountView;

    // fb
    public CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;

    // google
    public int RC_SIGN_IN = 0;
    private Activity activity;

    public SocialRequest socialRequest = new SocialRequest();
    private GoogleCloudMessaging gcm;
    private String GCM_ID;
    private String deviceId;

    public AccountPresenterImpl(AccountView accountView, Activity activity) {
        this.accountView = accountView;
        this.activity = activity;
        deviceId = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @Override
    public void loginFb(final Activity activity) {
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList(AppConstant.FB_READ_PERMISSIONS));
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        final Profile profile = Profile.getCurrentProfile();

                        socialRequest = new SocialRequest();

                        new AsyncTask<Void, Void, Void>() {

                            Uri profileUri = null;

                            @Override
                            protected Void doInBackground(Void... params) {
                                if (Profile.getCurrentProfile() == null) {
                                    mProfileTracker = new ProfileTracker() {
                                        @Override
                                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                                            // profile2 is the new profile

                                            Uri profileUri = null;
                                            profileUri = profile2.getProfilePictureUri(640, 640);
                                            socialRequest.ProfileImg = profileUri.toString();
                                            Log.e("Profile pic #1 if", "" + profileUri);
                                            Log.e("Profile pic #2 if", "" + socialRequest.ProfileImg);
                                            //Log.e("Profile pic #3 if", "" + );
                                            mProfileTracker.stopTracking();
                                        }
                                    };
                                    mProfileTracker.startTracking();

                                } else {
                                    profileUri = profile.getProfilePictureUri(640, 640);
                                    socialRequest.ProfileImg = profileUri.toString();
                                    Log.e("Profile pic url else", "" + profileUri);
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                GraphRequest request = GraphRequest.newMeRequest(
                                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject user, GraphResponse response) {
                                                if (response.getError() != null) {
                                                    // handle error
                                                } else {
                                                    try {
                                                        JSONObject fbProfile = response.getJSONObject();
                                                        socialRequest.FName = fbProfile.getString("first_name");
                                                        socialRequest.LName = fbProfile.getString("last_name");
                                                        socialRequest.Gender = fbProfile.getString("gender");
                                                        socialRequest.Email = fbProfile.getString("email");
                                                        socialRequest.SocialID = fbProfile.get("id").toString();
                                                        socialRequest.LoginBy = AppConstant.LOGIN_BY_FB;

                                                        Log.e("social_request_impl", Functions.jsonString(socialRequest));

                                                        onFacebookLogin(socialRequest, true, activity.getString(R.string.facebook_success), "");
                                                    } catch (Exception e) {
                                                        onFacebookLogin(socialRequest, false, "", activity.getString(R.string.facebook_connection_error) + " " +
                                                                e.getMessage());
                                                    }
                                                }
                                            }
                                        });

                                Bundle parameters = new Bundle();
                                parameters.putString("fields", AppConstant.FB_PARAM_FIELDS);
                                request.setParameters(parameters);
                                request.executeAsync();
                                super.onPostExecute(aVoid);
                            }
                        }.execute();

                    }

                    @Override
                    public void onCancel() {
                        onFacebookLogin(socialRequest, false, "", activity.getString(R.string.facebook_connection_cancelled));
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        onFacebookLogin(socialRequest, false, "", activity.getString(R.string.facebook_connection_failed));
                    }
                });
    }

    @Override
    public void loginGplus(Activity activity, GoogleApiClient mGoogleApiClient) {
        this.activity = activity;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void onFacebookLogin(SocialRequest socialRequest, boolean isSuccess, String success, String error) {
        if (isSuccess) {
            accountView.onFacebookLoginSuccess(socialRequest, success);
        } else {
            accountView.onFacebookLoginError(error);
        }
    }

    @Override
    public void socialMediaActivityResultHandler(int requestCode, int resultCode, Intent
            data, GoogleApiClient mGoogleApiClient) {
        if (requestCode == RC_SIGN_IN) {  //handles Google+ Result
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result, mGoogleApiClient);
        } else { //handles Facebook Result
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void doLogin(LoginActivity loginActivity, final UserProfile userProfile) {
        accountView.showProgress();
    }

    @Override
    public void fetchCity(final Context context, String string) {
        CityRequest request = new CityRequest();
        request.CityName = string;

        Log.e("city_req", Functions.jsonString(request));

        FetchCityService service = UtomoApplication.retrofit.create(FetchCityService.class);
        Call<CityOutput> call = service.doFetchCity(request);
        call.enqueue(new Callback<CityOutput>() {
            @Override
            public void onResponse(Call<CityOutput> call, Response<CityOutput> response) {
                if (response.body() == null) {
                    Functions.showToast(context, "Error");
                } else {
                    Log.e("json_res", Functions.jsonString(response.body()));
                    CityAdapter adapter = new CityAdapter(context, R.layout.layout_adapter_item, response.body().FetchCity.Data);
                    accountView.setCityAdapter(adapter, response.body().FetchCity.Data);
                }
            }

            @Override
            public void onFailure(Call<CityOutput> call, Throwable t) {

            }
        });
    }

    private void processLogin(final Activity activity, final BasicLoginRequest loginRequest) {
        Log.e("login_request", loginRequest.toString());

        if (GCM_ID == null || GCM_ID == "") {
            onLogin(null, false, "", activity.getString(R.string.gcm_error));

        } else {

            SignUpService signUpService = UtomoApplication.retrofit.create(SignUpService.class);
            Call<ManiBasicLoginSignUp> call = signUpService.doUserLogin(loginRequest);
            call.enqueue(new Callback<ManiBasicLoginSignUp>() {
                @Override
                public void onResponse(Call<ManiBasicLoginSignUp> call, Response<ManiBasicLoginSignUp> response) {

                    if (accountView != null)
                        accountView.hideProgress();

                    if (response == null) {
                        Functions.showToast(activity, "Error occurred.");

                    } else {
                        Log.e("login_response", response.body().toString());

                        ManiBasicLoginSignUp loginOutput = response.body();

                        if (loginOutput.BasicLoginSignUp.ResponseCode == 1) {

                            PrefUtils.setUserFullProfileDetails(activity, loginOutput.BasicLoginSignUp.Data.get(0));
                            PrefUtils.setLoggedIn(activity, true);
                            loginSuccess();

                        } else if (loginOutput.BasicLoginSignUp.ResponseCode == -3) {

                            otpAlert(loginRequest, AppConstant.LOGIN, loginOutput.BasicLoginSignUp.Data.get(0).OTP, loginOutput);

                        } else {
                            Functions.showToast(activity, loginOutput.BasicLoginSignUp.ResponseMessage);
                        }
                    }

                }

                @Override
                public void onFailure(Call<ManiBasicLoginSignUp> call, Throwable t) {
                    Log.e("onFailure", t.toString());
                }
            });

        }
    }

    private void loginSuccess() {
        if (accountView != null)
            accountView.navigateDashboard();
    }

    private void verifyOTP(String otp, String mobile, final String string, final ManiBasicLoginSignUp loginOutput) {

        OtpVerifyService service = UtomoApplication.retrofit.create(OtpVerifyService.class);
        Call<ManiBasicLoginSignUp> call = service.verifyOTP(mobile, otp);
        call.enqueue(new Callback<ManiBasicLoginSignUp>() {
            @Override
            public void onResponse(Call<ManiBasicLoginSignUp> call, Response<ManiBasicLoginSignUp> response) {

                if (response.body() == null) {
                    Functions.showToast(activity, "Error occurred.");

                } else {

                    ManiBasicLoginSignUp otpOutput = response.body();

                    if (otpOutput.OTPVerification.ResponseCode == 1) {

                        Functions.showToast(activity, "OTP Verification successful.");

                        if (string.equals(AppConstant.LOGIN)) {

                            PrefUtils.setUserFullProfileDetails(activity, loginOutput.BasicLoginSignUp.Data.get(0));
                            PrefUtils.setLoggedIn(activity, true);
                            loginSuccess();

                        } else {
                            PrefUtils.setUserFullProfileDetails(activity, loginOutput.BasicLoginSignUp.Data.get(0));
                            PrefUtils.setLoggedIn(activity, true);

                            signUpSuccess();
                        }
                    } else {
                        Functions.showToast(activity, "OTP Verification Failed.");
                    }
                }
            }

            @Override
            public void onFailure(Call<ManiBasicLoginSignUp> call, Throwable t) {

            }
        });

    }

    private void onLogin(Object o, boolean b, String s, String string) {

    }

    private void handleSignInResult(GoogleSignInResult result, GoogleApiClient mGoogleApiClient) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            final GoogleSignInAccount acct = result.getSignInAccount();
            socialRequest = new SocialRequest();
            socialRequest.FName = acct.getDisplayName();
            if (acct.getPhotoUrl() != null) {
                //userProfile.setPhotoUri(acct.getPhotoUrl());
            }

            Plus.PeopleApi.load(mGoogleApiClient, acct.getId()).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
                @Override
                public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
                    try {
                        Person person = loadPeopleResult.getPersonBuffer().get(0);

                        socialRequest.Email = acct.getEmail();
                        if (acct.getPhotoUrl() != null) {
                            socialRequest.ProfileImg = acct.getPhotoUrl().toString();
                        }
                        if (person.getGender() == 0) {
                            socialRequest.Gender = "Male";
                        } else if (person.getGender() == 1) {
                            socialRequest.Gender = "Female";
                        }
                        socialRequest.SocialID = acct.getId();
                        socialRequest.LoginBy = AppConstant.LOGIN_BY_GPLUS;

                        onGoogleLogin(socialRequest, true, activity.getString(R.string.success), "");
                    } catch (Exception e) {
                        onGoogleLogin(socialRequest, false, "", activity.getString(R.string.google_connection_failed));
                    }
                }
            });
        } else {
            onGoogleLogin(socialRequest, false, "", activity.getString(R.string.google_connection_failed));
        }
    }

    private void onGoogleLogin(SocialRequest socialRequest, boolean isSuccess, String success, String error) {
        if (isSuccess) {
            accountView.onGoogleLoginSuccess(socialRequest, success);
        } else {
            accountView.onGoogleLoginError(error);
        }
    }

    @Override
    public void checkCredentials(String number, String pwd) {

        setProgressBar();

        boolean isError = false;

        if (number.length() != 10) {
            isError = true;
            setNumberError();

        } else if (pwd.length() == 0) {
            isError = true;
            setPasswordError();

        }

        if (!isError) {
            doNormalLogin(number, pwd);
        }

    }

    private void doNormalLogin(String number, String pwd) {

        final BasicLoginRequest loginRequest = new BasicLoginRequest();
        loginRequest.Mobile = number;
        loginRequest.Password = pwd;
        loginRequest.DeviceId = deviceId;
        loginRequest.isSignUp = false;

        try {
            if (gcm == null) {

                if (Functions.isGooglePlayServiceAvailable(activity)) {
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {
                            try {
                                if (gcm == null) {
                                    gcm = GoogleCloudMessaging.getInstance(activity);
                                }
                                GCM_ID = gcm.register(activity.getString(R.string.project_id));
                            } catch (Exception ex) {
                                GCM_ID = "";
                            }
                            return GCM_ID;
                        }

                        @Override
                        protected void onPostExecute(String regId) {
                            super.onPostExecute(regId);
                            if (!regId.equals("")) {
                                GCM_ID = regId;
                                loginRequest.GCMId = GCM_ID;
                                processLogin(activity, loginRequest);
                            } else {
                                Functions.showToast(activity, activity.getString(R.string.gcm_error));
                            }
                        }
                    }.execute();

                } else {
                    Functions.showToast(activity, activity.getString(R.string.gcm_error));
                }

            } else {
                processLogin(activity, loginRequest);
            }

        } catch (Exception e) {
            Functions.showToast(activity, activity.getString(R.string.gcm_error));
        }

    }

    private void setNumberError() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.numberError();
        }
    }

    private void signUpSuccess() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.navigateAddCar();
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
    public void checkCredentials(String number, String name, String email, String pwd, int cityId) {

        setProgressBar();

        boolean isError = false;

        if (number.length() != 10) {
            isError = true;
            setNumberError();

        } else if (name.length() == 0) {
            isError = true;
            setNameError();

        } else if (pwd.length() < 6) {
            isError = true;
            setPasswordError();

        } else if (email.length() != 0) {
            if (!Functions.emailValidation(email)) {
                isError = true;
                setEmailError();
            }

        } else if (cityId == 0) {
            isError = true;
            setCityError();
        }

        if (!isError) {
            doSignUp(number, name, email, pwd, cityId);
        }
    }

    private void setCityError() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.cityError();
        }
    }

    private void doSignUp(String number, String name, String email, String pwd, int cityId) {

        final BasicLoginRequest loginRequest = new BasicLoginRequest();
        loginRequest.EmailID = email;
        loginRequest.Mobile = number;
        loginRequest.Name = name;
        loginRequest.Password = pwd;
        loginRequest.DeviceId = deviceId;
        loginRequest.isSignUp = true;
        loginRequest.CityID = cityId;

        //get GCM
        try {
            if (gcm == null) {

                if (Functions.isGooglePlayServiceAvailable(activity)) {
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {
                            try {
                                if (gcm == null) {
                                    gcm = GoogleCloudMessaging.getInstance(activity);
                                }
                                GCM_ID = gcm.register(activity.getString(R.string.project_id));
                            } catch (Exception ex) {
                                GCM_ID = "";
                            }
                            return GCM_ID;
                        }

                        @Override
                        protected void onPostExecute(String regId) {
                            super.onPostExecute(regId);
                            if (!regId.equals("")) {
                                GCM_ID = regId;
                                loginRequest.GCMId = GCM_ID;
                                processSignUp(activity, loginRequest);
                            } else {
                                Functions.showToast(activity, activity.getString(R.string.gcm_error));
                            }
                        }
                    }.execute();

                } else {
                    Functions.showToast(activity, activity.getString(R.string.gcm_error));
                }

            } else {
                processSignUp(activity, loginRequest);
            }

        } catch (Exception e) {
            Functions.showToast(activity, activity.getString(R.string.gcm_error));
        }

    }

    private void processSignUp(final Activity activity, final BasicLoginRequest loginRequest) {
        Log.e("request", loginRequest.toString());

        if (GCM_ID == null || GCM_ID == "") {
            Functions.showToast(activity, activity.getString(R.string.gcm_error));

        } else {

            SignUpService signUpService = UtomoApplication.retrofit.create(SignUpService.class);
            Call<ManiBasicLoginSignUp> call = signUpService.doUserLogin(loginRequest);
            call.enqueue(new Callback<ManiBasicLoginSignUp>() {
                @Override
                public void onResponse(Call<ManiBasicLoginSignUp> call, Response<ManiBasicLoginSignUp> response) {

                    if (response.body() == null) {
                        Functions.showToast(activity, "Error occurred.");

                    } else {

                        ManiBasicLoginSignUp loginOutput = response.body();

                        Log.e("response", response.body().toString());

                        if (loginOutput.BasicLoginSignUp.ResponseCode == 1) {
                            otpAlert(loginRequest, AppConstant.SIGN_UP, loginOutput.BasicLoginSignUp.Data.get(0).OTP, loginOutput);
                            //signUpSuccess();

                        } else {
                            Functions.showToast(activity, loginOutput.BasicLoginSignUp.ResponseMessage);
                        }
                    }

                }

                @Override
                public void onFailure(Call<ManiBasicLoginSignUp> call, Throwable t) {
                    Log.e("onFailure", t.toString());
                }
            });

        }
    }

    private void otpAlert(final BasicLoginRequest loginRequest, final String string, String otp, final ManiBasicLoginSignUp loginOutput) {
        OTPDialog dialog = new OTPDialog(activity, otp);
        dialog.setOnSubmitListener(new OTPDialog.onSubmitListener() {
            @Override
            public void onSubmit(String otp) {
                verifyOTP(otp, loginRequest.Mobile, string, loginOutput);
            }
        });
        dialog.show();
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

    @Override
    public void openForget(Context context, String number) {
        if (number.equals("")) {
            accountView.numberError();
        } else {
            // call ws
            callWS(context, number);

        }
    }

    private void callWS(final Context context, final String number) {
        ResendOtpService service = UtomoApplication.retrofit.create(ResendOtpService.class);
        Call<ResendOutput> call = service.resendOTP(number);
        call.enqueue(new Callback<ResendOutput>() {
            @Override
            public void onResponse(Call<ResendOutput> call, Response<ResendOutput> response) {
                if (response.body() != null) {
                    ResendOutput output = response.body();

                    Log.e("res", Functions.jsonString(output));

                    if (output.ResendOTP.ResponseCode == 1) {
                        OTPDialog dialog = new OTPDialog(activity, output.ResendOTP.Data.get(0).OTP);
                        dialog.setOnSubmitListener(new OTPDialog.onSubmitListener() {
                            @Override
                            public void onSubmit(String otp) {

                            }
                        });
                        dialog.show();
                    } else {
                        Functions.showToast(context, output.ResendOTP.ResponseMessage);
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<ResendOutput> call, Throwable t) {

            }
        });
    }

    private void setNameError() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.nameError();
        }
    }

    private void setPasswordError() {
        if (accountView != null) {
            accountView.hideProgress();
            accountView.pwdError();
        }
    }
}
