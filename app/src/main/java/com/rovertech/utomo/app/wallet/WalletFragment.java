package com.rovertech.utomo.app.wallet;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;


public class WalletFragment extends Fragment {

    private View parentView;
    private DrawerActivity activity;
    private TextView txtWalletRs, txtInvite, txtWalletTitle;
    private LinearLayout amountLayout;

    private TextView txt1, txt2, txt3, txt4, txt5;

    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_wallet, container, false);
        init();

        return parentView;
    }

    private void init() {
        activity = (DrawerActivity) getActivity();
        //activity.hideFab(true);

        txt1 = (TextView) parentView.findViewById(R.id.txt1);
        txt2 = (TextView) parentView.findViewById(R.id.txt2);
        txt3 = (TextView) parentView.findViewById(R.id.txt3);
        txt4 = (TextView) parentView.findViewById(R.id.txt4);
        txt5 = (TextView) parentView.findViewById(R.id.txt5);

        amountLayout = (LinearLayout) parentView.findViewById(R.id.amountLayout);
        txtWalletRs = (TextView) parentView.findViewById(R.id.txtWalletRs);
        txtInvite = (TextView) parentView.findViewById(R.id.txtInvite);
        txtWalletTitle = (TextView) parentView.findViewById(R.id.txtWalletRsTitle);

        txtWalletRs.setText(String.format("%s %.2f", getString(R.string.Rs), PrefUtils.getUserFullProfileDetails(getActivity()).WalletBalance));

        setTypeface();
    }

    private void setTypeface() {
        txtWalletRs.setTypeface(Functions.getRegularFont(getActivity()), Typeface.BOLD);
        txtInvite.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        txtWalletTitle.setTypeface(Functions.getRegularFont(getActivity()));
        txt1.setTypeface(Functions.getBoldFont(getActivity()));
        txt2.setTypeface(Functions.getBoldFont(getActivity()));
        txt3.setTypeface(Functions.getBoldFont(getActivity()));
        txt4.setTypeface(Functions.getBoldFont(getActivity()));
        txt5.setTypeface(Functions.getBoldFont(getActivity()));
    }
}
