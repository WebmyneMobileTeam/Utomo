package com.rovertech.utomo.app.about;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private View parentView;

    private TextView txtAppName, txt1, txt2, txt3, txt4, txt5, txtVersion, txtContact;

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

        txtAppName = (TextView) parentView.findViewById(R.id.txtAppName);
        txt1 = (TextView) parentView.findViewById(R.id.txt1);
        txt2 = (TextView) parentView.findViewById(R.id.txt2);
        txt3 = (TextView) parentView.findViewById(R.id.txt3);
        txt4 = (TextView) parentView.findViewById(R.id.txt4);
        txt5 = (TextView) parentView.findViewById(R.id.txt5);
        txtVersion = (TextView) parentView.findViewById(R.id.txtVersion);
        txtContact = (TextView) parentView.findViewById(R.id.txtContact);

        setTypeface();

    }

    private void setTypeface() {
        txtAppName.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        txt1.setTypeface(Functions.getBoldFont(getActivity()));
        txt2.setTypeface(Functions.getBoldFont(getActivity()));
        txt3.setTypeface(Functions.getBoldFont(getActivity()));
        txt4.setTypeface(Functions.getBoldFont(getActivity()));
        txt5.setTypeface(Functions.getBoldFont(getActivity()));
        txtVersion.setTypeface(Functions.getBoldFont(getActivity()));
        txtContact.setTypeface(Functions.getBoldFont(getActivity()));
    }

}
