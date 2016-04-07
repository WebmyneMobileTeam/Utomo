package com.rovertech.utomo.app.helper;

/**
 * @author jatin
 */

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String VEHICLE_NO_PATTERN = "[A-Z]{2}\\s\\d{1,2}\\s[A-Z]{1,2}\\s\\d{3,4}";

    private static Pattern pattern;
    private static Matcher matcher;

    public static void fireIntent(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        context.startActivity(i);

    }

    public static void fireIntent(Context context, Intent intent) {
        context.startActivity(intent);

    }

    public static void LoadImage(ImageView imageView, String url, Context context) {

        try {
            Glide.clear(imageView);
            Glide.with(context).load(url).into(imageView);
        }catch (Exception e){

        }


    }

    public static Typeface getNormalFont(Context _context) {
        Typeface tf = Typeface.createFromAsset(_context.getAssets(), "custom.otf");
        return tf;
    }

    public static Typeface getBoldFont(Context _context) {
        Typeface tf = Typeface.createFromAsset(_context.getAssets(), "custombold.otf");
        return tf;
    }

    public static String jsonString(Object obj) {
        return "" + new GsonBuilder().create().toJson(obj).toString();
    }


    public static boolean isGooglePlayServiceAvailable(Context mContext) {
        boolean flag = false;
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext.getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public static boolean emailValidation(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean vehicleValidation(String vehicleNo) {
        pattern = Pattern.compile(VEHICLE_NO_PATTERN);
        matcher = pattern.matcher(vehicleNo);
        return matcher.matches();
    }

    public static String parseDate(String inputDate, String inputPattern, String outputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void showSnack(View v, String msg) {
        Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void rotateViewClockwise(ImageView imageview) {
        ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(imageview, "rotation", 0f, 180f);
        imageViewObjectAnimator.setDuration(500);
        imageViewObjectAnimator.start();
    }

    public static void antirotateViewClockwise(ImageView imageview) {
        ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(imageview, "rotation", 180f, 360f);
        imageViewObjectAnimator.setDuration(500);
        imageViewObjectAnimator.start();
    }

    public static String toStr(EditText editText) {


        return editText.getText().toString().trim();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void showErrorAlert(Context context, String msg) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Error");
        alert.setMessage(msg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public static void showErrorAlert(Context context, String title, String msg) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public static void showErrorAlert(final Context context, String title, String msg, final boolean isFinish) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (isFinish) {
                    Activity activity = (Activity) context;
                    activity.finish();

                }
            }
        });
        alert.show();
    }

    public static void openInMap(Context context, double latitude, double longitude, String labelName) {
        String newUri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + labelName + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUri));
        context.startActivity(intent);
    }

    public static Uri getImageUri(Context context, Bitmap thumbnail) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), thumbnail, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Context context, Uri tempUri) {
        Cursor cursor = context.getContentResolver().query(tempUri, null, null, null, null);
        int idx = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        }
        return cursor.getString(idx);
    }
}