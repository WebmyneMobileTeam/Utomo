package com.rovertech.utomo.app.main.notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.main.notification.model.NotificationItem;
import com.rovertech.utomo.app.main.notification.model.RescheduleBookingRequest;
import com.rovertech.utomo.app.main.notification.model.RescheduleResp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    private final Context c;
    public ArrayList<NotificationItem> notificationItems;
    private NotificationView mAdapterCallback;

    public NotificationAdapter(Context c, NotificationView mNotificationView, ArrayList<NotificationItem> notificationItems) {
        this.c=c;
        this.notificationItems = notificationItems;
        this.mAdapterCallback=mNotificationView;
       /* try {
            this.mAdapterCallback = ((NotificationView) c);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NotificationView.");
        }*/
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

        if (notificationItems.get(position).ServicecentreName != null) {
            holder.txtNotificationTitle.setText(Html.fromHtml(notificationItems.get(position).ServicecentreName));
        }
        if (notificationItems.get(position).Description != null) {
            holder.txtNotificationDesc.setText(Html.fromHtml(notificationItems.get(position).Description));
        }
        holder.txtNotificationsTimestamp.setText(notificationItems.get(position).PreferedDateTime);

        if (notificationItems.get(position).Type) {
            holder.llAccpRej.setVisibility(View.VISIBLE);

            holder.llAccp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("m click","Accept" + notificationItems.get(position).BookingID + " || " +notificationItems.get(position).NotificationID);
                    callRescheduleBookingApi(notificationItems.get(position).BookingID,notificationItems.get(position).NotificationID,true);
                }
            });
            holder.llReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("m click","Reject");
                    callRescheduleBookingApi(notificationItems.get(position).BookingID,notificationItems.get(position).NotificationID,false);
                }
            });
        }
    }

    private void callRescheduleBookingApi(String bookingID, int notificationID, final boolean b) {
        try {
            RescheduleBookingRequest request = new RescheduleBookingRequest(Integer.parseInt(bookingID), notificationID, b);
            RescheduleBookingApi apiCall = UtomoApplication.retrofit.create(RescheduleBookingApi.class);
            Call<RescheduleResp> call = apiCall.RescheduleBooking(request);
            call.enqueue(new Callback<RescheduleResp>() {
                @Override
                public void onResponse(Call<RescheduleResp> call, Response<RescheduleResp> response) {
                    if (response.body().RescheduleBookingResponce.ResponseCode == 1) {
                        if(b)
                        {
                        Toast.makeText(c,"Your Booking Reschedule Successfully",Toast.LENGTH_LONG).show();}
                        else
                        {
                            Toast.makeText(c,"Your Booking Reschedule Rejected",Toast.LENGTH_LONG).show();
                        }
                    }else
                    {
                        if(b) {
                            Toast.makeText(c, "Your Booking Reschedule Rejected", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(c, "Your Booking Reschedule Successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                    try {
                        mAdapterCallback.onMethodCallback();

                    } catch (ClassCastException exception) {
                        exception.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<RescheduleResp> call, Throwable t) {

                }
            });
        }catch (Exception e)
        {

        }

    }

    public void clear() {
        notificationItems.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(ArrayList<NotificationItem> entries) {
        notificationItems.addAll(entries);
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

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNotificationTitle, txtNotificationDesc, txtNotificationsTimestamp, txtReject, txtAccept;
        private LinearLayout llAccpRej,llAccp,llReject;

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
