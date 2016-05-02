package com.rovertech.utomo.app.helper;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.account.model.UserProfileOutput;
import com.rovertech.utomo.app.main.centreDetail.model.FeedBack;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xitij on 17-03-2015.
 */
public class PrefUtils {

    public static String USER_ID = "UserId";
    public static String USER_PROFILE_KEY = "USER_PROFILE_KEY";
    public static String FEEDBACK = "FEEDBACK";
    public static String GCM_ID = "GCM_ID";
    public static String DEVICE_ID = "Device_ID";
    public static String CURRENT_CAR_SELECTED = "CURRENT_CAR_SELECTED";
    public static String CURRENT_CENTER_SELECTED = "CURRENT_CENTER_SELECTED";
    public static String PAGER_POSITION = "PAGER_POSITION";

    public static final String RedirectLogin = "RedirectLogin";

    public static void setSplash(Context ctx, boolean value) {
        Prefs.with(ctx).save(AppConstant.IS_SPLASH, value);
    }

    public static boolean isSplash(Context ctx) {
        return Prefs.with(ctx).getBoolean(AppConstant.IS_SPLASH, false);
    }


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

    public static void setFeedBack(Context context, List<FeedBack> feedBacks) {

        String feedBackString;
        if (feedBacks != null) {

            feedBackString = UtomoApplication.getInstance().getGson().toJson(feedBacks);

        } else {
            feedBacks = new ArrayList<>();
            feedBackString = UtomoApplication.getInstance().getGson().toJson(feedBacks);
        }

        Prefs.with(context).save(FEEDBACK, feedBackString);
    }


    public static List<FeedBack> getFeedBack(Context context) {
        Gson gson = new Gson();

        List<FeedBack> feedBackList = null;

        String getCityString = Prefs.with(context).getString(FEEDBACK, "");

        try {
            Type type = new TypeToken<List<FeedBack>>() {
            }.getType();
            feedBackList = gson.fromJson(getCityString, type);

        } catch (Exception e) {

        }
        return feedBackList;
    }

    public static void setCurrentPosition(Context context, int position) {
        Prefs.with(context).save(PAGER_POSITION, position);
    }

    public static int getCurrentPosition(Context ctx) {
        return Prefs.with(ctx).getInt(PAGER_POSITION, 0);
    }

    public static void setCurrentCarSelected(Context context, CarPojo carPojo) {
        if (carPojo != null) {
            String carPojoString = UtomoApplication.getInstance().getGson().toJson(carPojo);
            Prefs.with(context).save(CURRENT_CAR_SELECTED, carPojoString);
        }
    }

    public static CarPojo getCurrentCarSelected(Context context) {

        CarPojo carPojo = new CarPojo();
        try {
            String carString = Prefs.with(context).getString(CURRENT_CAR_SELECTED, "");
            carPojo = UtomoApplication.getInstance().getGson().fromJson(carString, CarPojo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return carPojo;
    }

    public static void setCenterSelected(Context context, FetchServiceCentreDetailPojo centerPojo) {
        if (centerPojo != null) {
            String carPojoString = UtomoApplication.getInstance().getGson().toJson(centerPojo);
            Prefs.with(context).save(CURRENT_CENTER_SELECTED, carPojoString);
        }
    }

    public static FetchServiceCentreDetailPojo getCurrentCenter(Context context) {

        FetchServiceCentreDetailPojo centerPojo = new FetchServiceCentreDetailPojo();
        try {
            String carString = Prefs.with(context).getString(CURRENT_CENTER_SELECTED, "");
            centerPojo = UtomoApplication.getInstance().getGson().fromJson(carString, FetchServiceCentreDetailPojo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return centerPojo;
    }


    public static void setGCMID(Activity activity, String gcm_id) {
        Prefs.with(activity).save(GCM_ID, gcm_id);
    }

    public static String getGcmId(Context context) {
        return Prefs.with(context).getString(GCM_ID, "");
    }

    public static String getDeviceId(Context context) {
        return Prefs.with(context).getString(DEVICE_ID, "");
    }

    public static void setDeviceId(Activity activity, String deviceId) {
        Prefs.with(activity).save(DEVICE_ID, deviceId);
    }

    public static void setCarAdded(Context ctx, boolean b) {
        Prefs.with(ctx).save(AppConstant.IS_CAR_ADDED, b);
    }

    public static boolean isCarAdded(Context ctx) {
        return Prefs.with(ctx).getBoolean(AppConstant.IS_CAR_ADDED, false);
    }

    public static void setRedirectLogin(Context ctx, int value) {
        Prefs.with(ctx).save(RedirectLogin, value);
    }

    public static int getRedirectLogin(Context ctx) {
        return Prefs.with(ctx).getInt(RedirectLogin, 0);
    }

}
