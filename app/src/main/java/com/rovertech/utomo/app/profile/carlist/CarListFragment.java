package com.rovertech.utomo.app.profile.carlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarListFragment extends Fragment implements CarFragmentView {

    private View parentView;
    private ProfileActivity activity;
    private TextView txtUpdate;
    private CarFragmentPresenter presenter;
    private ArrayList<CarPojo> carList;
    private CarListAdapter adapter;
    private RecyclerView recyclerView;

    public CarListFragment() {
        // Required empty public constructor
    }

    public static CarListFragment newInstance() {
        CarListFragment fragment = new CarListFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_car_list, container, false);

        presenter = new CarFragmentPresenterImpl(this);

        init();

        carList = new ArrayList<>();
        carList = presenter.fetchMyCars();

        adapter = new CarListAdapter(getActivity(),carList);
        recyclerView.setAdapter(adapter);

        return parentView;
    }

    private void init() {
        activity = (ProfileActivity) getActivity();

        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        txtUpdate = (TextView) parentView.findViewById(R.id.txtUpdate);
        txtUpdate.setTypeface(Functions.getBoldFont(getActivity()));

    }

}
