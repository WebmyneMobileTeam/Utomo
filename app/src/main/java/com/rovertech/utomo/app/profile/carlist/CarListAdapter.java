package com.rovertech.utomo.app.profile.carlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.tiles.carItem.CarItemTile;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    Context context;
    ArrayList<CarPojo> carArrayList;

    public CarListAdapter(Context context, ArrayList<CarPojo> carArrayList) {
        this.context = context;
        this.carArrayList = carArrayList;
    }

    public void setCarList(ArrayList<CarPojo> carArrayList) {
        this.carArrayList = new ArrayList<>();
        this.carArrayList = carArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tile_car_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.carItemTile = new CarItemTile(context, v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CarPojo carPojo = carArrayList.get(position);
        if (carPojo != null)
            holder.carItemTile.setDetails();
    }

    @Override
    public int getItemCount() {
        return carArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CarItemTile carItemTile;

        public ViewHolder(View view) {
            super(view);

        }
    }

}
