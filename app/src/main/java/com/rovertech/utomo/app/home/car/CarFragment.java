package com.rovertech.utomo.app.home.car;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.bookings.MyBookingFragment;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.home.car.model.DashboardData;
import com.rovertech.utomo.app.main.centerListing.ServiceCenterListActivity;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.tiles.CurrentServiceTile;
import com.rovertech.utomo.app.tiles.HealthMeterTile;
import com.rovertech.utomo.app.tiles.odometer.OdometerTile;
import com.rovertech.utomo.app.tiles.performance.PerformanceTile;
import com.rovertech.utomo.app.tiles.serviceDate.ServiceDateTile;
import com.rovertech.utomo.app.tiles.sponsoredCenter.SponsoredCenterSet;
import com.rovertech.utomo.app.widget.LocationFinder;

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
    private ScrollView scrollView;

    private CurrentServiceTile currentServiceTile;
    private HealthMeterTile healthMeterTile;
    private ServiceDateTile serviceDateTile;
    private OdometerTile odometerTile;
    private PerformanceTile performanceTile;
    private SponsoredCenterSet sponsoredCenterSet;

    private TextView txtRequestBooking, txtRequestBookingTitle;

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
        presenter.fetchDashboard(getActivity(), carPojo, "", 0, "", 0);

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
        scrollView = (ScrollView) parentView.findViewById(R.id.scrollView);
        currentServiceTile = (CurrentServiceTile) parentView.findViewById(R.id.currentServiceTile);
        healthMeterTile = (HealthMeterTile) parentView.findViewById(R.id.healthMeterTile);

        serviceDateTile = (ServiceDateTile) parentView.findViewById(R.id.serviceDateTile);
        serviceDateTile.setOnDateSetListener(new ServiceDateTile.onDateSetListener() {
            @Override
            public void setDate(String date, int mode) {
                presenter.fetchDashboard(getActivity(), carPojo, date, mode, "", 0);
            }
        });

        odometerTile = (OdometerTile) parentView.findViewById(R.id.odometerTile);
        performanceTile = (PerformanceTile) parentView.findViewById(R.id.performanceTile);
        sponsoredCenterSet = (SponsoredCenterSet) parentView.findViewById(R.id.sponsoredCenterSet);

        txtRequestBooking = (TextView) parentView.findViewById(R.id.txtRequestBooking);
        txtRequestBookingTitle = (TextView) parentView.findViewById(R.id.txtRequestBookingTitle);
        txtRequestBooking.setPaintFlags(txtRequestBooking.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtRequestBooking.setTypeface(Functions.getRegularFont(getActivity()));
        txtRequestBookingTitle.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);

        carPojo = (CarPojo) getArguments().getSerializable("car");

        txtRequestBooking.setOnClickListener(new View.OnClickListener() {
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
        odometerTile.setOnOdometerChangeListener(new OdometerTile.onOdometerChangeListener() {
            @Override
            public void onChange(String odometer) {
                presenter.fetchDashboard(getActivity(), carPojo, "", AppConstant.MODE_ODOMETER, odometer, 0);
            }
        });

        performanceTile.setPerformance(data.lstPerformance);
        performanceTile.setOnPerformanceResetListener(new PerformanceTile.onPerformanceResetListener() {
            @Override
            public void onReset(final int matricesId, final String date) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                alertDialogBuilder.setTitle("Reset")
                        .setMessage("Are you sure want to reset this performance matrices?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                presenter.fetchDashboard(getActivity(), carPojo, date, AppConstant.MODE_PERFORMANCE, "", matricesId);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        if (data.lstReferTile.size() == 0) {
            sponsoredCenterSet.setVisibility(View.GONE);
        } else {
            sponsoredCenterSet.setVisibility(View.VISIBLE);
            sponsoredCenterSet.setCenterList(data.lstReferTile);
        }

        mainContent.setVisibility(View.VISIBLE);
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

    public void getLocation(LocationFinder finder) {
        Log.e("location", finder.getLatitude() + " : " + finder.getLongitude());

    }
}