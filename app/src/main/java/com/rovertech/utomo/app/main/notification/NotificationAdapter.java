package com.rovertech.utomo.app.main.notification;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rovertech.utomo.app.R;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    public static final int NOTIFICATION_SIMPLE = 0;
    public static final int NOTIFICATIONS_ACCEPT_REJECT = 1;


    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 0) {
            return NOTIFICATION_SIMPLE;
        } else {
            return NOTIFICATIONS_ACCEPT_REJECT;
        }


    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        NotificationViewHolder notificationViewHolder = null;

        switch (viewType) {

            case NOTIFICATION_SIMPLE:
                notificationViewHolder = new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_simple, parent, false));
                break;
            case NOTIFICATIONS_ACCEPT_REJECT:
                notificationViewHolder = new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_accept_reject, parent, false));
                break;
            default:
                notificationViewHolder = new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_simple, parent, false));
                break;
        }

        return notificationViewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        public NotificationViewHolder(View itemView) {
            super(itemView);
        }
    }
}
