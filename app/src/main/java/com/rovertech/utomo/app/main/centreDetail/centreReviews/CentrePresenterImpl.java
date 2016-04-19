package com.rovertech.utomo.app.main.centreDetail.centreReviews;

import android.content.Context;

import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.centreDetail.model.FeedBack;

import java.util.List;

/**
 * Created by raghavthakkar on 18-04-2016.
 */
public class CentrePresenterImpl implements CentrePresenter {

    private CentreReviewsView mCentreReviewsView;

    public CentrePresenterImpl(CentreReviewsView mCentreReviewsView) {
        this.mCentreReviewsView = mCentreReviewsView;
    }

    @Override
    public void initView() {

        mCentreReviewsView.init();


    }

    @Override
    public void setUpRecyclerView(Context context) {
        List<FeedBack> lstFeedBack = PrefUtils.getFeedBack(context);
        CentreReviewAdapter centreReviewAdapter = new CentreReviewAdapter(lstFeedBack);
        mCentreReviewsView.setUpRecyclerView(centreReviewAdapter);

    }

    @Override
    public void destroy() {
        if (mCentreReviewsView != null) {
            mCentreReviewsView = null;
        }
    }

}
