package com.rovertech.utomo.app.main.centreDetail;

import android.content.Context;

/**
 * Created by raghavthakkar on 30-03-2016.
 */
public interface CentreDetailsPresnter {

    void init();

    void fetchServiceCenterDetails(int serviceCentreID,Context context);

    void destory();



}
