package com.rovertech.utomo.app.offers.adpter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.offers.model.OfferCategory;
import com.rovertech.utomo.app.offers.model.OfferPojo;
import com.rovertech.utomo.app.widget.dialog.AdminOfferInfoDialog;

import java.util.ArrayList;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class AdminOfferAdapter extends RecyclerView.Adapter<AdminOfferAdapter.NotificationViewHolder> {

    private static  Context c;
    private NotificationViewHolder holder = null;
    private static ArrayList<OfferPojo> itemList;

    public AdminOfferAdapter(Context c,ArrayList<OfferPojo> itemList) {
        this.itemList = itemList;
        this.c=c;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        switch (viewType) {

            default:
                holder = new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_offer1, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, final int position) {

        holder.txtOfferTitle.setText("Offer Name : "+itemList.get(position).OfferName);
        holder.txtOfferTitleCode.setText(itemList.get(position).OfferCode);
       // holder.txtOfferTitle.setText("Title : "+itemList.get(position).Description);
        holder.txtOfferValidTo.setText("Valid From "+ itemList.get(position).ValidFrom +" To "+itemList.get(position).ValidTo );
        holder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<OfferCategory> category=itemList.get(position).lstAvailOffersCategory;
                final AdminOfferInfoDialog dialog=new AdminOfferInfoDialog(c,category,"admin",itemList.get(position).Description);
                dialog.setOnSubmitListener(new AdminOfferInfoDialog.onSubmitListener() {
                    @Override
                    public void onSubmit(OfferCategory offerCategory) {
                        dialog.dismiss();
                    }

                });
                dialog.show();

            }
        });
        setTypeFace();

       /* Glide.with(c)
                .load(itemList.get(position).OfferImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.img);*/
    }

    private void setTypeFace() {
        holder.txtOfferTitle1.setTypeface(Functions.getRegularFont(c));
        holder.txtOfferTitleCode.setTypeface(Functions.getBoldFont(c), Typeface.BOLD);
        holder.txtOfferTitle.setTypeface(Functions.getRegularFont(c));
        holder.txtOfferValidTo.setTypeface(Functions.getRegularFont(c));
        holder.txtOfferDiscount.setTypeface(Functions.getBoldFont(c), Typeface.BOLD);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder{

        private TextView txtOfferTitle,txtOfferTitleCode,txtOfferTitle1,txtOfferValidTo,txtOfferDiscount;
        private ImageView img,imgInfo;
        public NotificationViewHolder(View itemView) {
            super(itemView);
            txtOfferTitle1= (TextView) itemView.findViewById(R.id.txtOfferTitle1);
            txtOfferTitleCode= (TextView) itemView.findViewById(R.id.txtOfferTitleCode);
            txtOfferTitle= (TextView) itemView.findViewById(R.id.txtOfferTitle);
            txtOfferValidTo= (TextView) itemView.findViewById(R.id.txtOfferValidTo);
            txtOfferDiscount= (TextView) itemView.findViewById(R.id.txtOfferDiscount);
            img= (ImageView) itemView.findViewById(R.id.img);
            imgInfo= (ImageView) itemView.findViewById(R.id.imgInfo);


        }




    }


}
