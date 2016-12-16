package com.rovertech.utomo.app.gcm;

/**
 * Created by priyasindkar on 04-01-2016.
 */

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.gcm.model.NotificationModel;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.drawer.DrawerActivityRevised;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class GcmMessageHandler extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmMessageHandler() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " +
                        extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
                for (int i = 0; i < 5; i++) {
                    Log.e("", "Working... " + (i + 1)
                            + "/5 @ " + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                }
                Log.e("", "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                sendNotification(extras.getString("message"));
                Log.e("", "Received: " + extras.getString("message"));
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String message) {

//        Log.e("message", message);

        NotificationModel notificationModel = UtomoApplication.getInstance().getGson().fromJson(message, NotificationModel.class);
        Log.e("car_id_gcm", String.valueOf(notificationModel.getVehicalId()));

//        JSONObject object = null;
//        String strMsg = "";
//
//        try {
//            object = new JSONObject(message);
//            strMsg = object.getString("Message");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        String title = this.getString(R.string.app_name);

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setAutoCancel(true)
                        .setContentText(String.format("%s", notificationModel.getMessage()))
                        .setWhen(when)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(String.format("%s", notificationModel.getMessage())))
                        .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);

        Intent resultIntent = new Intent(this, DrawerActivityRevised.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if (notificationModel.getNotificationTypeId() == AppConstant.SERVICE_OFFER) {
            PrefUtils.setNotificationCarId(getApplicationContext(), notificationModel.getVehicalId());
        }

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        // TODO: 16-05-2016  If Else for Settings

        try {
            if (notificationModel.getNotificationTypeId() == AppConstant.SERVICE_OFFER) {
                if (PrefUtils.getSettingsBooking(getApplicationContext())) {
                    notificationManager.notify(m, mBuilder.build());
                }

            } else if (notificationModel.getNotificationTypeId() == AppConstant.ADMIN_OFFER) {
                if (PrefUtils.getSettingsOffer(getApplicationContext())) {
                    notificationManager.notify(m, mBuilder.build());
                }

            } else {
                notificationManager.notify(m, mBuilder.build());
            }
        } catch (Exception e) {
            Log.e("exception", "temp exception for PN");
            e.printStackTrace();
        }

    }
}
