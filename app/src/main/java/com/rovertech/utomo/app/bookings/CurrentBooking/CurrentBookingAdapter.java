package com.rovertech.utomo.app.bookings.CurrentBooking;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.rovertech.utomo.app.bookings.CurrentBooking.model.UserBookingsPojo;
import com.rovertech.utomo.app.bookings.MyBookingFragment;
import com.rovertech.utomo.app.tiles.CurrentServiceTile;

import java.util.List;

/**
 * Created by raghavthakkar on 15-03-2016.
 */
public class CurrentBookingAdapter extends RecyclerView.Adapter<CurrentBookingAdapter.CurrentViewHolder> {


    @MyBookingFragment.BookingViewMode
    int mBookingViewMode = 0;
    public List<UserBookingsPojo> userBookingsPojos;
    final String TAG = CurrentViewHolder.class.getName();

    public CurrentBookingAdapter(List<UserBookingsPojo> userBookingsPojos, @MyBookingFragment.BookingViewMode int bookingViewMode) {
        this.userBookingsPojos = userBookingsPojos;
        this.mBookingViewMode = bookingViewMode;
    }

    public void setUserBookingsPojos(List<UserBookingsPojo> userBookingsPojos) {
        this.userBookingsPojos.clear();
        this.userBookingsPojos = userBookingsPojos;
        notifyDataSetChanged();
    }

    @Override
    public CurrentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CurrentServiceTile currentServiceTile = new CurrentServiceTile(parent.getContext());
        CurrentViewHolder currentViewHolder = new CurrentViewHolder(currentServiceTile);
        Log.d("TAG", "OnCreateViewHolder");
        return currentViewHolder;
    }

    @Override
    public void onBindViewHolder(CurrentViewHolder holder, int position) {

        UserBookingsPojo userBookingsPojo = userBookingsPojos.get(position);
        holder.currentServiceTile.setCurrentServiceDetails(userBookingsPojo, mBookingViewMode);
    }

    @Override
    public int getItemCount() {
        return userBookingsPojos.size();
    }

    static class CurrentViewHolder extends RecyclerView.ViewHolder {
        CurrentServiceTile currentServiceTile;

        public CurrentViewHolder(View itemView) {
            super(itemView);
            currentServiceTile = (CurrentServiceTile) itemView;
        }
    }
}
