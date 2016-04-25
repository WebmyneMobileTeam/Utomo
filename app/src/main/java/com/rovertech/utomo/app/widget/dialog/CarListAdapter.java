package com.rovertech.utomo.app.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public class CarListAdapter extends BaseAdapter {

    Context context;
    ArrayList<CarPojo> carArrayList;
    private static LayoutInflater inflater = null;

    public CarListAdapter(Context context, ArrayList<CarPojo> carArrayList) {
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.carArrayList = carArrayList;
    }

    @Override
    public int getCount() {
        return carArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return carArrayList.get(position);
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
            holder.txtCarName = (TextView) convertView.findViewById(R.id.txtCarName);
            holder.txtCarNo = (TextView) convertView.findViewById(R.id.txtCarNo);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtCarName.setText(String.format("%s %s", carArrayList.get(position).Make, carArrayList.get(position).Model));
        holder.txtCarNo.setText(String.format("( %s )", carArrayList.get(position).VehicleNo));

        return convertView;
    }

    public class ViewHolder {
        public TextView txtCarName, txtCarNo;

    }
}
