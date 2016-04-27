package com.rovertech.utomo.app.bookings;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public class MyBookingAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    private int[] images = {R.drawable.ic_current_event, R.drawable.ic_action_past_event};

    public MyBookingAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    public void add(Fragment fragment, String title) {
        fragments.add(fragment);
        strings.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);

        TextView tv = (TextView) v.findViewById(R.id.tab_title);
        tv.setText(String.format(" %s ", getPageTitle(position)));
        tv.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        ImageView img = (ImageView) v.findViewById(R.id.tab_image);
        img.setImageResource(images[position]);
        img.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);

        return v;
    }
}
