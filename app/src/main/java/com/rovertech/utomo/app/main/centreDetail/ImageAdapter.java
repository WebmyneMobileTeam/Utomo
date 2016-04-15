package com.rovertech.utomo.app.main.centreDetail;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rovertech.utomo.app.helper.Functions;

import java.util.List;

/**
 * Created by sagartahelyani on 16-03-2016.
 */
public class ImageAdapter extends PagerAdapter {

    Context context;
    private List<String> images;

    public ImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Functions.LoadImage(imageView, images.get(position), context);

        FrameLayout frameLayout = new FrameLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View shadowView = new View(context);
        shadowView.setBackgroundColor(Color.parseColor("#99000000"));
        frameLayout.addView(imageView, params);
        frameLayout.addView(shadowView, params);
        ((ViewPager) container).addView(frameLayout, 0);

        return frameLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((FrameLayout) object);
    }
}
