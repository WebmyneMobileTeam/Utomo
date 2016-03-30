package com.rovertech.utomo.app.helper;

/**
 * Created by raghavthakkar on 06-11-2015.
 */
public class AppConstant {

    public static String LOGGED_IN = "LOGGED_IN";

    public static String LOGIN = "login";
    public static String SIGN_UP = "sign_up";

    final public static String BASE_URL = "http://ws-srv-net.in.webmyne.com/Applications/CarBell/Development/UTOMOService_V01/Service/";
    final public static String BASIC_LOGIN_SIGNUP = "UserLoginSignup.svc/json/BasicLoginSignUp";
    final public static String OTP_VERIFY = "UserLoginSignup.svc/json/OTPVerification/{MOBILENUMBER}/{OTP}";
    final public static String FETCH_CITY = "UserActivities.svc/json/FetchCity";
    final public static String FETCH_MAKE = "UserActivities.svc/json/FetchMake";
    final public static String FETCH_YEAR = "UserActivities.svc/json/FetchYear/{DEALERSHIP}";
    final public static String FETCH_MODEL = "UserActivities.svc/json/FetchModel/{DEALERSHIP}/{YEAR}";
    final public static String FETCH_SERVICE_CENTRELIST = "UserActivities.svc/json/FetchServiceCentreList";
    final public static String ADD_REVIEW = "UserActivities.svc/json/AddReview";

    public static String NO_INTERNET_CONNECTION = "No Internet Connection";
    public static String INTERNAL_ERROR = "INTERNAL ERROR.Please try again later.";
    public static String TIMEOUTERRROR = "Server not Responding";

    public static String FRAGMENT_VALUE = "fragment";
    public static String HOME_FRAGMENT = "home";
    public static String MY_BOOKING_FRAGMENT = "mybooking";

    public static String[] startupTexts = {"Lorem Ipsum is simply dummy text", "It is a long established fact that a reader will be distracted",
            "Contrary to popular belief, Lorem Ipsum is not simply random text.", "There are many variations of passages of Lorem Ipsum available",
            "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."};

    public static String[] FB_READ_PERMISSIONS = new String[]{"email", "public_profile", "user_friends"};
    public static String FB_PARAM_FIELDS = "id, first_name,last_name, email, gender, birthday, link";

    public static String LOGIN_BY_FB = "FB";
    public static String LOGIN_BY_GPLUS = "GP";

    // Add car intent constants
    public static String SKIP = "isSkip";

    // Fetch Service centre listing types
    public static int BY_LAT_LNG = 1;
    public static int BY_CITY = 2;

    public static int REQUEST_CAMERA = 0;
    public static int PICK_IMAGE = 1;


}