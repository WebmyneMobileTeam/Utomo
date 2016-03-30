package com.rovertech.utomo.app.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by sagartahelyani on 04-03-2016.
 */
public class SocialConnection {

    public CallbackManager callbackManager;
    Context context;
    onFbSuccessListener onFbSuccessListener;
    private Uri imageUri;

    public SocialConnection(Context context) {
        this.context = context;
        FacebookSdk.sdkInitialize(context.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    public void setOnFbSuccessListener(onFbSuccessListener onFbSuccessListener) {
        this.onFbSuccessListener = onFbSuccessListener;
    }

    public void socialActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    public void loginWithFaceBook() {

        LoginManager.getInstance().logInWithReadPermissions((Activity) context, Arrays.asList("public_profile, email, user_birthday, user_friends"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(final JSONObject object, GraphResponse response) {

                        try {
                            final Profile profile = Profile.getCurrentProfile();

                            new AsyncTask<Void, Void, Void>() {

                                @Override
                                protected Void doInBackground(Void... params) {
                                    if (profile != null) {
                                        imageUri = profile.getProfilePictureUri(640, 640);
                                    }
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);
                                    if (onFbSuccessListener != null) {
                                        onFbSuccessListener.onSuccess(object, imageUri);
                                    }
                                }
                            }.execute();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.e("cancel", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("fail", "fail " + error.getMessage());
            }
        });

    }

    public interface onFbSuccessListener {

        void onSuccess(JSONObject object, Uri imageUri);
    }
}
