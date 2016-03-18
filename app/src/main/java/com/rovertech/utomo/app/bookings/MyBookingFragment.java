package com.rovertech.utomo.app.bookings;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookingFragment extends Fragment implements MyBookingView {


    private DrawerActivity activity;
    private MyBookingPresenter myBookingPresenter;
    private View parentView;

    public MyBookingFragment() {
        // Required empty public constructor
    }

    public static MyBookingFragment newInstance() {

        MyBookingFragment myBookingFragment = new MyBookingFragment();
        return myBookingFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_booking, container, false);
        initView(parentView);
        myBookingPresenter = new MyBookingPresenterImpl(getActivity().getSupportFragmentManager(),this);
        myBookingPresenter.setUpViewPagerAndTabs();
        return parentView;
    }


    @Override
    public void initView(View view) {

        //other view to init

    }

    @Override
    public void setUpViewPagerAndTabs(MyBookingAdapter myBookingAdapter) {


        if (myBookingAdapter == null) {
            new Throwable("My BookingAdapter should not be null");
        }
        ViewPager viewPager = (ViewPager) parentView.findViewById(R.id.myBookingPager);
        viewPager.setAdapter(myBookingAdapter);
        TabLayout tabLayout = (TabLayout) parentView.findViewById(R.id.myBookingTab);
        tabLayout.setupWithViewPager(viewPager);

    }
}
