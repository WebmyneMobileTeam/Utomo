package com.rovertech.utomo.app.profile.carlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.AddCarActivity;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.tiles.carItem.CarItemTile;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    Context context;
    ArrayList<CarPojo> carArrayList;

    public void setOnDeleteListener(onDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    private onDeleteListener onDeleteListener;


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

        if(carArrayList.size()==1)
        {
            holder.deleteLayout.setVisibility(View.GONE);
        }
        else
        {
            holder.deleteLayout.setVisibility(View.VISIBLE);
       }
        final CarPojo carPojo = carArrayList.get(position);
        if (carPojo != null) {

            holder.carItemTile.setDetails(carPojo);
            holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("VehicleID", carPojo.VehicleID + "");
                    if (onDeleteListener != null)
                        onDeleteListener.onDelete(carPojo.VehicleID);
                }
            });

            holder.editLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editCarIntent = new Intent(context, AddCarActivity.class);
                    editCarIntent.putExtra("EditCar", AddCarActivity.editCar);
                    editCarIntent.putExtra("CarPojo", carPojo);
                    Functions.fireIntent(context, editCarIntent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return carArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CarItemTile carItemTile;
        public TextView txtDelete, txtEdit;
        public CardView carCardView;
        public LinearLayout editLayout, deleteLayout;

        public ViewHolder(View view) {
            super(view);
            txtDelete = (TextView) view.findViewById(R.id.txtDelete);
            txtEdit = (TextView) view.findViewById(R.id.txtEdit);
            carCardView = (CardView) view.findViewById(R.id.carCardView);
            editLayout = (LinearLayout) view.findViewById(R.id.editLayout);
            deleteLayout = (LinearLayout) view.findViewById(R.id.deleteLayout);
        }
    }

    public interface onDeleteListener {
        void onDelete(int vehicleId);
    }
}
