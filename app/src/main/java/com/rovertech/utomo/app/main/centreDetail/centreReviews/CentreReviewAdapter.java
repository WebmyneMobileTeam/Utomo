package com.rovertech.utomo.app.main.centreDetail.centreReviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centreDetail.model.FeedBack;

import java.util.List;

/**
 * Created by raghavthakkar on 18-04-2016.
 */
public class CentreReviewAdapter extends RecyclerView.Adapter<CentreReviewAdapter.CentreReviewHolder> {

    private List<FeedBack> lstFeedBack;
    private Context context;

    public CentreReviewAdapter(List<FeedBack> lstFeedBack, Context context) {
        this.lstFeedBack = lstFeedBack;
        this.context = context;
    }

    @Override
    public CentreReviewAdapter.CentreReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CentreReviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_centre_review, parent, false));
    }

    @Override
    public void onBindViewHolder(CentreReviewHolder holder, int position) {

        FeedBack feedBack = lstFeedBack.get(position);
        holder.reviewerName.setText(feedBack.ClientName);
        holder.reviewerDesciptions.setText(feedBack.FeedBackMessage);
        holder.reviewerDate.setText(Functions.displayOnlyDate(feedBack.FeedBackDate));
        holder.txtRating.setText(String.format("%.1f/5", feedBack.Rating));

    }

    @Override
    public int getItemCount() {
        return lstFeedBack.size();
    }

    public class CentreReviewHolder extends RecyclerView.ViewHolder {

        TextView reviewerName, reviewerDesciptions, reviewerDate, txtRating;

        public CentreReviewHolder(View itemView) {
            super(itemView);
            reviewerName = (TextView) itemView.findViewById(R.id.reviewerName);
            reviewerDesciptions = (TextView) itemView.findViewById(R.id.reviewerDesciptions);
            reviewerDate = (TextView) itemView.findViewById(R.id.reviewerDate);
            txtRating = (TextView) itemView.findViewById(R.id.txtRating);

            reviewerName.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
            reviewerDesciptions.setTypeface(Functions.getRegularFont(context));
            reviewerDate.setTypeface(Functions.getRegularFont(context));
            txtRating.setTypeface(Functions.getRegularFont(context));

        }
    }
}
