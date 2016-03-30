package com.rovertech.utomo.app.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private View parentView;
    private DrawerActivity activity;

    private TextView txtNotification;
    private CheckBox chbOffer, chbBooking;

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
        activity.hideFab(true);

        chbOffer = (CheckBox) parentView.findViewById(R.id.chbOffer);
        chbBooking = (CheckBox) parentView.findViewById(R.id.chbBooking);
        txtNotification = (TextView) parentView.findViewById(R.id.txtNotification);

        setTypeface();
    }

    private void setTypeface() {
        chbBooking.setTypeface(Functions.getNormalFont(getActivity()));
        chbOffer.setTypeface(Functions.getNormalFont(getActivity()));
        txtNotification.setTypeface(Functions.getBoldFont(getActivity()));
    }

}
