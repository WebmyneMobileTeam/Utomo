package com.rovertech.utomo.app.invite;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

/**
 * Created by sagartahelyani on 18-03-2016.
 */
public interface InvitePresenter {

    void copyText(String txtCode, Context context);

    void invite(String code, Context context);
}
