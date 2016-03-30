package com.rovertech.utomo.app.main.review;

import android.content.Context;

/**
 * Created by sagartahelyani on 21-03-2016.
 */
public interface ReviewPresenter {

    void submitRating(Context context, String strMessage, float rating);
}
