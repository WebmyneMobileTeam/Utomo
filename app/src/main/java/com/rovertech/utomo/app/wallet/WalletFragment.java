package com.rovertech.utomo.app.wallet;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.wallet.adpter.WalletAdapter;
import com.rovertech.utomo.app.wallet.model.WalletPojo;
import com.rovertech.utomo.app.wallet.mvp.WalletPresenter;
import com.rovertech.utomo.app.wallet.mvp.WalletPresenterImpl;
import com.rovertech.utomo.app.wallet.mvp.WalletView;
import com.rovertech.utomo.app.widget.DividerItemDecoration;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

import java.util.ArrayList;


public class WalletFragment extends Fragment implements WalletView {

    private View parentView;
    private TextView txtWalletRs, txtWalletTitle, emptyTextView, txtLatest, txtViewAll;

    Button btnHistory, btnInvite;
    private FamiliarRecyclerView recyclerView;

    private ArrayList<WalletPojo> walletArrayList;
    private WalletAdapter adapter;
    private WalletPresenter presenter;

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

        parentView = inflater.inflate(R.layout.fragment_wallet, container, false);
        init();

        return parentView;
    }

    private void init() {

        presenter = new WalletPresenterImpl(this, getActivity());
        emptyTextView = (TextView) parentView.findViewById(R.id.emptyTextView);
        txtLatest = (TextView) parentView.findViewById(R.id.txtLatest);

        txtViewAll = (TextView) parentView.findViewById(R.id.txtViewAll);
        txtWalletRs = (TextView) parentView.findViewById(R.id.txtWalletRs);
        btnInvite = (Button) parentView.findViewById(R.id.btnInvite);
        btnHistory = (Button) parentView.findViewById(R.id.btnHistory);
        txtWalletTitle = (TextView) parentView.findViewById(R.id.txtWalletRsTitle);

        txtWalletRs.setText(String.format("%s %.2f", getString(R.string.Rs), PrefUtils.getUserFullProfileDetails(getActivity()).WalletBalance));

        setTypeface();

        initRecyclerView();
    }

    private void initRecyclerView() {
        walletArrayList = new ArrayList<>();

        recyclerView = (FamiliarRecyclerView) parentView.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new WalletAdapter(getActivity(), walletArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        presenter.fetchWalletHistory();

    }

    private void setTypeface() {
        txtWalletRs.setTypeface(Functions.getRegularFont(getActivity()), Typeface.BOLD);
        btnInvite.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        btnHistory.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        emptyTextView.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        txtLatest.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        txtViewAll.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);

        txtWalletTitle.setTypeface(Functions.getRegularFont(getActivity()));
    }

    @Override
    public void setHistory(ArrayList<WalletPojo> walletList) {
        adapter.setWalletArrayList(walletList);
    }
}
