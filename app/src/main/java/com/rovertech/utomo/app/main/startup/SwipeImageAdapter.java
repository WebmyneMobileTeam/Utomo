package com.rovertech.utomo.app.main.startup;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rovertech.utomo.app.R;

/**
 * Created by sagartahelyani on 07-03-2016.
 */
public class SwipeImageAdapter extends PagerAdapter {

    Context context;
    LayoutInflater mLayoutInflater;
    int[] imageArray;

    public SwipeImageAdapter(Context context, int[] imageArray) {
        this.context = context;
        this.imageArray = imageArray;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView slideImage = (ImageView) itemView.findViewById(R.id.imgSlide);
        slideImage.setImageResource(imageArray[position]);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
