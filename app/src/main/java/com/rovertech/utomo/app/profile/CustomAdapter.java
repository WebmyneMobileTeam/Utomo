package com.rovertech.utomo.app.profile;

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
import com.rovertech.utomo.app.profile.carlist.CarListFragment;
import com.rovertech.utomo.app.profile.personal.PersonalProfileFragment;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 08-03-2016.
 */
public class CustomAdapter extends FragmentStatePagerAdapter {

    private Context context;
    public ArrayList<Fragment> pagerFragments;

    private String tabTitles[] = new String[]{"Personal", "My Car"};
    private int tabIcons[] = new int[]{R.drawable.ic_account, R.drawable.transport};

    public CustomAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        pagerFragments = new ArrayList<>();
        pagerFragments.add(PersonalProfileFragment.newInstance());
        pagerFragments.add(CarListFragment.newInstance());

    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return pagerFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];

    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);

        TextView tv = (TextView) v.findViewById(R.id.tab_title);
        tv.setText(String.format(" %s ", getPageTitle(position)));
        tv.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        ImageView img = (ImageView) v.findViewById(R.id.tab_image);
        img.setImageResource(tabIcons[position]);
        img.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_ATOP);

        return v;
    }

    public Fragment getFragment(int index) {
        return pagerFragments.get(index);
    }
}
