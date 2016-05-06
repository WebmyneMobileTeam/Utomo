package com.rovertech.utomo.app.settings;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private View parentView;
    private TextView txtNotificationTitle;
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

        txtNotificationTitle = (TextView) parentView.findViewById(R.id.txtNotificationTitle);
        switchOffer = (SwitchCompat) parentView.findViewById(R.id.switchOffer);
        switchBooking = (SwitchCompat) parentView.findViewById(R.id.switchBooking);

        setTypeface();
    }

    private void setTypeface() {
        switchBooking.setTypeface(Functions.getRegularFont(getActivity()));
        switchOffer.setTypeface(Functions.getRegularFont(getActivity()));
        txtNotificationTitle.setTypeface(Functions.getRegularFont(getActivity()), Typeface.BOLD);
    }

}
