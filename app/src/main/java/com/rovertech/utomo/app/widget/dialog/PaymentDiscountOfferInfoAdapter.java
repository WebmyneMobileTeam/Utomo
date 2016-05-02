package com.rovertech.utomo.app.widget.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.invoice.model.PaymentDistinctDiscountModel;
import com.rovertech.utomo.app.offers.model.OfferCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public class PaymentDiscountOfferInfoAdapter extends BaseAdapter {

    Context context;
    List<PaymentDistinctDiscountModel> offerArrayList;
    private static LayoutInflater inflater = null;

    public PaymentDiscountOfferInfoAdapter(Context context, List<PaymentDistinctDiscountModel> offerArrayList) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.offerArrayList = offerArrayList;
    }

    @Override
    public int getCount() {
        return offerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return offerArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.layout_car_list_item, null);
            holder.txtDetail = (TextView) convertView.findViewById(R.id.txtCarName);
            holder.txtCarNo = (TextView) convertView.findViewById(R.id.txtCarNo);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDetail.setText(String.format("%s %s off on %s", offerArrayList.get(position).DiscountAmount,
                offerArrayList.get(position).DiscountType, offerArrayList.get(position).ServiceName));
        holder.txtDetail.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        holder.txtCarNo.setVisibility(View.GONE);

        return convertView;
    }

    public void setItemList(List<PaymentDistinctDiscountModel> itemList) {
        this.offerArrayList = itemList;
    }

    public class ViewHolder {
        public TextView txtDetail, txtCarNo;

    }
}
