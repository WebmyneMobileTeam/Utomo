package com.rovertech.utomo.app.invoice.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.invoice.model.PaymentDistinctDiscountModel;
import com.rovertech.utomo.app.invoice.model.PaymentOfferDiscountList;
import com.rovertech.utomo.app.offers.model.OfferCategory;
import com.rovertech.utomo.app.widget.dialog.AdminOfferInfoDialog;
import com.rovertech.utomo.app.widget.dialog.PaymentDiscountOfferInfoDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class PaymentDiscountOffersAdapter extends RecyclerView.Adapter<PaymentDiscountOffersAdapter.DiscountOffersViewHolder> {

    private Context mContext;
    private DiscountOffersViewHolder holder = null;
    private List<PaymentOfferDiscountList> itemList;
    private List<String> serviceJobList = new ArrayList<>();
    private List<PaymentDistinctDiscountModel> filteredDiscounts = new ArrayList<>();
    private boolean[] checkedState;
    private OnOfferSelectedListener onOfferSelectedListener;


    public PaymentDiscountOffersAdapter(Context ctx, List<PaymentOfferDiscountList> itemList, List<String> jobsList,
                                        List<PaymentDistinctDiscountModel> filteredDiscounts) {
        this.itemList = itemList;
        this.mContext = ctx;
        this.serviceJobList = jobsList;
        this.filteredDiscounts = filteredDiscounts;
        checkedState = new boolean[itemList.size()];
        for (int i = 0; i < checkedState.length; i++) {
            checkedState[i] = false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public DiscountOffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
                holder = new DiscountOffersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_discount_offers_item, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final DiscountOffersViewHolder holder, final int position) {
        setTypeFace();

        if (checkedState[position]) {
            holder.isOfferSelected.setChecked(true);
        } else {
            holder.isOfferSelected.setChecked(false);
        }

        holder.txtOfferTitle.setText(itemList.get(position).OfferName);
        holder.txtOfferCode.setText(itemList.get(position).OfferCode);
        holder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PaymentDiscountOfferInfoDialog dialog = new PaymentDiscountOfferInfoDialog(mContext, itemList.get(position).lstDistinctDiscount, itemList.get(position).OfferName);
                dialog.show();
            }
        });

        holder.linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isOfferSelected.isChecked()) {
                    holder.isOfferSelected.setChecked(false);
                    onOfferSelectedListener.onOfferSelected(false, new ArrayList<PaymentDistinctDiscountModel>());
                } else {

                    holder.isOfferSelected.setChecked(true);
                    onOfferSelectedListener.onOfferSelected(true, itemList.get(position).lstDistinctDiscount);
                    holder.isOfferSelected.setChecked(true);
                    for (int i = 0; i < checkedState.length; i++) {

                        if (i != position) {
                            checkedState[i] = false;
                        } else {
                            checkedState[i] = true;
                        }
                    }
                    notifyDataSetChanged();
                }

            }

        });

/*        holder.isOfferSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    onOfferSelectedListener.onOfferSelected(itemList.get(position).lstDistinctDiscount);
                    holder.isOfferSelected.setChecked(true);
                    for (int i = 0; i < checkedState.length; i++) {

                        if (i != position) {
                            checkedState[i] = false;
                        } else {
                            checkedState[i] = true;
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });*/
    }

    private void setTypeFace() {
        holder.txtOfferTitle.setTypeface(Functions.getRegularFont(mContext));
        holder.txtOfferCode.setTypeface(Functions.getRegularFont(mContext));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class DiscountOffersViewHolder extends RecyclerView.ViewHolder {

        private TextView txtOfferTitle, txtOfferCode;
        private ImageView imgInfo;
        private RadioButton isOfferSelected;
        private CardView linearParent;

        public DiscountOffersViewHolder(View itemView) {
            super(itemView);
            txtOfferTitle = (TextView) itemView.findViewById(R.id.txtOfferTitle);
            txtOfferCode = (TextView) itemView.findViewById(R.id.txtOfferCode);
            imgInfo = (ImageView) itemView.findViewById(R.id.imgInfo);
            isOfferSelected = (RadioButton) itemView.findViewById(R.id.isOfferSelected);
            linearParent = (CardView) itemView.findViewById(R.id.linearParent);
        }
    }

    public interface OnOfferSelectedListener {
        void onOfferSelected(boolean isOfferSelected, List<PaymentDistinctDiscountModel> discountOfferItems);
    }

    public void setOnOfferSelectedListener(OnOfferSelectedListener _obj) {
        this.onOfferSelectedListener = _obj;
    }
}
