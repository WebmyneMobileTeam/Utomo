package com.rovertech.utomo.app.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.offers.model.OfferCategory;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public class AdminOfferInfoAdapter extends BaseAdapter {

    Context context;
    ArrayList<OfferCategory> offerArrayList;
    private static LayoutInflater inflater = null;

    public AdminOfferInfoAdapter(Context context, ArrayList<OfferCategory> offerArrayList) {
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        holder.txtDetail.setText(String.format("%d %s off on %s", offerArrayList.get(position).AdminOfferValue,
                offerArrayList.get(position).AmountType,offerArrayList.get(position).OfferCategoryName));
        holder.txtDetail.setTypeface(Functions.getBoldFont(context));
        //String.format(getString(R.string.all), 3.12, 2)
        //String.format("Amount: %.2f  for %d days ",  var1, var2);
        holder.txtCarNo.setVisibility(View.GONE);

        return convertView;
    }

    public class ViewHolder {
        public TextView txtDetail, txtCarNo;

    }
}
