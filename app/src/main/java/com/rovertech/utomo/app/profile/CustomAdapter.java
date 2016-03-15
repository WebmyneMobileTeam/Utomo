package com.rovertech.utomo.app.profile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
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
    public String[] tabTitles;

    public CustomAdapter(FragmentManager fm, Context context, String[] tabIcons) {
        super(fm);
        this.context = context;
        this.tabTitles = tabIcons;
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

        /*Drawable image = ContextCompat.getDrawable(context, tabIcons[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString("   ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/

        return tabTitles[position];

    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);

        TextView tv = (TextView) v.findViewById(R.id.tab_title);
        tv.setTypeface(Functions.getBoldFont(context));
        tv.setText(tabTitles[position]);

        ImageView img = (ImageView) v.findViewById(R.id.tab_image);
        img.setVisibility(View.GONE);

        return v;
    }
}
