package com.rovertech.utomo.app.bookings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public class MyBookingAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    public MyBookingAdapter(FragmentManager fm) {
        super(fm);
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
}
