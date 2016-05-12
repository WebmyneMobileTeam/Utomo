package com.rovertech.utomo.app.profile.personal;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.io.File;

/**
 * Created by sagartahelyani on 31-03-2016.
 */
public interface PersonalProfilePresenter {

    void doUpdate(Context context, String name, String birthDate, String address, int cityId, File file, String email);

    void selectDOB(Context context);

    void fetchCity(Context context, String string);

    void selectImage();

    void setImage(Intent data, Context context, int requestCode);

    void changePwd(Context context);
}
