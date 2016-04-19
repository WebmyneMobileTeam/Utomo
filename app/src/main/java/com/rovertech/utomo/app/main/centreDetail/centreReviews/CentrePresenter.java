package com.rovertech.utomo.app.main.centreDetail.centreReviews;

import android.content.Context;

/**
 * Created by raghavthakkar on 18-04-2016.
 */
public interface CentrePresenter {

   void initView();

    void setUpRecyclerView(Context context);

    void destroy();

}
