package com.rovertech.utomo.app.temp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.main.dashboard.DashboardActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends Fragment {

    private View parentView;
    private DashboardActivity activity;

    public CarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_car, container, false);
        init();
        return parentView;
    }

    private void init() {
        activity = (DashboardActivity) getActivity();
    }

}
