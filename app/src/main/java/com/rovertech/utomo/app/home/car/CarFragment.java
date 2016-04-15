package com.rovertech.utomo.app.home.car;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.bookings.MyBookingFragment;
import com.rovertech.utomo.app.home.car.model.DashboardData;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.tiles.CurrentServiceTile;
import com.rovertech.utomo.app.tiles.HealthMeterTile;
import com.rovertech.utomo.app.tiles.odometer.OdometerTile;
import com.rovertech.utomo.app.tiles.performance.PerformanceTile;
import com.rovertech.utomo.app.tiles.serviceDate.ServiceDateTile;
import com.rovertech.utomo.app.tiles.sponsoredCenter.SponsoredCenterSet;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends Fragment implements CarView {

    private View parentView;
    private DrawerActivity activity;
    private CarPresenter presenter;
    private CarPojo carPojo;
    private ProgressDialog progressDialog;
    private LinearLayout mainContent;

    private CurrentServiceTile currentServiceTile;
    private HealthMeterTile healthMeterTile;
    private ServiceDateTile serviceDateTile;
    private OdometerTile odometerTile;
    private PerformanceTile performanceTile;
    private SponsoredCenterSet sponsoredCenterSet;

    public CarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_car, container, false);

        init();

        presenter = new CarPresenterImpl(this);
        presenter.fetchDashboard(getActivity(), carPojo);

        return parentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    public static CarFragment newInstance(CarPojo carPojo) {

        Bundle args = new Bundle();
        args.putSerializable("car", carPojo);
        CarFragment fragment = new CarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void init() {
        activity = (DrawerActivity) getActivity();
        mainContent = (LinearLayout) parentView.findViewById(R.id.mainContent);
        mainContent.setVisibility(View.GONE);

        // findview
        currentServiceTile = (CurrentServiceTile) parentView.findViewById(R.id.currentServiceTile);
        healthMeterTile = (HealthMeterTile) parentView.findViewById(R.id.healthMeterTile);
        serviceDateTile = (ServiceDateTile) parentView.findViewById(R.id.serviceDateTile);
        odometerTile = (OdometerTile) parentView.findViewById(R.id.odometerTile);
        performanceTile = (PerformanceTile) parentView.findViewById(R.id.performanceTile);
        sponsoredCenterSet = (SponsoredCenterSet) parentView.findViewById(R.id.sponsoredCenterSet);

        carPojo = (CarPojo) getArguments().getSerializable("car");
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait..", false);
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void setDashboard(DashboardData data) {

        if (data.IsCurrentBooking) {
            currentServiceTile.setVisibility(View.VISIBLE);
            currentServiceTile.setDetails(data, MyBookingFragment.CURRENTBOOKING);

        } else {

            if (data.BookingID == 0) {
                currentServiceTile.setVisibility(View.GONE);
            } else {
                currentServiceTile.setVisibility(View.VISIBLE);
                currentServiceTile.setDetails(data, MyBookingFragment.PASTBOOKING);
            }
        }

        healthMeterTile.setCarHealth(data.CarHealth);
        serviceDateTile.setDate(data.LastServiceDate);
        odometerTile.setOdometerReading(data.OdometerReading);
        performanceTile.setPerformance(data.lstPerformance);

        if (data.lstReferTile.size() == 0) {
            sponsoredCenterSet.setVisibility(View.GONE);
        } else {
            sponsoredCenterSet.setVisibility(View.VISIBLE);
            sponsoredCenterSet.setCenterList(data.lstReferTile);
        }

        mainContent.setVisibility(View.VISIBLE);
    }
}
