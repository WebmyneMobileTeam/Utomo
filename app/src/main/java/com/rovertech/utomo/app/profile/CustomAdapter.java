package com.rovertech.utomo.app.profile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.rovertech.utomo.app.R;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 08-03-2016.
 */
public class CustomAdapter extends FragmentStatePagerAdapter {

    Context context;
    private int[] imageResId = {
            R.drawable.ic_account,
            R.drawable.ic_car
    };
    public ArrayList<Fragment> pagerFragments;

    public CustomAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        pagerFragments = new ArrayList<>();
        pagerFragments.add(PersonalProfileFragment.newInstance());
        pagerFragments.add(CarListFragment.newInstance());

    }

    @Override
    public Fragment getItem(int position) {
        return pagerFragments.get(position);
    }

    @Override
    public int getCount() {
        return imageResId.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth() + 20, image.getIntrinsicHeight() + 20);
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }
}
