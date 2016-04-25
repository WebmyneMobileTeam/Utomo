package com.rovertech.utomo.app.offers.adpter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.main.drawer.AdminOfferRequestAPI;
import com.rovertech.utomo.app.offers.model.AdminOfferResp;
import com.rovertech.utomo.app.offers.model.OfferItem;
import com.rovertech.utomo.app.offers.model.OfferPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class AdminOfferAdapter extends RecyclerView.Adapter<AdminOfferAdapter.NotificationViewHolder> {

    private NotificationViewHolder holder = null;
    private ArrayList<OfferPojo> itemList;

    public AdminOfferAdapter(ArrayList<OfferPojo> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        switch (viewType) {

            default:
                holder = new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_offer, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

    holder.txtOfferTitle.setText(itemList.get(position).OfferName);
        holder.txtOfferTitleCode.setText(itemList.get(position).OfferCode);
        holder.txtOfferDesc.setText(itemList.get(position).Description);
        holder.txtOfferValidTo.setText("Valid Upto : "+ itemList.get(position).ValidTo);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView txtOfferTitle,txtOfferTitleCode,txtOfferDesc,txtOfferValidTo,txtUseOffer;
        private ImageView img;
        public NotificationViewHolder(View itemView) {
            super(itemView);
            txtOfferTitle= (TextView) itemView.findViewById(R.id.txtOfferTitle);
            txtOfferTitleCode= (TextView) itemView.findViewById(R.id.txtOfferTitleCode);
            txtOfferDesc= (TextView) itemView.findViewById(R.id.txtOfferDesc);
            txtOfferValidTo= (TextView) itemView.findViewById(R.id.txtOfferValidTo);
            txtUseOffer= (TextView) itemView.findViewById(R.id.txtUseOffer);
            img= (ImageView) itemView.findViewById(R.id.img);

        }
    }


}
