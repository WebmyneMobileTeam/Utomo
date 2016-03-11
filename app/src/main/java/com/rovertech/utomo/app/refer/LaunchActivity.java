package com.rovertech.utomo.app.refer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;

import org.json.JSONObject;

import java.util.Arrays;

public class LaunchActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private Button mLoginButton;
    CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_GET_TOKEN = 9002;
    private String TAG = "tage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_launcher_screen);
        callbackManager = CallbackManager.Factory.create();
        initView();

    }

    private void initView() {

        mLoginButton = (Button) findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithFaceBook();

            }
        });


    }

    private void loginWithFaceBook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile,email,user_birthday,user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                final Profile profile = Profile.getCurrentProfile();

                if (profile != null) {

                    GraphRequest request = GraphRequest.newMeRequest(
                            accessToken,
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(
                                        JSONObject object,
                                        GraphResponse response) {

                                    FaceBookProfile faceBookProfile = UtomoApplication.getInstance().getGson().fromJson(object.toString(), FaceBookProfile.class);
                                    faceBookProfile.profilePic = profile.getProfilePictureUri(640, 640).toString();


                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,birthday,first_name,email,gender,last_name,link");
                    request.setParameters(parameters);
                    request.executeAsync();

                } else {
                    LoginManager.getInstance().logOut();
                }
            }

            @Override
            public void onCancel() {

                Log.d("cancel", "cancel");
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onError(FacebookException error) {

                Log.e("Error", error.getMessage());
                LoginManager.getInstance().logOut();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_TOKEN) {
            // [START get_id_token]
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);


            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                String idToken = acct.getIdToken();
                acct.getPhotoUrl();
                Plus.PeopleApi.load(mGoogleApiClient, acct.getId()).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
                    @Override
                    public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
                        Person person = loadPeopleResult.getPersonBuffer().get(0);
                        Log.e(TAG, "Person loaded");
                        Log.e(TAG, person.getName().getGivenName());
                        Log.e(TAG, person.getName().getFamilyName());
                        Log.e(TAG, person.getDisplayName());
                        Log.e(TAG, person.getGender() + "");
                        Log.e(TAG, person.getImage() + "");
                        Log.e(TAG, person.getBirthday() + "");
                        disconnectGoogle();

                    }
                });
                // Show signed-in UI.


                // TODO(user): send token to server and validate server-side
            } else {


            }
            // [END get_id_token]
        }

    }

    private void disconnectGoogle() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d(TAG, "signOut:onResult:" + status);

                    }
                });
    }



    public void googleSignIn(View view) {

        validateServerClientID();

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
        getIdToken();
    }


    private void getIdToken() {
        // Show an account picker to let the user choose a Google account from the device.
        // If the GoogleSignInOptions only asks for IDToken and/or profile and/or email then no
        // consent screen will be shown here.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    private void validateServerClientID() {
        String serverClientId = getString(R.string.server_client_id);
        String suffix = ".apps.googleusercontent.com";
        if (!serverClientId.trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;


            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("failed", "onConnectionFailed:" + connectionResult);
    }
}
