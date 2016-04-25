package com.rovertech.utomo.app.offers.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.main.drawer.AdminOfferRequestAPI;
import com.rovertech.utomo.app.offers.model.AdminOfferResp;
import com.rovertech.utomo.app.offers.model.OfferItem;
import com.rovertech.utomo.app.offers.model.OfferPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 21-04-2016.
 */
public class AdminOfferAdapter extends RecyclerView.Adapter<AdminOfferAdapter.NotificationViewHolder> {

    private static  Context c;
    private NotificationViewHolder holder = null;
    private ArrayList<OfferPojo> itemList;

    public AdminOfferAdapter(Context c,ArrayList<OfferPojo> itemList) {
        this.itemList = itemList;
        this.c=c;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        switch (viewType) {

            default:
                holder = new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_offer, parent, false));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

    holder.txtOfferTitle.setText(itemList.get(position).OfferName);
        holder.txtOfferTitleCode.setText(itemList.get(position).OfferCode);
        holder.txtOfferDesc.setText(itemList.get(position).Description);
        holder.txtOfferValidTo.setText("Valid Upto : "+ itemList.get(position).ValidTo);

        Glide.with(c)
                .load(itemList.get(position).OfferImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtOfferTitle,txtOfferTitleCode,txtOfferDesc,txtOfferValidTo,txtUseOffer;
        private ImageView img;
        public NotificationViewHolder(View itemView) {
            super(itemView);
            txtOfferTitle= (TextView) itemView.findViewById(R.id.txtOfferTitle);
            txtOfferTitleCode= (TextView) itemView.findViewById(R.id.txtOfferTitleCode);
            txtOfferDesc= (TextView) itemView.findViewById(R.id.txtOfferDesc);
            txtOfferValidTo= (TextView) itemView.findViewById(R.id.txtOfferValidTo);
            txtUseOffer= (TextView) itemView.findViewById(R.id.txtUseOffer);
            img= (ImageView) itemView.findViewById(R.id.img);
            txtUseOffer.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.txtUseOffer:
                    setClipboard(txtOfferTitleCode.getText().toString());
                    break;

                default:

                    break;
            }
        }

        private void setClipboard(String text) {
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(c,"your code is copied successufully",Toast.LENGTH_SHORT).show();
            paste();
        }

        public void paste() {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard.getText() != null) {
                    Log.d("copies text", clipboard.getText() + "");

                    // txtNotes.getText().insert(txtNotes.getSelectionStart(), clipboard.getText());
                }
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                if (item.getText() != null) {
                    //txtNotes.getText().insert(txtNotes.getSelectionStart(), item.getText());
                    Log.d("copies text", item.getText() + "");
                }
            }
        }
    }


}
