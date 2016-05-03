package com.rovertech.utomo.app.wallet;

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
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;


public class WalletFragment extends Fragment {

    private View parentView;
    private DrawerActivity activity;
    private TextView txtWalletRs, txtWalletTitle, emptyView;

    Button btnHistory, btnInvite;
    private FamiliarRecyclerView recyclerView;

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

        emptyView = (TextView) parentView.findViewById(R.id.emptyView);

        txtWalletRs = (TextView) parentView.findViewById(R.id.txtWalletRs);
        btnInvite = (Button) parentView.findViewById(R.id.btnInvite);
        btnHistory = (Button) parentView.findViewById(R.id.btnHistory);
        txtWalletTitle = (TextView) parentView.findViewById(R.id.txtWalletRsTitle);

        txtWalletRs.setText(String.format("%s %.2f", getString(R.string.Rs), PrefUtils.getUserFullProfileDetails(getActivity()).WalletBalance));

        setTypeface();

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = (FamiliarRecyclerView) parentView.findViewById(R.id.recyclerView);
    }

    private void setTypeface() {
        txtWalletRs.setTypeface(Functions.getRegularFont(getActivity()), Typeface.BOLD);
        btnInvite.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        btnHistory.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        emptyView.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);

        txtWalletTitle.setTypeface(Functions.getRegularFont(getActivity()));
    }
}
