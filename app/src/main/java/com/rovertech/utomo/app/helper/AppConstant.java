package com.rovertech.utomo.app.helper;

/**
 * Created by raghavthakkar on 06-11-2015.
 */
public class AppConstant {

    public static String LOGGED_IN = "LOGGED_IN";
    public static String IS_SPLASH = "IS_SPLASH";
    public static String IS_CAR_ADDED = "IS_CAR_ADDED";

    public static String LOGIN = "login";
    public static String SIGN_UP = "sign_up";

    final public static String BASE_URL = "http://ws-srv-net.in.webmyne.com/Applications/CarBell/Development/UTOMOService_V01/Service/";

    // Account login/sign-up/change-forget password
    final public static String RESEND_OTP = "UserLoginSignup.svc/json/ResendOTP/{MOBILENUMBER}";
    final public static String BASIC_LOGIN_SIGNUP = "UserLoginSignup.svc/json/BasicLoginSignUp";
    final public static String OTP_VERIFY = "UserLoginSignup.svc/json/OTPVerification/{MOBILENUMBER}/{OTP}";
    final public static String SOCIAL_LOGIN_SIGNUP = "UserLoginSignup.svc/json/SocialLoginSignUp";
    final public static String RESET_PASSWORD = "UserLoginSignup.svc/json/ResetPassword/{MOBILENO}/{NEWPASSWORD}";
    final public static String UPDATE_PROFILE = BASE_URL + "UserLoginSignup.svc/json/UpdateUserProfile";
    final public static String BOOKING_ACTIVITIES = BASE_URL + "BookingActivities.svc/json/RequestForBooking";
    // Add car WS list
    final public static String FETCH_CITY = "UserActivities.svc/json/FetchCity";
    final public static String FETCH_MAKE = "UserActivities.svc/json/FetchMake";
    final public static String FETCH_YEAR = "UserActivities.svc/json/FetchYear/{DEALERSHIP}";
    final public static String FETCH_MODEL = "UserActivities.svc/json/FetchModel/{DEALERSHIP}/{YEAR}";
    final public static String ADD_CAR = BASE_URL + "UserActivities.svc/json/InsertVehicleDetails";
    final public static String DELETE_CAR = "UserActivities.svc/json/DeleteVehicleDetails/{USERID}/{VEHICLELID}";

    final public static String FETCH_SERVICE_CENTRELIST = "UserActivities.svc/json/FetchServiceCentreList";
    final public static String ADD_REVIEW = "UserActivities.svc/json/AddReview";
    final public static String FETCH_SERVICE_CENTRE_DETAIL = "UserActivities.svc/json/FetchServiceCentreDetail/";
    final public static String FETCH_VEHICLE_LIST = "UserActivities.svc/json/FetchVehicleList";
    final public static String INSERT_VEHICLE_DETAILS = BASE_URL + "UserActivities.svc/json/InsertVehicleDetails";
    final public static String UPDATE_VEHICLE_DETAILS = BASE_URL + "UserActivities.svc/json/UpdateVehicleDetails";
    final public static String FETCH_ADMIN_OFFER = BASE_URL + "UserActivities.svc/json/FetchAdminOffer";
    final public static String FETCH_NOTIFICATION = BASE_URL + "UserNotificationActivity.svc/json/FetchNotification/{USERID}";


    // Booking
    final public static String USER_BOOKINGS = "BookingActivities.svc/json/UserBookings/";
    final public static String BOOKING_DETAILS = "BookingActivities.svc/json/UserBookingDetail/{BOOKINGID}";
    final public static String CANCEL_BOOKING = "BookingActivities.svc/json/CancleBooking/{BOOKINGID}";

    // dashboard
    final public static String FETCH_DASHBOARD = "UserActivities.svc/json/FetchUserVehicleDashboard";

    public static String NO_INTERNET_CONNECTION = "No Internet Connection";
    public static String INTERNAL_ERROR = "INTERNAL ERROR.Please try again later.";
    public static String TIMEOUTERRROR = "Server not Responding";
    public static String ALREADY_BOOK = "You have request for service or this car has already is under service. Please choose another car.";

    public static String FRAGMENT_VALUE = "fragment";
    public static String HOME_FRAGMENT = "home";
    public static String MY_BOOKING_FRAGMENT = "mybooking";

    public static String[] startupTexts = {"Lorem Ipsum is simply dummy text", "It is a long established fact that a reader will be distracted",
            "Contrary to popular belief, Lorem Ipsum is not simply random text.", "There are many variations of passages of Lorem Ipsum available",
            "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."};

    public static String[] FB_READ_PERMISSIONS = new String[]{"public_profile, email, user_birthday, user_friends"};
    public static String FB_PARAM_FIELDS = "id, first_name,last_name, email, gender, birthday, link";

    public static String LOGIN_BY_FB = "FB";
    public static String LOGIN_BY_GPLUS = "GP";

    // Add car intent constants
    public static String SKIP = "isSkip";

    public static String VEHICLE_ID = "vehicleId";

    // Fetch Service centre listing types
    public static int BY_LAT_LNG = 1;
    public static int BY_CITY = 2;

    public static int REQUEST_CAMERA = 0;
    public static int PICK_IMAGE = 1;

    // Booking Status Codes
    public static int PENDING = 1;
    public static int SCHEDULE = 2;
    public static int ACCEPTED = 3;
    public static int PICKED = 4;
    public static int JOB_CARD_DONE = 5;
    public static int SERVICE_COMPLETED = 6;
    public static int INVOICED = 7;
    public static int PAYMENT_DONE = 8;
    public static int CAR_DELIVERED = 9;
    public static int CANCELLED = 10;

    // dashboard mode constants
    public static int MODE_ODOMETER = 1;
    public static int MODE_DATE = 2;
    public static int MODE_PERFORMANCE = 3;


    // Redirect to Login Page
    public static int FROM_START = 1;
    public static int FROM_SC = 2;

    // Redirect Booking Page
    public static int FROM_LOGIN = 1;
    public static int FROM_SC_LIST = 2;

}