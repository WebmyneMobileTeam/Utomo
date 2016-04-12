package com.rovertech.utomo.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
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


        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                                   @Override
                                   public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                                       Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
                                   }

                                   @Override
                                   public void cancel(ImageView imageView) {
                                       Glide.clear(imageView);
                                   }

                                   @Override
                                   public Drawable placeholder(Context ctx, String tag) {
                                       //define different placeholders for different imageView targets
                                       //default tags are accessible via the DrawerImageLoader.Tags
                                       //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                                       if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                                           return DrawerUIUtils.getPlaceHolder(ctx);
                                       } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                                           return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
                                       } else if ("customUrlItem".equals(tag)) {
                                           return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
                                       }

                                       //we use the default one for
                                       //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()

                                       return super.placeholder(ctx, tag);
                                   }
                               }

        );

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
