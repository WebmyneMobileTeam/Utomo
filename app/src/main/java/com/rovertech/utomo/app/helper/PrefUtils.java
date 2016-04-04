package com.rovertech.utomo.app.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.rovertech.utomo.app.account.model.UserProfileOutput;

/**
 * Created by xitij on 17-03-2015.
 */
public class PrefUtils {

    public static String USER_ID = "UserId";
    public static String USER_PROFILE_KEY = "USER_PROFILE_KEY";

    public static void setLoggedIn(Context ctx, boolean value) {
        Prefs.with(ctx).save(AppConstant.LOGGED_IN, value);
    }

    public static boolean isUserLoggedIn(Context ctx) {
        return Prefs.with(ctx).getBoolean(AppConstant.LOGGED_IN, false);
    }

    public static void setUserID(Context ctx, int value) {
        Prefs.with(ctx).save(USER_ID, value);
    }

    public static int getUserID(Context ctx) {
        return Prefs.with(ctx).getInt(USER_ID, 0);
    }

    public static void setUserFullProfileDetails(Context context, UserProfileOutput userProfile) {

        String toJson = new Gson().toJson(userProfile);
        setUserID(context, userProfile.UserID);
        Prefs.with(context).save(USER_PROFILE_KEY, toJson);
    }

    public static UserProfileOutput getUserFullProfileDetails(Context context) {
        Gson gson = new Gson();

        UserProfileOutput userProfileDetails = null;

        String getCityString = Prefs.with(context).getString(USER_PROFILE_KEY, "");

        try {
            userProfileDetails = gson.fromJson(getCityString, UserProfileOutput.class);

        } catch (Exception e) {

        }
        return userProfileDetails;
    }

}
