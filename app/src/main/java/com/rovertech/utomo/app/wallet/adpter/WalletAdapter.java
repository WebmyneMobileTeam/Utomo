package com.rovertech.utomo.app.wallet.adpter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.wallet.model.WalletPojo;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<WalletPojo> walletArrayList;

    public WalletAdapter(Context context, ArrayList<WalletPojo> walletArrayList) {
        this.context = context;
        this.walletArrayList = walletArrayList;
    }

    public void setWalletArrayList(ArrayList<WalletPojo> walletArrayList) {
        this.walletArrayList = new ArrayList<>();
        this.walletArrayList = walletArrayList;
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_item, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(v);

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        WalletPojo wallet = walletArrayList.get(position);

        holder.txtDescription.setText(String.format("%s", wallet.Description));
        holder.txtDate.setText(String.format("%s", Functions.displayOnlyDate(wallet.TransactionDate)));

        if (wallet.IsCredited) {
            holder.txtAmount.setText(String.format("+ %s %d", context.getResources().getString(R.string.Rs), wallet.CreditedAmount));
        } else {
            holder.txtAmount.setText(String.format("- %s %d", context.getResources().getString(R.string.Rs), wallet.CreditedAmount));
        }
    }

    @Override
    public int getItemCount() {
        return walletArrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtDescription, txtDate, txtAmount;

        public CustomViewHolder(View view) {
            super(view);
            this.txtDescription = (TextView) view.findViewById(R.id.txtDescription);
            this.txtDate = (TextView) view.findViewById(R.id.txtDate);
            this.txtAmount = (TextView) view.findViewById(R.id.txtAmount);

            this.txtDescription.setTypeface(Functions.getRegularFont(context), Typeface.BOLD);
            this.txtDate.setTypeface(Functions.getRegularFont(context));
            this.txtAmount.setTypeface(Functions.getRegularFont(context), Typeface.BOLD);

        }
    }
}
