package com.rovertech.utomo.app.bookings.PastBooking;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.bookings.CurrentBooking.BookingPresenter;
import com.rovertech.utomo.app.bookings.CurrentBooking.BookingPresenterImpl;
import com.rovertech.utomo.app.bookings.CurrentBooking.BookingView;
import com.rovertech.utomo.app.bookings.CurrentBooking.CurrentBookingAdapter;
import com.rovertech.utomo.app.bookings.MyBookingFragment;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;


public class PastBookingFragment extends Fragment implements BookingView {

    private View parentView;
    private BookingPresenter bookingPresenter;
    private ProgressBar progressBar;
    private TextView emptyTextView;

    public PastBookingFragment() {

    }


    public static PastBookingFragment newInstance() {
        PastBookingFragment fragment = new PastBookingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        parentView = inflater.inflate(R.layout.fragment_current_booking, container, false);
        initView(parentView);
        bookingPresenter = new BookingPresenterImpl(this);
        bookingPresenter.setUpRecyclerView(getActivity(), MyBookingFragment.PASTBOOKING);
        return parentView;
    }

    @Override
    public void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        emptyTextView = (TextView) view.findViewById(R.id.emptyTextView);
        emptyTextView.setText("No Past Booking");
        emptyTextView.setTypeface(Functions.getRegularFont(getActivity()), Typeface.BOLD);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void setUpRecyclerVIew(CurrentBookingAdapter currentBookingAdapter) {

        if (currentBookingAdapter == null) {
            new Throwable("CurrentBookingAdapter should not be null");
        }
        FamiliarRecyclerView currentBookingRecyclerView = (FamiliarRecyclerView) parentView.findViewById(R.id.currentBookingRecyclerView);
        currentBookingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        currentBookingRecyclerView.setAdapter(currentBookingAdapter);

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bookingPresenter.destory();
    }
}
