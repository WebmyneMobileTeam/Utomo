package com.rovertech.utomo.app.main.notification.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.notification.model.NotificationItem;
import com.rovertech.utomo.app.main.notification.presenter.NotificationAdapterPresenter;
import com.rovertech.utomo.app.main.notification.presenter.NotificationAdapterPresenterImpl;
import com.rovertech.utomo.app.main.notification.presenter.NotificationAdapterView;
import com.rovertech.utomo.app.main.notification.presenter.NotificationView;

import java.util.ArrayList;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> implements NotificationAdapterView {


    private final Context c;
    public ArrayList<NotificationItem> notificationItems;
    private NotificationView mAdapterCallback;
    private NotificationAdapterPresenter presenter;
    private ProgressDialog progressDialog;

    public NotificationAdapter(Context c, NotificationView mNotificationView, ArrayList<NotificationItem> notificationItems) {
        this.c = c;
        this.notificationItems = notificationItems;
        this.mAdapterCallback = mNotificationView;
        this.presenter = new NotificationAdapterPresenterImpl(c, this);
        this.progressDialog = new ProgressDialog(c);
        this.progressDialog.setMessage("Please wait...");
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NotificationViewHolder notificationViewHolder = null;
        notificationViewHolder = new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_accept_reject, parent, false));
        return notificationViewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, final int position) {
        holder.txtNotificationTitle.setTypeface(Functions.getBoldFont(c), Typeface.BOLD);
        holder.txtNotificationDesc.setTypeface(Functions.getBoldFont(c), Typeface.NORMAL);
        holder.txtNotificationsTimestamp.setTypeface(Functions.getBoldFont(c), Typeface.NORMAL);
        holder.txtReject.setTypeface(Functions.getBoldFont(c), Typeface.NORMAL);
        holder.txtAccept.setTypeface(Functions.getBoldFont(c), Typeface.NORMAL);


        if (notificationItems.get(position).ServicecentreName != null) {
            holder.txtNotificationTitle.setText(Html.fromHtml(notificationItems.get(position).ServicecentreName));
        }
        if (notificationItems.get(position).Description != null) {
            holder.txtNotificationDesc.setText(Html.fromHtml(notificationItems.get(position).Description));
        }
        if (notificationItems.get(position).NotificationDate != null) {
            holder.txtNotificationsTimestamp.setText(Html.fromHtml(notificationItems.get(position).NotificationDate));
        }
       // holder.txtNotificationsTimestamp.setText(notificationItems.get(position).NotificationDate);

        if (notificationItems.get(position).Type) {
            holder.llAccpRej.setVisibility(View.VISIBLE);

            holder.llAccp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Log.d("m click", "Accept" + notificationItems.get(position).BookingID + " || " + notificationItems.get(position).NotificationID);
                    presenter.callRescheduleBookingApi(notificationItems.get(position).BookingID, notificationItems.get(position).NotificationID, true);
                }
            });

            holder.llReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Log.d("m click", "Reject");
                    presenter.callRescheduleBookingApi(notificationItems.get(position).BookingID, notificationItems.get(position).NotificationID, false);
                }
            });
        }
        else
        {
            holder.llAccpRej.setVisibility(View.GONE);
        }
    }

    public void clear() {
        notificationItems.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(ArrayList<NotificationItem> entries) {
       // notificationItems.addAll(entries);
        notificationItems = entries;
        notifyDataSetChanged();
    }

    public void upDateEntries(ArrayList<NotificationItem> entries) {
        notificationItems = entries;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.hide();
    }

    @Override
    public void onAcceptReject(boolean isSuccess, String message) {
        mAdapterCallback.onAcceptRejectCallback(message);
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNotificationTitle, txtNotificationDesc, txtNotificationsTimestamp, txtReject, txtAccept;
        private LinearLayout llAccpRej, llAccp, llReject;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            txtNotificationTitle = (TextView) itemView.findViewById(R.id.txtNotificationTitle);

            txtNotificationDesc = (TextView) itemView.findViewById(R.id.txtNotificationDesc);
            txtNotificationsTimestamp = (TextView) itemView.findViewById(R.id.txtNotificationsTimestamp);
            txtReject = (TextView) itemView.findViewById(R.id.txtReject);
            txtAccept = (TextView) itemView.findViewById(R.id.txtAccept);
            llAccpRej = (LinearLayout) itemView.findViewById(R.id.llAccpRej);
            llAccp = (LinearLayout) itemView.findViewById(R.id.llAccp);
            llReject = (LinearLayout) itemView.findViewById(R.id.llReject);

        }
    }
}
