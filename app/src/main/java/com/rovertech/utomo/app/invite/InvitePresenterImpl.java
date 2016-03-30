package com.rovertech.utomo.app.invite;

import android.content.Context;

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
        Functions.showToast(context, "Share");
    }
}
