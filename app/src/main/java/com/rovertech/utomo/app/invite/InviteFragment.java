package com.rovertech.utomo.app.invite;


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
import com.rovertech.utomo.app.helper.PrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviteFragment extends Fragment implements InviteView, View.OnClickListener {

    private View parentView;

    private TextView txtCode, txtDesc;
    private Button btnInvite;
    private InvitePresenter presenter;

    public InviteFragment() {
        // Required empty public constructor
    }


    public static InviteFragment newInstance() {

        Bundle args = new Bundle();

        InviteFragment fragment = new InviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_invite1, container, false);
        init();

        presenter = new InvitePresenterImpl(this);

        return parentView;
    }

    private void init() {
        txtCode = (TextView) parentView.findViewById(R.id.txtCode);
        txtDesc = (TextView) parentView.findViewById(R.id.txtWalletRs);
        btnInvite = (Button) parentView.findViewById(R.id.btnInvite);

        setTypeface();

        txtCode.setText(String.format("%s", PrefUtils.getUserFullProfileDetails(getActivity()).MyReferCode));
        txtCode.setOnClickListener(this);
        btnInvite.setOnClickListener(this);
    }

    private void setTypeface() {
        txtCode.setTypeface(Functions.getRegularFont(getActivity()), Typeface.BOLD);
        txtDesc.setTypeface(Functions.getRegularFont(getActivity()));
        btnInvite.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
    }

    @Override
    public void onClick(View v) {

        String code = txtCode.getText().toString().trim();

        switch (v.getId()) {
            case R.id.txtCode:
                presenter.copyText(code, getActivity());
                break;

            case R.id.btnInvite:
                presenter.invite(code, getActivity());
                break;
        }
    }
}
