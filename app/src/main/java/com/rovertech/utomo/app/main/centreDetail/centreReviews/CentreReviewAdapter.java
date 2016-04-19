package com.rovertech.utomo.app.main.centreDetail.centreReviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
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

    public CentreReviewAdapter(List<FeedBack> lstFeedBack) {
        this.lstFeedBack = lstFeedBack;
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
        holder.reviewRating.setRating(feedBack.Rating);
        holder.reviewerDate.setText(Functions.displayOnlyDate(feedBack.FeedBackDate));

    }


    @Override
    public int getItemCount() {
        return lstFeedBack.size();
    }

    public static class CentreReviewHolder extends RecyclerView.ViewHolder {

        TextView reviewerName, reviewerDesciptions, reviewerDate;
        RatingBar reviewRating;

        public CentreReviewHolder(View itemView) {
            super(itemView);
            reviewerName = (TextView) itemView.findViewById(R.id.reviewerName);
            reviewerDesciptions = (TextView) itemView.findViewById(R.id.reviewerDesciptions);
            reviewerDate = (TextView) itemView.findViewById(R.id.reviewerDate);
            reviewRating = (RatingBar) itemView.findViewById(R.id.reviewRating);

        }
    }
}
