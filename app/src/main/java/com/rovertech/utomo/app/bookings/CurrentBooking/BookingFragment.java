package com.rovertech.utomo.app.bookings.CurrentBooking;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.bookings.MyBookingFragment;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;


public class BookingFragment extends Fragment implements BookingView {

    private View parentView;
    private BookingPresenter bookingPresenter;
    private ProgressBar progressBar;

    public BookingFragment() {

    }


    public static BookingFragment newInstance() {
        BookingFragment fragment = new BookingFragment();
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
        bookingPresenter.setUpRecyclerView(getActivity(), MyBookingFragment.CURRENTBOOKING);
        return parentView;
    }

    @Override
    public void initView(View view) {

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

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
