package com.rovertech.utomo.app.bookings.CurrentBooking;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rovertech.utomo.app.R;


public class CurrentBookingFragment extends Fragment implements CurrentBookingView {

    private View parentView;
    private CurrentBookingPresenter currentBookingPresenter;

    public CurrentBookingFragment() {

    }


    public static CurrentBookingFragment newInstance() {
        CurrentBookingFragment fragment = new CurrentBookingFragment();
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
        currentBookingPresenter = new CurrentBookingPresenterImpl(this);
        currentBookingPresenter.setUpRecyclerView();
        return parentView;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setUpRecyclerVIew(CurrentBookingAdapter currentBookingAdapter) {

        if (currentBookingAdapter == null) {
            new Throwable("CurrentBookingAdapter should not be null");
        }

        RecyclerView currentBookingRecyclerView = (RecyclerView) parentView.findViewById(R.id.currentBookingRecyclerView);
        currentBookingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        currentBookingRecyclerView.setAdapter(currentBookingAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        currentBookingPresenter.destory();
    }
}
