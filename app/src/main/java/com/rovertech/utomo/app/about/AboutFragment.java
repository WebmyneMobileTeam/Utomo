package com.rovertech.utomo.app.about;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class AboutFragment extends Fragment {

    private View parentView;
    private DrawerActivity activity;

    private TextView txtAppName, txtVersion, txt1, txt2, txt3, txt4, txt5;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_about, container, false);
        init();
        return parentView;
    }

    private void init() {
        activity = (DrawerActivity) getActivity();

        txtAppName = (TextView) parentView.findViewById(R.id.txtAppName);
        txt1 = (TextView) parentView.findViewById(R.id.txt1);
        txt2 = (TextView) parentView.findViewById(R.id.txt2);
        txt3 = (TextView) parentView.findViewById(R.id.txt3);
        txt4 = (TextView) parentView.findViewById(R.id.txt4);
        txt5 = (TextView) parentView.findViewById(R.id.txt5);
        txtVersion = (TextView) parentView.findViewById(R.id.txtVersion);

        setTypeface();

    }

    private void setTypeface() {
        txtAppName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "roboto_light.ttf"));
        txt1.setTypeface(Functions.getNormalFont(getActivity()));
        txt2.setTypeface(Functions.getNormalFont(getActivity()));
        txt3.setTypeface(Functions.getNormalFont(getActivity()));
        txt4.setTypeface(Functions.getNormalFont(getActivity()));
        txt5.setTypeface(Functions.getNormalFont(getActivity()));
        txtVersion.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "roboto_light.ttf"));
    }

}
