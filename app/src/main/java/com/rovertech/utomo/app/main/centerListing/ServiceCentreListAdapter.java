package com.rovertech.utomo.app.main.centerListing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.tiles.serviceCentre.ServiceCentreTile;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 15-03-2016.
 */
public class ServiceCentreListAdapter extends RecyclerView.Adapter<ServiceCentreListAdapter.ViewHolder> {

    Context context;
    ArrayList<ServiceCenterPojo> centerArrayList;

    public ServiceCentreListAdapter(Context context, ArrayList<ServiceCenterPojo> centerArrayList) {
        this.context = context;
        this.centerArrayList = centerArrayList;
    }

    public void setCentreList(ArrayList<ServiceCenterPojo> centerArrayList) {
        this.centerArrayList = new ArrayList<>();
        this.centerArrayList = centerArrayList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tile_centre_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.serviceCentreTile = new ServiceCentreTile(context, v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ServiceCenterPojo centerPojo = centerArrayList.get(position);
        if (centerPojo != null)
            holder.serviceCentreTile.setDetails(centerPojo);
    }

    @Override
    public int getItemCount() {
        return centerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ServiceCentreTile serviceCentreTile;

        public ViewHolder(View view) {
            super(view);

        }
    }
}
