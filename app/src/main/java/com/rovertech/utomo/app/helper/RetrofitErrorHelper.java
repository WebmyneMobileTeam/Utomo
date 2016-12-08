package com.rovertech.utomo.app.helper;

import android.content.Context;

import com.rovertech.utomo.app.R;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by raghavthakkar on 13-05-2016.
 */
public class RetrofitErrorHelper {


    public static void showErrorMsg(Throwable throwable, Context context) {

        if (throwable instanceof IOException) {
            Functions.showErrorAlert(context, context.getResources().getString(R.string.no_internet_connection), false);
        } else if (throwable instanceof SocketTimeoutException) {
            Functions.showErrorAlert(context, context.getResources().getString(R.string.time_out), false);
        } else {
            Functions.showErrorAlert(context, context.getResources().getString(R.string.server_error), false);
        }
    }
}
