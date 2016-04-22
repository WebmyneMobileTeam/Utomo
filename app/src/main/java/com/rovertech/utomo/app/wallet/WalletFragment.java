package com.rovertech.utomo.app.wallet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;


public class WalletFragment extends Fragment {

    private View parentView;
    private DrawerActivity activity;
    private TextView txtWallet, txtInvite, txtWalletTitle;

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

        txtWallet = (TextView) parentView.findViewById(R.id.txtWalletRs);
        txtInvite = (TextView) parentView.findViewById(R.id.txtInvite);
        txtWalletTitle = (TextView) parentView.findViewById(R.id.txtWalletRsTitle);

        txtWallet.setText(getString(R.string.Rs) + " 1000");

        setTypeface();

    }


    private void setTypeface() {
        txtWallet.setTypeface(Functions.getThinFont(getActivity()));
        txtInvite.setTypeface(Functions.getBoldFont(getActivity()));
        txtWalletTitle.setTypeface(Functions.getRegularFont(getActivity()));
    }

}
