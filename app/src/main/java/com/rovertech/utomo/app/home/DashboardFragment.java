package com.rovertech.utomo.app.home;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.home.car.CarFragment;
import com.rovertech.utomo.app.home.presenter.DashboardPresenter;
import com.rovertech.utomo.app.home.presenter.DashboardPresenterImpl;
import com.rovertech.utomo.app.home.presenter.DashboardView;
import com.rovertech.utomo.app.main.centerListing.ServiceCenterListActivity;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.widget.LocationFinder;

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
    private FloatingActionButton fab;

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

        presenter.fetchMyCars(getActivity());

        return parentView;
    }

    private void init() {

        activity = (DrawerActivity) getActivity();

        //  pagerLayout = (LinearLayout) parentView.findViewById(R.id.pagerLayout);
        txtNoCar = (TextView) parentView.findViewById(R.id.txtNoCar);
        txtNoCar.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        viewPager = (ViewPager) parentView.findViewById(R.id.pager);
        tabLayout = (TabLayout) parentView.findViewById(R.id.tab_layout);
        fab = (FloatingActionButton) parentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PrefUtils.getCurrentCarSelected(getActivity()).CurrentBooking) {
                    Functions.showErrorAlert(getActivity(), "Cant' Book", AppConstant.ALREADY_BOOK);
                } else {
                    presenter.openCenterListing();
                }
            }
        });
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
        fab.setVisibility(View.GONE);
        txtNoCar.setVisibility(View.GONE);

        //  pagerLayout.setVisibility(View.VISIBLE);
        setCarPager(viewPager, data);
    }

    private void setCarPager(ViewPager viewPager, final ArrayList<CarPojo> data) {

        adapter = new CarFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), data);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(0);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            if (i == 0) {
                PrefUtils.setCurrentCarSelected(getActivity(), data.get(0));
            }
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null)
                tab.setCustomView(adapter.getTabView(i));
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                PrefUtils.setCurrentCarSelected(getActivity(), data.get(position));
                PrefUtils.setCurrentPosition(getActivity(), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(PrefUtils.getCurrentPosition(getActivity()));
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void setErrorMsg(String msg) {
        //    pagerLayout.setVisibility(View.GONE);
        txtNoCar.setVisibility(View.VISIBLE);
        txtNoCar.setText(msg);
        fab.setVisibility(View.GONE);
    }

    @Override
    public void navigateCenterListActivity() {
        LocationFinder finder = new LocationFinder(getActivity());

        if (!finder.canGetLocation()) {
            accurateAlert();

        } else {
            getLocation(finder);
            Intent centreIntent = new Intent(getActivity(), ServiceCenterListActivity.class);
            centreIntent.putExtra("lat", finder.getLatitude());
            centreIntent.putExtra("lng", finder.getLongitude());
            startActivity(centreIntent);
        }
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
                pagerFragments.add(CarFragment.newInstance(data.get(i)));
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
            tv.setTypeface(Functions.getTabHostFont(context), Typeface.BOLD);
            ImageView img = (ImageView) v.findViewById(R.id.tab_image);

            if (data.get(position).CurrentBooking) {
                img.setVisibility(View.VISIBLE);
            } else {
                img.setVisibility(View.GONE);
            }

            return v;
        }
    }

    private void accurateAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Note");
        alert.setMessage("Do you want to getting service centres nearby your location? Turn on your GPS from Settings.");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("No, Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent centreIntent = new Intent(getActivity(), ServiceCenterListActivity.class);
                centreIntent.putExtra("lat", 0.0);
                centreIntent.putExtra("lng", 0.0);
                startActivity(centreIntent);
            }
        });
        alert.show();
    }

    @Override
    public void getLocation(LocationFinder finder) {
        Log.e("location", finder.getLatitude() + " : " + finder.getLongitude());

    }
}
