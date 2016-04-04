package com.rovertech.utomo.app.home;


import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.home.presenter.DashboardPresenter;
import com.rovertech.utomo.app.home.presenter.DashboardPresenterImpl;
import com.rovertech.utomo.app.home.presenter.DashboardView;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.temp.CarFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements DashboardView {

    private View parentView;
    private DrawerActivity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CarFragmentPagerAdapter adapter;
    private DashboardPresenter presenter;
    private ProgressDialog progressDialog;
    private TextView txtNoCar;
    private LinearLayout pagerLayout;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init();

        presenter = new DashboardPresenterImpl(this);

        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.fetchMyCars(getActivity());
    }

    private void init() {
        activity = (DrawerActivity) getActivity();
        activity.hideFab(false);

        pagerLayout = (LinearLayout) parentView.findViewById(R.id.pagerLayout);
        txtNoCar = (TextView) parentView.findViewById(R.id.txtNoCar);
        txtNoCar.setTypeface(Functions.getBoldFont(getActivity()));
        viewPager = (ViewPager) parentView.findViewById(R.id.pager);
        tabLayout = (TabLayout) parentView.findViewById(R.id.tab_layout);

    }

    public static DashboardFragment newInstance() {

        Bundle args = new Bundle();
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait..", false);
    }

    @Override
    public void setCarList(ArrayList<CarPojo> data) {
        txtNoCar.setVisibility(View.GONE);
        pagerLayout.setVisibility(View.VISIBLE);
        setCarPager(viewPager, data);
    }

    private void setCarPager(ViewPager viewPager, ArrayList<CarPojo> data) {

        adapter = new CarFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), data);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null)
                tab.setCustomView(adapter.getTabView(i));
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void setErrorMsg(String msg) {
        pagerLayout.setVisibility(View.GONE);
        txtNoCar.setVisibility(View.VISIBLE);
        txtNoCar.setText(msg);
    }

    private class CarFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private Context context;
        public ArrayList<Fragment> pagerFragments;
        ArrayList<CarPojo> data;

        public CarFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<CarPojo> data) {
            super(fm);
            this.context = context;
            this.data = data;
            pagerFragments = new ArrayList<>();

            for (int i = 0; i < data.size(); i++) {
                pagerFragments.add(CarFragment.newInstance());
            }
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return pagerFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Drawable image = ContextCompat.getDrawable(context, R.drawable.wrench);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());

            SpannableString sb = new SpannableString(" " + data.get(position).Make + " " + data.get(position).Model);
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);

            TextView tv = (TextView) v.findViewById(R.id.tab_title);
            tv.setText(String.format(" %s %s ", data.get(position).Make, data.get(position).Model));
            tv.setTypeface(Functions.getBoldFont(context));
            ImageView img = (ImageView) v.findViewById(R.id.tab_image);

            if (data.get(position).CurrentBooking) {
                img.setVisibility(View.VISIBLE);
            } else {
                img.setVisibility(View.GONE);
            }

            return v;
        }
    }
}
