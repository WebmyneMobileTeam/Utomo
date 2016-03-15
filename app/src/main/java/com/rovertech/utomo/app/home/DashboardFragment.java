package com.rovertech.utomo.app.home;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.temp.CarFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private View parentView;
    private DrawerActivity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CarFragmentPagerAdapter adapter;
    private String tabTitles[] = new String[]{"Car 1", "Car 2", "Car 3"};
    private int tabIcons[] = new int[]{R.drawable.wrench, 0, 0};

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        init();
        return parentView;
    }

    private void init() {
        activity = (DrawerActivity) getActivity();

        viewPager = (ViewPager) parentView.findViewById(R.id.pager);
        tabLayout = (TabLayout) parentView.findViewById(R.id.tab_layout);
        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new CarFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        /*tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getIcon() != null)
                    tab.getIcon().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getIcon() != null)
                    tab.getIcon().setColorFilter(ContextCompat.getColor(getActivity(), R.color.half_black), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

        /*for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tabIcons[i] != 0) {
                tab.setIcon(tabIcons[i]);
                tab.getIcon().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            }
            // tab.select();

        }*/

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

    }

    private class CarFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private Context context;
        public ArrayList<Fragment> pagerFragments;

        public CarFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
            pagerFragments = new ArrayList<>();
            pagerFragments.add(CarFragment.newInstance());
            pagerFragments.add(CarFragment.newInstance());
            pagerFragments.add(CarFragment.newInstance());
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

            if (tabIcons[position] == 0) {
                return tabTitles[position];

            } else {
                Drawable image = ContextCompat.getDrawable(context, tabIcons[position]);
                image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
                // Replace blank spaces with image icon
                SpannableString sb = new SpannableString("   " + tabTitles[position]);
                ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sb;
            }
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);

            TextView tv = (TextView) v.findViewById(R.id.tab_title);
            tv.setText(tabTitles[position]);
            tv.setTypeface(Functions.getBoldFont(context));
            ImageView img = (ImageView) v.findViewById(R.id.tab_image);

            if (tabIcons[position] == 0) {
                img.setVisibility(View.GONE);
            } else {
                img.setImageResource(tabIcons[position]);
            }

            return v;
        }
    }
}
