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
    // private RecyclerView recyclerView;
    private TextView txtWallet, txtInvite, txtWalletTitle;
    //  private LinearLayoutManager linearLayoutManager;
    // private RecyclerViewAdapter myRecyclerViewAdapter;

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

       /* recyclerView=(RecyclerView)parentView.findViewById(R.id.recyclerView);
        linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        myRecyclerViewAdapter = new RecyclerViewAdapter(getActivity());
        myRecyclerViewAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        myRecyclerViewAdapter.add(0, "Eric");
        myRecyclerViewAdapter.add(1, "Android");
        myRecyclerViewAdapter.add(0, "Android-er");
        myRecyclerViewAdapter.add(2, "Google");
        myRecyclerViewAdapter.add(3, "android dev");
       *//* myRecyclerViewAdapter.add(0, "android-er.blogspot.com");
        myRecyclerViewAdapter.add(4, "Peter");
        myRecyclerViewAdapter.add(4, "Paul");
        myRecyclerViewAdapter.add(4, "Mary");
        myRecyclerViewAdapter.add(4, "May");
        myRecyclerViewAdapter.add(4, "Divid");
        myRecyclerViewAdapter.add(4, "Frankie");*//*
*/
    }


    private void setTypeface() {
        txtWallet.setTypeface(Functions.getNormalFontRoboto(getActivity()));
        txtInvite.setTypeface(Functions.getNormalFontRoboto(getActivity()));
        txtWalletTitle.setTypeface(Functions.getNormalFontRoboto(getActivity()));
    }

    /*@Override
    public void onItemClick(RecyclerViewAdapter.ItemHolder item, int position) {
        *//*Toast.makeText(getActivity(),
                position + " : " + item.getItemName(),
                Toast.LENGTH_SHORT).show();*//*
    }*/
}
