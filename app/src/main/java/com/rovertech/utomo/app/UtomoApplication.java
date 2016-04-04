package com.rovertech.utomo.app;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.google.gson.Gson;
import com.rovertech.utomo.app.helper.AppConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by raghavthakkar on 06-11-2015.
 */
public class UtomoApplication extends MultiDexApplication {


    private static UtomoApplication utomoApplication;
    private Gson gson;
    public static Retrofit retrofit;


    @Override
    public void onCreate() {
        super.onCreate();

        utomoApplication = this;
        setGson();

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    public void setGson() {
        this.gson = new Gson();
    }


    /**
     * @return ApplicationController gson
     */
    public Gson getGson() {
        if (this.gson == null) {
            gson = new Gson();
        }
        return this.gson;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized UtomoApplication getInstance() {
        return utomoApplication;
    }


}
