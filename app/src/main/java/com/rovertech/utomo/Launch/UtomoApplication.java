package com.rovertech.utomo.Launch;

import android.app.Application;

import com.google.gson.Gson;


/**
 * Created by raghavthakkar on 06-11-2015.
 */
public class UtomoApplication extends Application {


    private static UtomoApplication utomoApplication;
    private Gson gson;


    @Override
    public void onCreate() {
        super.onCreate();

        utomoApplication = this;
        setGson();


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
