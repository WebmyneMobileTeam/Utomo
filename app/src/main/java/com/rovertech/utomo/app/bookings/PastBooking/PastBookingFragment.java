package com.rovertech.utomo.app.bookings.PastBooking;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerViewOnScrollListener;


public class PastBookingFragment extends Fragment implements PastBookingView {

    private View parentView, mFooterLoadMoreView;
    private PastBookingPresenter pastBookingPresenter;


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

        parentView = inflater.inflate(R.layout.fragment_past_booking, container, false);
        initView(parentView);
        pastBookingPresenter = new PastBookingPresenterImpl(this);
        pastBookingPresenter.setUpRecyclerView();
        return parentView;
    }

    @Override
    public void initView(View view) {

        mFooterLoadMoreView = View.inflate(getActivity(), R.layout.footer_view_load_more, null);
        mFooterLoadMoreView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mFooterLoadMoreView.setVisibility(View.GONE);
    }

    @Override
    public void setUpRecyclerVIew(final PastBookingAdapter pastBookingAdapter) {

        if (pastBookingAdapter == null) {
            new Throwable("CurrentBookingAdapter should not be null");
        }

        FamiliarRecyclerView currentBookingRecyclerView = (FamiliarRecyclerView) parentView.findViewById(R.id.currentBookingRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        currentBookingRecyclerView.setLayoutManager(linearLayoutManager);


        currentBookingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        currentBookingRecyclerView.addFooterView(mFooterLoadMoreView);


        currentBookingRecyclerView.setAdapter(pastBookingAdapter);
        currentBookingRecyclerView.addOnScrollListener(new FamiliarRecyclerViewOnScrollListener(linearLayoutManager) {
            @Override
            public void onScrolledToTop() {

            }

            @Override
            public void onScrolledToBottom() {
                pastBookingPresenter.addMoreData(pastBookingPresenter.getData());
            }
        });


        pastBookingAdapter.addMoreData(pastBookingPresenter.getData());

    }

    @Override
    public void hideFooterView() {

        mFooterLoadMoreView.setVisibility(View.GONE);
    }

    @Override
    public void showFooterView() {

        mFooterLoadMoreView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pastBookingPresenter.destory();
    }
}
