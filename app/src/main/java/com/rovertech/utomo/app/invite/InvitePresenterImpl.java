package com.rovertech.utomo.app.invite;

import android.content.Context;
import android.content.Intent;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 18-03-2016.
 */
public class InvitePresenterImpl implements InvitePresenter {

    private InviteView view;

    public InvitePresenterImpl(InviteView view) {
        this.view = view;
    }

    @Override
    public void copyText(String txtCode, Context context) {

        int sdk = android.os.Build.VERSION.SDK_INT;

        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(txtCode);

        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("code", txtCode);
            clipboard.setPrimaryClip(clip);
        }

        Functions.showToast(context, "Referral code copied to clipboard");
    }

    @Override
    public void invite(String code, Context context) {
        String text = "Use Promo code " + code + " when you create new account Utomo App." + "\n";
        String appURL = "Use Utomo app for your car service and share in your circle. Download App from Play Store\n" + "http://play.google.com/store/apps/details?id=" + context.getPackageName();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text + appURL);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, "Share via..."));
    }
}
