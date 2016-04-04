package com.rovertech.utomo.app.profile.personal;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

/**
 * Created by sagartahelyani on 31-03-2016.
 */
public interface PersonalProfilePresenter {

    void doUpdate();

    void selectPUCDate(Context context);

    void fetchCity(Context context, String string);

    void selectImage(Context context);

    void setImage(Intent data, Context context, int requestCode);
}
