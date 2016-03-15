package com.rovertech.utomo.app.profile.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.profile.ProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalProfileFragment extends Fragment {

    private View parentView;
    private ProfileActivity activity;
    private EditText edtEmail, edtMobile, edtName;
    private TextView txtChangePassword, txtUpdate;

    public PersonalProfileFragment() {
        // Required empty public constructor
    }

    public static PersonalProfileFragment newInstance() {
        PersonalProfileFragment fragment = new PersonalProfileFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_personal, container, false);
        init();

        setTypeface();
        return parentView;
    }

    private void setTypeface() {
        edtEmail.setTypeface(Functions.getNormalFont(getActivity()));
        edtMobile.setTypeface(Functions.getNormalFont(getActivity()));
        edtName.setTypeface(Functions.getNormalFont(getActivity()));
        txtChangePassword.setTypeface(Functions.getBoldFont(getActivity()));
        txtUpdate.setTypeface(Functions.getBoldFont(getActivity()));
    }

    private void init() {
        activity = (ProfileActivity) getActivity();

        edtEmail = (EditText) parentView.findViewById(R.id.edtEmail);
        edtMobile = (EditText) parentView.findViewById(R.id.edtMobile);
        edtName = (EditText) parentView.findViewById(R.id.edtName);
        txtChangePassword = (TextView) parentView.findViewById(R.id.txtChangePassword);
        txtUpdate = (TextView) parentView.findViewById(R.id.txtUpdate);

    }

}
