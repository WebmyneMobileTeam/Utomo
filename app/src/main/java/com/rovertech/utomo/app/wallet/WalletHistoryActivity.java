package com.rovertech.utomo.app.wallet;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.wallet.adpter.WalletAdapter;
import com.rovertech.utomo.app.wallet.model.WalletPojo;
import com.rovertech.utomo.app.widget.DividerItemDecoration;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

import java.util.ArrayList;

public class WalletHistoryActivity extends AppCompatActivity {

    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);

        init();
    }

    private void init() {
        initToolbar();

        emptyTextView = (TextView) findViewById(R.id.emptyTextView);
        emptyTextView.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        initRecyclerView();
    }

    private void initRecyclerView() {

        ArrayList<WalletPojo> walletArrayList = new ArrayList<>();

        FamiliarRecyclerView recyclerView = (FamiliarRecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (PrefUtils.getWallet(this) == null || PrefUtils.getWallet(this).Data.size() == 0) {
            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        } else {
            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            walletArrayList.addAll(PrefUtils.getWallet(this).Data);
            WalletAdapter adapter = new WalletAdapter(this, walletArrayList);
            recyclerView.setAdapter(adapter);
        }

    }

    private void initToolbar() {

        TextView txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);
        txtCustomTitle.setText(String.format("%s", "Wallet History"));

        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });
    }
}
