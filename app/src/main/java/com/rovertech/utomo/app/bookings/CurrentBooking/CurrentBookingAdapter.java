package com.rovertech.utomo.app.bookings.CurrentBooking;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.rovertech.utomo.app.tiles.CurrentServiceTile;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public class CurrentBookingAdapter extends RecyclerView.Adapter<CurrentBookingAdapter.CurrentViewHolder> {

    final String TAG = CurrentViewHolder.class.getName();

    @Override
    public CurrentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CurrentServiceTile currentServiceTile = new CurrentServiceTile(parent.getContext());
        CurrentViewHolder currentViewHolder = new CurrentViewHolder(currentServiceTile);
        Log.d("TAG", "OnCreateViewHolder");
        return currentViewHolder;
    }

    @Override
    public void onBindViewHolder(CurrentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    static class CurrentViewHolder extends RecyclerView.ViewHolder {
        CurrentServiceTile currentServiceTile;

        public CurrentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
