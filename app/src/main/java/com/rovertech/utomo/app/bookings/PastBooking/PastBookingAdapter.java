package com.rovertech.utomo.app.bookings.PastBooking;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.rovertech.utomo.app.tiles.PastServiceTile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public class PastBookingAdapter extends RecyclerView.Adapter<PastBookingAdapter.PastViewHolder> {

    final String TAG = PastViewHolder.class.getName();
    final List<String> strings = new ArrayList<>();

    @Override
    public PastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PastServiceTile pastServiceTile = new PastServiceTile(parent.getContext());
        PastViewHolder pastViewHolder = new PastViewHolder(pastServiceTile);
        Log.d("TAG", "OnCreateViewHolder");
        return pastViewHolder;
    }

    @Override
    public void onBindViewHolder(PastViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public void addMoreData(List<String> strings) {

        this.strings.addAll(strings);
        notifyDataSetChanged();

    }

    static class PastViewHolder extends RecyclerView.ViewHolder {
        PastServiceTile pastServiceTile;

        public PastViewHolder(View itemView) {
            super(itemView);
        }
    }
}
