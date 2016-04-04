package com.rovertech.utomo.app.profile.carlist;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.AddCarActivity;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.profile.ProfileActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarListFragment extends Fragment implements CarFragmentView, View.OnClickListener {

    private View parentView;
    private ProfileActivity activity;
    private CarFragmentPresenter presenter;
    private ArrayList<CarPojo> carList;
    private CarListAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnAddCar;
    private TextView txtEmpty;
    private ProgressDialog progressDialog;

    public CarListFragment() {
        // Required empty public constructor
    }

    public static CarListFragment newInstance() {
        CarListFragment fragment = new CarListFragment();

        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            //presenter.fetchMyCars(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_car_list, container, false);

        init();

        presenter = new CarFragmentPresenterImpl(this);

        carList = new ArrayList<>();
        adapter = new CarListAdapter(getActivity(), carList);
        recyclerView.setAdapter(adapter);

        btnAddCar.setOnClickListener(this);

        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.fetchMyCars(getActivity());

    }

    private void init() {
        activity = (ProfileActivity) getActivity();

        txtEmpty = (TextView) parentView.findViewById(R.id.txtEmpty);
        btnAddCar = (Button) parentView.findViewById(R.id.btnAddCar);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnAddCar.setTypeface(Functions.getBoldFont(getActivity()));
        txtEmpty.setTypeface(Functions.getBoldFont(getActivity()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddCar:
                Intent addCarIntent = new Intent(getActivity(), AddCarActivity.class);
                addCarIntent.putExtra(AppConstant.SKIP, false);
                startActivity(addCarIntent);
                break;
        }
    }

    @Override
    public void setCarList(ArrayList<CarPojo> carList) {
        adapter.setCarList(carList);
    }

    @Override
    public void setEmptyView() {
        txtEmpty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), "Loading your vehicles", "Please wait..", false);
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
