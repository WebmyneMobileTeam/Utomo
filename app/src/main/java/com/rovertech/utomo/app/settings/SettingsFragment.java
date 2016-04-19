package com.rovertech.utomo.app.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private View parentView;
    private DrawerActivity activity;

    private TextView txtNotification;
    private SwitchCompat switchOffer, switchBooking;


    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {

        Bundle args = new Bundle();

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_settings, container, false);
        init();
        return parentView;
    }

    private void init() {
        activity = (DrawerActivity) getActivity();

        switchOffer = (SwitchCompat) parentView.findViewById(R.id.switchOffer);
        switchBooking = (SwitchCompat) parentView.findViewById(R.id.switchBooking);
        txtNotification = (TextView) parentView.findViewById(R.id.txtNotification);

        setTypeface();
    }

    private void setTypeface() {
        switchBooking.setTypeface(Functions.getRegularFont(getActivity()));
        switchOffer.setTypeface(Functions.getRegularFont(getActivity()));
        txtNotification.setTypeface(Functions.getBoldFont(getActivity()));
    }

}
