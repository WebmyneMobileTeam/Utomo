package com.rovertech.utomo.app.offers.adpter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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


    private static final String AdminOfferAdapter = "AdminOfferAdapter.check";

    private static Context context;
    private static boolean adminFlag;
    private NotificationViewHolder holder = null;
    private ArrayList<OfferPojo> itemList;

    public AdminOfferAdapter(Context c, ArrayList<OfferPojo> itemList, boolean adminFlag) {
        this.itemList = itemList;
        this.context = c;
        this.adminFlag = adminFlag;
    }

    public void setOfferList(ArrayList<OfferPojo> itemList) {
        this.itemList = new ArrayList<>();
        this.itemList = itemList;
        notifyDataSetChanged();
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
        OfferPojo offerPojo = itemList.get(position);
//        Log.e("position " + position, Functions.jsonString(offerPojo));
        holder.setOfferDetails(offerPojo);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView txtOfferTitle, txtOfferTitleCode, txtOfferTitle1, txtOfferValidTo, txtOfferDiscount, txtOfferBy;
        private ImageView img, imgInfo;

        private NotificationViewHolder(View itemView) {
            super(itemView);
            txtOfferTitle1 = (TextView) itemView.findViewById(R.id.txtOfferTitle1);
            txtOfferTitleCode = (TextView) itemView.findViewById(R.id.txtOfferTitleCode);
            txtOfferTitle = (TextView) itemView.findViewById(R.id.txtOfferTitle);
            txtOfferValidTo = (TextView) itemView.findViewById(R.id.txtOfferValidTo);
            txtOfferDiscount = (TextView) itemView.findViewById(R.id.txtOfferDiscount);
            img = (ImageView) itemView.findViewById(R.id.img);
            imgInfo = (ImageView) itemView.findViewById(R.id.imgInfo);
            txtOfferBy = (TextView) itemView.findViewById(R.id.txtOfferBy);

            setTypeFace();

        }

        private void setTypeFace() {
            txtOfferTitle1.setTypeface(Functions.getRegularFont(context));
            txtOfferTitleCode.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
            txtOfferTitle.setTypeface(Functions.getRegularFont(context));
            txtOfferValidTo.setTypeface(Functions.getRegularFont(context));
            txtOfferBy.setTypeface(Functions.getRegularFont(context));
            txtOfferDiscount.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        }

        private void setOfferDetails(final OfferPojo offerPojo) {

            final ArrayList<OfferCategory> category = new ArrayList<>();
            float sum = 0;

            txtOfferTitle.setText("Offer Name : " + offerPojo.OfferName);
            txtOfferTitleCode.setText(offerPojo.OfferCode);

            if (offerPojo.OfferType != null) {
                if (offerPojo.OfferType.equals("Running")) {
                    txtOfferTitle1.setText("Active Offer");
                } else if (offerPojo.OfferType.equals("UpComming")) {
                    img.setColorFilter(context.getResources().getColor(R.color.button_bg), PorterDuff.Mode.SRC_ATOP);
                    txtOfferTitle1.setText("UpComming Offer");
                }
            }

            if (offerPojo.lstAvailOffersCategory.size() > 0) {
                category.addAll(offerPojo.lstAvailOffersCategory);

                if (category.size() > 0) {
                    for (int i = 0; i < category.size(); i++) {
                        if (adminFlag) {
                            sum = sum + category.get(i).AdminOfferValue;
                            txtOfferBy.setVisibility(View.VISIBLE);
                            // Html.fromHtml("Offer from "+"<u>"+itemList.get(position).OfferBy+"</u>");
                        } else {
                            int amt = 0;
                            //  if (category.get(i).AdminOfferValue > 0) {
                            amt = category.get(i).AdminOfferValue + category.get(i).SCOfferValue;
                            //  }
                            sum = sum + amt;
                            txtOfferBy.setVisibility(View.GONE);
                        }
                    }
                }

                txtOfferDiscount.setText("UPTO " + Math.round(sum) + " " + category.get(0).AmountType + " OFF ");

                imgInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AdminOfferInfoDialog dialog = new AdminOfferInfoDialog(context, category, adminFlag, offerPojo.OfferName, offerPojo.Description);
                        dialog.setOnSubmitListener(new AdminOfferInfoDialog.onSubmitListener() {
                            @Override
                            public void onSubmit(OfferCategory offerCategory) {
                                dialog.dismiss();
                            }

                        });
                        dialog.show();
                    }
                });

            }

            txtOfferValidTo.setText("Valid From " + offerPojo.ValidFrom + " To " + offerPojo.ValidTo);

            txtOfferBy.setText(Html.fromHtml("Offer from " + "<u>" + offerPojo.OfferBy + "</u>"));

        }
    }

}
